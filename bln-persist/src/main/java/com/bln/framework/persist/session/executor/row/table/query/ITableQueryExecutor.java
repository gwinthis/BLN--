package com.bln.framework.persist.session.executor.row.table.query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.session.executor.row.table.ITableExecutor;


public interface ITableQueryExecutor extends ITableExecutor{


	/**
	 * 查询记录
	 * @param conClause 条件信息
	 * @param pagination 分页信息
	 * @param orderby 排序信息
	 * @return 满足条件的记录
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public List<IRow> query(IConditionClause conClause, IPagination pagination, String orderby)
			throws SQLException, IOException;

	/**
	 * 查询之后并锁定对应记录
	 * @param conClause 条件语句
	 * @param orderby 排序子句
	 * @return 查询出的记录
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<IRow> queryForUpdate(IConditionClause conClause, String orderby)
			throws SQLException, IOException;

	/**
	 * 计算是否存在符合条件的记录
	 * @param conClause 条件语句
	 * @return 是否存在
	 * @throws SQLException
	 * @throws IOException
	 */
	public boolean isExists(IConditionClause conClause) throws SQLException, IOException;

	/**
	 * 计算符合条件的记录数
	 * @param conClause 条件语句
	 * @return 行数
	 * @throws SQLException
	 * @throws IOException
	 */
	public int count(IConditionClause conClause) throws SQLException, IOException;
	
	
	/**
	 * clone出ITableSession对象
	 * @return
	 */
	public ITableQueryExecutor clone();

	/**
	 * 查询满足条件的第一行记录
	 * @param conClause 条件信息
	 * @param pagination 分页信息
	 * @param orderby 排序信息
	 * @return 满足条件的第一行记录
	 * @throws SQLException
	 * @throws IOException
	 */
	public IRow queryFirstRow(IConditionClause conClause, IPagination pagination, String orderby)
			throws SQLException, IOException;
}