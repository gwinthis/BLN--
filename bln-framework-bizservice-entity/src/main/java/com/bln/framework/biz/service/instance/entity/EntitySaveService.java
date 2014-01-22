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
 * 记录删除服务
 */
public class EntitySaveService extends EntityServiceBase{

	/**
	 * 当执行删除的时候是否删除子表记录
	 */
	protected boolean deleteChildRowOnDelete = false;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.获取父表记录
		List<IRow> rows = reqMo.getRowsOfReq(tableName);
		
		//2.获取子表记录
		Map<String, List<IRow>> childRowsMap = reqMo.getRowsOfReq(childTableNames);
		
		//3.获取实体
		IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
		
		//4.保存父表记录
		Map<String, List<IRow>> allRows = entity.save(rows, childRowsMap, deleteChildRowOnDelete);
		
		//5.设置返回数据
		reqMo.setAllRowsOfResp(allRows);
		
		//6.返回响应MO
		IMessageObject respMo = moTemplate.serviceSuccessfulEnd(reqMo);
		return respMo;
	}

	/**
	 * @return the deleteChildRowOnDelete
	 */
	public boolean isDeleteChildRowOnDelete() {
		return deleteChildRowOnDelete;
	}

	/**
	 * @param deleteChildRowOnDelete the deleteChildRowOnDelete to set
	 */
	public void setDeleteChildRowOnDelete(boolean deleteChildRowOnDelete) {
		this.deleteChildRowOnDelete = deleteChildRowOnDelete;
	}
}
