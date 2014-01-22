/**
 * @author gengw
 * Created on Apr 27, 2012
 */
package com.bln.framework.persist.session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import com.bln.framework.persist.jdbc.wrapper.ConnectionLogWrapper;
import com.bln.framework.persist.session.executor.row.sp.IStoreProcedureExecutor;
import com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor;
import com.bln.framework.persist.session.executor.row.table.query.lib.ITableQueryExecutorLib;
import com.bln.framework.persist.session.executor.row.table.update.ITableUpdateExecutor;
import com.bln.framework.persist.session.executor.row.table.update.lib.ITableUpdateExecutorLib;
import com.bln.framework.persist.session.executor.row.update.IUpdateExecutor;
import com.bln.framework.persist.session.executor.row.view.IViewExecutor;

/**
 * 数据库的会话类
 */
public class Session implements ISession {
	
	/**
	 * 数据库连接
	 */
	protected Connection connection = null;

	/**
	 * tableQueryExecutor库
	 */
	protected ITableQueryExecutorLib tableQueryExecutorLib = null;

	/**
	 * tableUpdateExecutor库
	 */
	protected ITableUpdateExecutorLib tableUpdateExecutorLib = null;

	/**
	 * 视图执行器
	 */
	protected IViewExecutor viewExecutor = null;

	/**
	 * 更新执行器
	 */
	protected IUpdateExecutor updateExecutor = null;
	
	/**
	 * 存储过程执行器
	 */
	protected IStoreProcedureExecutor spExecutor = null;
	
	/**
	 * 构造函数
	 */
	public Session(){

	}
	
	/**
	 * 构造函数
	 * @param conn 数据库连接
	 */
	public Session(Connection conn){
		if (!(conn instanceof ConnectionLogWrapper)){
			conn = new ConnectionLogWrapper(conn);
		}
		this.connection = conn;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#isClosed()
	 */
	public boolean isClosed() throws SQLException {
		return connection.isClosed();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#close()
	 */
	public void close() throws SQLException {
		connection.close();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#getTransactionIsolation()
	 */
	public int getTransactionIsolation() throws SQLException {
		return connection.getTransactionIsolation();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#beginTransaction()
	 */
	public void beginTransaction() throws SQLException{
		connection.setAutoCommit(false);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#setSavepoint()
	 */
	public Savepoint setSavepoint() throws SQLException {
		return connection.setSavepoint();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#setSavepoint(java.lang.String)
	 */
	public Savepoint setSavepoint(String name) throws SQLException {
		return connection.setSavepoint(name);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#releaseSavepoint(java.sql.Savepoint)
	 */
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		connection.releaseSavepoint(savepoint);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#rollback()
	 */
	public void rollback() throws SQLException {
		connection.rollback();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#rollback(java.sql.Savepoint)
	 */
	public void rollback(Savepoint savepoint) throws SQLException {
		connection.rollback(savepoint);
	}
	
	/**
	 * @throws SQLException
	 * @see java.sql.Connection#commit()
	 */
	public void commit() throws SQLException {
		connection.commit();
	}

	/**
	 * 获得表的查询执行器对象
	 * @param tableName 表名
	 * @return 获得表的查询执行器对象
	 */ 
	public ITableQueryExecutor getTableQueryExecutor(String tableName){
		
		ITableQueryExecutor tqe = tableQueryExecutorLib.getNotNullInstance(tableName);
		tqe.setConnection(connection);

		return tqe;
	}
	
	/**
	 * 获得表的查询执行器对象
	 * @param tableName 表名
	 * @return 获得表的更新执行器对象
	 */ 
	public ITableUpdateExecutor getTableUpdateExecutor(String tableName){
		
		ITableUpdateExecutor tue = tableUpdateExecutorLib.getNotNullInstance(tableName);
		tue.setConnection(connection);

		return tue;
	}

	/**
	 * 获取视图执行器
	 * @return the viewExecutor
	 */
	public IViewExecutor getViewExecutor() {
		
		IViewExecutor viewExecutor = this.viewExecutor.clone();
		viewExecutor.setConnection(connection);
		
		return viewExecutor;
	}

	/**
	 * 获取更新执行器
	 * @return the udpateExecutor
	 */
	public IUpdateExecutor getUpdateExecutor() {
		
		IUpdateExecutor udpateExecutor = this.updateExecutor.clone();
		udpateExecutor.setConnection(connection);

		return udpateExecutor;
	}
	
	/**
	 * 获取存储过程执行器
	 * @return the spExecutor
	 */
	public IStoreProcedureExecutor getSpExecutor() {
		
		IStoreProcedureExecutor spExecutor = this.spExecutor.clone();
		spExecutor.setConnection(connection);
		
		return spExecutor;
	}
	
	/**
	 * @param tableQueryExecutorLib the tableQueryExecutorLib to set
	 */
	public void setTableQueryExecutorLib(
			ITableQueryExecutorLib tableQueryExecutorLib) {
		this.tableQueryExecutorLib = tableQueryExecutorLib;
	}

	/**
	 * @param tableUpdateExecutorLib the tableUpdateExecutorLib to set
	 */
	public void setTableUpdateExecutorLib(
			ITableUpdateExecutorLib tableUpdateExecutorLib) {
		this.tableUpdateExecutorLib = tableUpdateExecutorLib;
	}

	
	/**
	 * @param viewExecutor the viewExecutor to set
	 */
	public void setViewExecutor(IViewExecutor viewExecutor) {
		this.viewExecutor = viewExecutor;
	}

	/**
	 * @param udpateExecutor the udpateExecutor to set
	 */
	public void setUpdateExecutor(IUpdateExecutor updateExecutor) {
		this.updateExecutor = updateExecutor;
	}
	
	/**
	 * @param spExecutor the spExecutor to set
	 */
	public void setSpExecutor(IStoreProcedureExecutor spExecutor) {
		this.spExecutor = spExecutor;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ISession clone(Connection connection){
		
		Session session = new Session(connection);
		
		session.setSpExecutor(spExecutor);
		session.setTableQueryExecutorLib(tableQueryExecutorLib);
		session.setTableUpdateExecutorLib(tableUpdateExecutorLib);
		session.setViewExecutor(viewExecutor);
		session.setUpdateExecutor(updateExecutor);
		
		return session;
	}


}
