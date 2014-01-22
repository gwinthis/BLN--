/**
 * @author gengw
 * Created on Aug 9, 2012
 */
package com.bln.framework.biz.service.instance.entity.integrate;

import java.util.List;
import java.util.Map;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;

import com.bln.framework.biz.service.instance.entity.complex.EntityService;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;

/**
 * 集成查询实体服务
 */
public class IntegrateEntityQueryService extends EntityService {
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.获取要查询的数据
		Map<String, List<IRow>> rowsMap = reqMo.getAllRowsOfReq();
		
		//2.获取查询条件、排序信息查询表的记录
		for ( Map.Entry<String, List<IRow>> entry : rowsMap.entrySet()){
			
			//获取表名
			String tableName = entry.getKey();
			
			//获取表的记录
			List<IRow> reqRows = entry.getValue();
			IRow row = reqRows.get(0);
			
			//获取查询条件
			IRow condition = null;
			if(row != null){
				List<IRow> conditions = row.getSubRows("CONDITION");
				if(conditions != null && conditions.size() > 0){
					condition = conditions.get(0);
				}				
			}
			
			//获取排序信息
			String orderbyClause = null;
			if(row != null){
				List<IRow> orderbys = row.getSubRows("ORDERBY");
				if(orderbys != null && orderbys.size() > 0){
					IRow orderby = orderbys.get(0);
					orderbyClause = orderby.getValue("CLAUSE");
				}				
			}
			
			//获取实体
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//执行查询
			List<IRow> rows = entity.query(condition, null, orderbyClause);
			reqMo.setRowsOfResp(tableName, rows);

		}
		
		//3.返回MO
		moTemplate.serviceSuccessfulEnd(reqMo);
		return reqMo;
	}
}
