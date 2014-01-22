package com.bln.framework.persist.session.executor.row.table.dp;

import com.bln.framework.persist.dataprivilege.IDataPrivilege;
import com.bln.framework.persist.session.executor.row.table.ITableExecutor;

/**
 * 
 * @param <E> 要代理的ITableExecutor子类
 */
public interface ITableExecutorWithDp<E extends ITableExecutor>{

	/**
	 * @return the dataPrivilege
	 */
	public abstract IDataPrivilege getDataPrivilege();

	/**
	 * @param dataPrivilege the dataPrivilege to set
	 */
	public abstract void setDataPrivilege(IDataPrivilege dataPrivilege);

	/**
	 * @return the tableExecutor
	 */
	public E getTableExecutor();

	/**
	 * @param tableExecutor the tableExecutor to set
	 */
	public void setTableExecutor(E tableExecutor);

	/**
	 * @return
	 * @see com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor#clone()
	 */
	public ITableExecutorWithDp<E> clone();
	
	/**
	 * 生成TableSession
	 * @return TableSession实例
	 */
	public ITableExecutor buildToTableExecutor();

}