package com.bln.framework.persist.session.executor.row.update;

import java.io.IOException;
import java.sql.SQLException;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.ISessionExecutor;
import com.bln.framework.persist.sql.entity.common.ICommonSqlEntity;

public interface IUpdateExecutor extends ISessionExecutor<IRow>{

	/**
	 * ����SQL����ѯ����
	 * @param selectSqlEntity ��ѯSQLʵ��
	 * @return ��ѯ���Ľ��
	 * @throws SQLException
	 * @throws IOException
	 */
	public abstract int execute(ICommonSqlEntity sqlEntity) throws SQLException, IOException;

	/**
	 * clone��ISessionExecutor����
	 * @return
	 */
	public IUpdateExecutor clone();

}