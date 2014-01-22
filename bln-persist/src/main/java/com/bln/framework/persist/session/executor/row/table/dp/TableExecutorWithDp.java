/**
 * @author gengw
 * Created on May 4, 2012
 */
package com.bln.framework.persist.session.executor.row.table.dp;

import com.bln.framework.persist.dataprivilege.IDataPrivilege;
import com.bln.framework.persist.session.executor.material.condition.ConditionClause;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.row.table.ITableExecutor;

/**
 * ʹ��Ȩ�޵ı�ִ����
 */
public abstract class TableExecutorWithDp<T extends TableExecutorWithDp<T, E>, E extends ITableExecutor> implements ITableExecutorWithDp<E>{

	/**
	 * ���ѯִ����
	 */
	protected E tableExecutor = null;
	
	/**
	 * ����Ȩ��
	 */
	protected IDataPrivilege dataPrivilege = null;

	/**
	 * ������ʵ��
	 * @return
	 */
	protected abstract T newInstance();
	
	/**
	 * @return
	 * @see com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor#clone()
	 */
	public T clone() {
		
		T tqed = newInstance();
		
		tqed.setDataPrivilege(dataPrivilege);
		tqed.setTableExecutor(tableExecutor);
		
		return tqed;
	}

	
	/**
	 * ���Ȩ������
	 * @param conClause ԭ������
	 * @return ���Ȩ�޺������
	 */
	protected IConditionClause addPrivilege(IConditionClause conClause){
		
		//��ȡ��ѯȨ��
		if(dataPrivilege == null){
			return conClause;
		}
		
		IConditionClause privilegeCon = dataPrivilege.getDataPrivilege();
		if(privilegeCon == null){
			privilegeCon = new ConditionClause();
		}
		
		//��Ӳ�ѯȨ��
		privilegeCon.addConClause(conClause, "AND");
		
		//����Ȩ��
		return privilegeCon;
	}

	/**
	 * @return the dataPrivilege
	 */
	public IDataPrivilege getDataPrivilege() {
		return dataPrivilege;
	}


	/**
	 * @param dataPrivilege the dataPrivilege to set
	 */
	public void setDataPrivilege(IDataPrivilege dataPrivilege) {
		this.dataPrivilege = dataPrivilege;
	}

	/**
	 * @return the tableExecutor
	 */
	public E getTableExecutor() {
		return tableExecutor;
	}

	/**
	 * @param tableExecutor the tableExecutor to set
	 */
	public void setTableExecutor(E tableExecutor) {
		this.tableExecutor = tableExecutor;
	}
}
