/**
 * @author gengw
 * Created at 2013-10-08
 */
package com.bln.framework.biz.service.instance.entity;

import java.util.List;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;

/**
 * 实体新增服务
 */
public class EntityInsertService extends EntityServiceBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.新增记录
		List<IRow> rows = reqMo.getRowsOfReq(tableName);
		if ( rows != null && rows.size() > 0){
			
			//1.1获取实体
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//1.2新增记录
			rows = entity.insertRows(rows);
			
			//1.3设置返回数据
			reqMo.setRowsOfResp(tableName, rows);
			
		}
		
		//2.返回响应MO
		IMessageObject respMo = moTemplate.serviceSuccessfulEnd(reqMo);
		return respMo;
	}
}
