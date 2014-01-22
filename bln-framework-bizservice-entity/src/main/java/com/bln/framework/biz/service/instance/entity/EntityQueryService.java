/**
 * @author gengw
 * Created at 2013-10-08
 */
package com.bln.framework.biz.service.instance.entity;

import java.util.List;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

/**
 * 实体查询服务
 */
public class EntityQueryService extends EntityQueryServiceBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.获取查询条件
		IRow condition = null;
		
		//获取第一条查询条件记录
		List<IRow> cons = reqMo.getRowsOfReq(conditionNodeName);
		if(null != cons && cons.size() > 0){
			condition = cons.get(0);
		}
		
		//2.获取分页信息
		IPagination pagination = paginationUtil.pagination(reqMo);
		
		//3.提取排序信息
		String orderby = this.orderbyExtractor.extractor(reqMo);
		
		//4.获取实体
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//5.执行查询
		List<IRow> rows = entity.query(condition, pagination, orderby);
		reqMo.setRowsOfResp(tableName, rows);
		
		//5.设置总行数和总页数		
		reqMo = paginationUtil.setPageCount(reqMo, pagination);

		//6.返回MO
		IMessageObject respMo = moTemplate.serviceSuccessfulEnd(reqMo);
		return respMo;
	}
}
