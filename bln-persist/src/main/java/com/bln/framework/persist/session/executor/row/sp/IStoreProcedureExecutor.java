package com.bln.framework.persist.session.executor.row.sp;

import java.io.IOException;
import java.sql.SQLException;

import com.bln.framework.persist.jdbc.template.storeprocedure.IStoreProcedureParameters;
import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureResult;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.ISessionExecutor;

public interface IStoreProcedureExecutor extends ISessionExecutor<IRow>{

	/**
	 * 执行存储过程
	 * @param procedureName 存储过程名称
	 * @param spParameters 存储过程参数
	 * @return 执行后的结果，如果输出参数有结果集，返回List
	 * @throws SQLException
	 * @throws IOException
	 */
	public abstract StoreProcedureResult<IRow> execute(String procedureName, IStoreProcedureParameters spParameters) throws SQLException, IOException;

	/**
	 * clone出ITableSession对象
	 * @return
	 */
	public IStoreProcedureExecutor clone();
}