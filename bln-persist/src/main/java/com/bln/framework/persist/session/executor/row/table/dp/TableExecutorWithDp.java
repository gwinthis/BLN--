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
 * 使用权限的表执行器
 */
public abstract class TableExecutorWithDp<T extends TableExecutorWithDp<T, E>, E extends ITableExecutor> implements ITableExecutorWithDp<E>{

	/**
	 * 表查询执行器
	 */
	protected E tableExecutor = null;
	
	/**
	 * 数据权限
	 */
	protected IDataPrivilege dataPrivilege = null;

	/**
	 * 生成新实例
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
	 * 添加权限条件
	 * @param conClause 原有条件
	 * @return 添加权限后的条件
	 */
	protected IConditionClause addPrivilege(IConditionClause conClause){
		
		//获取查询权限
		if(dataPrivilege == null){
			return conClause;
		}
		
		IConditionClause privilegeCon = dataPrivilege.getDataPrivilege();
		if(privilegeCon == null){
			privilegeCon = new ConditionClause();
		}
		
		//添加查询权限
		privilegeCon.addConClause(conClause, "AND");
		
		//返回权限
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
