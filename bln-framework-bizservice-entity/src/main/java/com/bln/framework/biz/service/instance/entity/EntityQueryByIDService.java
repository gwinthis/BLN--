/**
 * @author gengw
 * Created at 2013-10-08
 */
package com.bln.framework.biz.service.instance.entity;

import java.util.List;
import java.util.Map;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;

/**
 * 通过ID获取记录和其子表查询服务
 */
public class EntityQueryByIDService extends EntityQueryServiceBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.获取查询条件
		IRow condition = reqMo.getRowOfReq(conditionNodeName);
		
		//2.获取实体
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//3.执行查询
		if(childTableNames != null && childTableNames.length > 0){
			Map<String, List<IRow>> respRowsMap = entity.queryByIdWithChilds(condition, childTableNames);
			reqMo.setAllRowsOfResp(respRowsMap);
		}else{
			IRow row = entity.queryById(condition);
			reqMo.setRowOfResp(tableName, row);
		}
		
		//4.返回响应MO
		IMessageObject respMo = moTemplate.serviceSuccessfulEnd(reqMo);
		return respMo;
	}

}
