package com.bln.framework.persist.session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import com.bln.framework.persist.session.executor.row.sp.IStoreProcedureExecutor;
import com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor;
import com.bln.framework.persist.session.executor.row.table.update.ITableUpdateExecutor;
import com.bln.framework.persist.session.executor.row.update.IUpdateExecutor;
import com.bln.framework.persist.session.executor.row.view.IViewExecutor;

public interface ISession {

	/**
	 * @return
	 * @throws SQLException
	 * @see java.sql.Connection#isClosed()
	 */
	public abstract boolean isClosed() throws SQLException;

	/**
	 * @throws SQLException
	 * @see java.sql.Connection#close()
	 */
	public abstract void close() throws SQLException;

	/**
	 * @return
	 * @throws SQLException
	 * @see java.sql.Connection#getTransactionIsolation()
	 */
	public abstract int getTransactionIsolation() throws SQLException;

	/**
	 * ��������
	 * @throws SQLException
	 */
	public abstract void beginTransaction() throws SQLException;

	/**
	 * @return
	 * @throws SQLException
	 * @see java.sql.Connection#setSavepoint()
	 */
	public abstract Savepoint setSavepoint() throws SQLException;

	/**
	 * @param name
	 * @return
	 * @throws SQLException
	 * @see java.sql.Connection#setSavepoint(java.lang.String)
	 */
	public abstract Savepoint setSavepoint(String name) throws SQLException;

	/**
	 * @param savepoint
	 * @throws SQLException
	 * @see java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
	 */
	public abstract void releaseSavepoint(Savepoint savepoint)
			throws SQLException;

	/**
	 * @throws SQLException
	 * @see java.sql.Connection#rollback()
	 */
	public abstract void rollback() throws SQLException;

	/**
	 * @param savepoint
	 * @throws SQLException
	 * @see java.sql.Connection#rollback(java.sql.Savepoint)
	 */
	public abstract void rollback(Savepoint savepoint) throws SQLException;
	
	/**
	 * ��¡ISession����
	 * @param connection
	 * @return
	 */
	public ISession clone(Connection connection);

	/**
	 * ��ñ�Ĳ�ѯִ��������
	 * @param tableName ����
	 * @return ��ñ�Ĳ�ѯִ��������
	 */
	public ITableQueryExecutor getTableQueryExecutor(String tableName);

	/**
	 * ��ñ�Ĳ�ѯִ��������
	 * @param tableName ����
	 * @return ��ñ�ĸ���ִ��������
	 */
	public ITableUpdateExecutor getTableUpdateExecutor(String tableName);

	/**
	 * ��ȡ��ͼִ����
	 * @return the viewExecutor
	 */
	public IViewExecutor getViewExecutor();

	/**
	 * ��ȡ�洢����ִ����
	 * @return the spExecutor
	 */
	public IStoreProcedureExecutor getSpExecutor();

	/**
	 * @throws SQLException
	 * @see java.sql.Connection#commit()
	 */
	public void commit() throws SQLException;

	/**
	 * ��ȡ����ִ����
	 * @return udpateExecutor
	 */
	public abstract IUpdateExecutor getUpdateExecutor();
	
}