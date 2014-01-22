package com.bln.framework.persist.session.executor.row.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.ISessionExecutor;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;

public interface IViewExecutor extends ISessionExecutor<IRow>{

	/**
	 * 根据SQL语句查询数据
	 * @param selectSqlEntity 查询SQL实体
	 * @return 查询出的结果
	 * @throws SQLException
	 * @throws IOException
	 */
	public abstract List<IRow> query(ISelectSqlEntity selectSqlEntity,
			IPagination pagination) throws SQLException, IOException;

	/**
	 * clone出ISessionExecutor对象
	 * @return
	 */
	public IViewExecutor clone();

	/**
	 * 查询结果集中的第一行记录
	 * @param selectSqlEntity select sql实体
	 * @param pagination 分页信息
	 * @return 第一条记录
	 * @throws SQLException
	 * @throws IOException
	 */
	public IRow queryFirstRow(ISelectSqlEntity selectSqlEntity, IPagination pagination) throws SQLException,
			IOException;
}