package com.bln.framework.persist.session.executor.row.sp;

import java.io.IOException;
import java.sql.SQLException;

import com.bln.framework.persist.jdbc.template.storeprocedure.IStoreProcedureParameters;
import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureResult;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.ISessionExecutor;

public interface IStoreProcedureExecutor extends ISessionExecutor<IRow>{

	/**
	 * ִ�д洢����
	 * @param procedureName �洢��������
	 * @param spParameters �洢���̲���
	 * @return ִ�к�Ľ���������������н����������List
	 * @throws SQLException
	 * @throws IOException
	 */
	public abstract StoreProcedureResult<IRow> execute(String procedureName, IStoreProcedureParameters spParameters) throws SQLException, IOException;

	/**
	 * clone��ITableSession����
	 * @return
	 */
	public IStoreProcedureExecutor clone();
}