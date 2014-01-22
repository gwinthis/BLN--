package com.bln.framework.persist.template;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.bln.framework.persist.jdbc.template.storeprocedure.IStoreProcedureParameters;
import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureResult;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

public interface IPersistTemplate {

	public abstract IRow queryFirstRowFromTable(
			String tableName, String where, List<Object> params,
			String orderby, IPagination pagination) throws SQLException,
			IOException;

	public abstract List<IRow> queryFromTable(
			String tableName, String where, List<Object> params,
			String orderby, IPagination pagination) throws SQLException,
			IOException;

	public abstract boolean isExsistsFromTable(
			String tableName, String where, List<Object> params)
			throws SQLException, IOException;

	public abstract void deleteFromTable( String tableName,
			String where) throws SQLException;

	public abstract IRow insertIntoTable( String tableName,
			IRow row) throws SQLException;

	public abstract IRow updateFromTable( String tableName,
			IRow row) throws SQLException, PersistStaleEntityException;

	public abstract int updateFromTableByCondition(
			String tableName, IRow row, IConditionClause conClause)
			throws SQLException, PersistStaleEntityException;

	public abstract int deleteFromTableByCondition(
			String tableName, IConditionClause conClause) throws SQLException,
			PersistStaleEntityException;

	public abstract IRow queryFirstRow( String sql,
			List<Object> params, IPagination pagination) throws SQLException,
			IOException;

	public abstract List<IRow> query( String sql,
			List<Object> params, IPagination pagination) throws SQLException,
			IOException;

	public abstract int update( String sql,
			List<Object> params) throws SQLException, IOException;

	public abstract StoreProcedureResult<IRow> executeStoreProcedure(
			 String procedureName,
			IStoreProcedureParameters params) throws SQLException, IOException;

	public abstract void beginTransaction()
			throws SQLException;

	public abstract void commit() throws SQLException;

	public abstract void rollback() throws SQLException;

	public abstract void closeCurrentAllSession() throws SQLException;

}