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
 * ��¼ɾ������
 */
public class EntityDeleteService extends EntityServiceBase{

	/**
	 * ��ִ��ɾ����ʱ���Ƿ�ɾ���ӱ��¼
	 */
	protected boolean deleteChildRowOnDelete = false;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IService#execute(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable {
		
		//1.��ȡ��¼
		List<IRow> rows = reqMo.getRowsOfReq(tableName);
		if ( rows != null && rows.size() > 0){
			
			//1.1��ȡʵ��
			IEntityStatelessModule entity = entityLib.getNotNullInstance(tableName);
			
			//1.2ɾ����¼
			entity.deleteRows(rows, deleteChildRowOnDelete);
		}
		
		//2.������ӦMO
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
