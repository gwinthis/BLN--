package com.bln.framework.persist.session.executor.row.table.update;

import java.sql.SQLException;


import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.row.table.ITableExecutor;

public interface ITableUpdateExecutor extends ITableExecutor{

	/**
	 * 插入记录
	 * @param row 行对象
	 * @return 插入之后的行对象
	 * @throws Throwable
	 */
	public abstract IRow insert(IRow row) throws SQLException;

	/**
	 * @param row
	 * @return
	 * @throws Throwable
	 */
	public abstract IRow update(IRow row) throws PersistStaleEntityException, SQLException;


	/**
	 * 删除记录
	 * @param row 记录对象
	 * @return 记录对象
	 * @throws Throwable
	 */
	public IRow delete(IRow row) throws PersistStaleEntityException, SQLException;

	/**
	 * 根据条件和数据项删除多行记录
	 * @param conClause 要删除记录的条件
	 * @return 删除的条数
	 * @throws SQLException 
	 */
	public int deleteByCondition(IConditionClause conClause) throws SQLException;

	/**
	 * 根据条件和数据项更新多行记录
	 * @param row 要更新的数据
	 * @param conClause 要更新记录的条件
	 * @return 更新的条数
	 * @throws SQLException 
	 */
	public int updateByCondition(IRow row, IConditionClause conClause)
			throws SQLException;

	/**
	 * clone出ITableSession对象
	 * @return
	 */
	public ITableUpdateExecutor clone();
}