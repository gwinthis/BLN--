package com.bln.framework.persist.session.executor.row.update;

import java.io.IOException;
import java.sql.SQLException;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.ISessionExecutor;
import com.bln.framework.persist.sql.entity.common.ICommonSqlEntity;

public interface IUpdateExecutor extends ISessionExecutor<IRow>{

	/**
	 * 根据SQL语句查询数据
	 * @param selectSqlEntity 查询SQL实体
	 * @return 查询出的结果
	 * @throws SQLException
	 * @throws IOException
	 */
	public abstract int execute(ICommonSqlEntity sqlEntity) throws SQLException, IOException;

	/**
	 * clone出ISessionExecutor对象
	 * @return
	 */
	public IUpdateExecutor clone();

}