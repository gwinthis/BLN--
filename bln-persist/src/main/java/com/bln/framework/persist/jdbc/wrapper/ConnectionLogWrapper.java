/**
 * @author Gengw
 * Created at 2008-05-05
 * 
 */

package com.bln.framework.persist.jdbc.wrapper;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Connection外覆类
 * 
 */
@SuppressWarnings({ "unchecked" })
public class ConnectionLogWrapper implements java.sql.Connection{
	
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(ConnectionLogWrapper.class);
	
	private Connection _connection = null;

	public ConnectionLogWrapper (Connection conn){
		_connection = conn;
		log.debug("start use connection: " + _connection);
	}
	
	public void clearWarnings() throws SQLException {
		_connection.clearWarnings();
	}

	public void close() throws SQLException {
		
		String info = _connection.toString();
		if(log.isDebugEnabled()){
			log.debug("closing connection: " + _connection);
		}
		
		_connection.close();
		
		if(log.isDebugEnabled()){
			log.debug("closed connection: " + info);
		}
	}

	public void commit() throws SQLException {
		_connection.commit();
	}

	public Statement createStatement() throws SQLException {
		return new StatementLogWrapper(_connection.createStatement());
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		return new StatementLogWrapper(_connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return new StatementLogWrapper(_connection.createStatement(resultSetType, resultSetConcurrency));
	}

	public boolean getAutoCommit() throws SQLException {
		return _connection.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		return _connection.getCatalog();
	}

	public int getHoldability() throws SQLException {
		return _connection.getHoldability();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return _connection.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		return _connection.getTransactionIsolation();
	}

	@SuppressWarnings("rawtypes")
	public Map getTypeMap() throws SQLException {
		return _connection.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		return _connection.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return _connection.isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return _connection.isReadOnly();
	}

	public String nativeSQL(String sql) throws SQLException {
		return _connection.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		CallableStatementLogWrapper cs = new CallableStatementLogWrapper(_connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
		cs.setLogSql(sql);
		return cs;
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {

		CallableStatementLogWrapper cs = new CallableStatementLogWrapper(_connection.prepareCall(sql, resultSetType, resultSetConcurrency));
		cs.setLogSql(sql);
		return cs;
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		
		CallableStatementLogWrapper cs = new CallableStatementLogWrapper(_connection.prepareCall(sql));
		cs.setLogSql(sql);
		return cs;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		PreparedStatementLogWrapper pstmtC = new PreparedStatementLogWrapper(_connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
		pstmtC.setLogSql(sql);
		return pstmtC;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		PreparedStatementLogWrapper pstmtC = new PreparedStatementLogWrapper(_connection.prepareStatement(sql, resultSetType, resultSetConcurrency));
		pstmtC.setLogSql(sql);
		return pstmtC;
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		PreparedStatementLogWrapper pstmtC = new PreparedStatementLogWrapper(_connection.prepareStatement(sql, autoGeneratedKeys));
		pstmtC.setLogSql(sql);
		return pstmtC;

	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		PreparedStatementLogWrapper pstmtC =  new PreparedStatementLogWrapper(_connection.prepareStatement(sql, columnIndexes));
		pstmtC.setLogSql(sql);
		return pstmtC;

	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		PreparedStatementLogWrapper pstmtC =  new PreparedStatementLogWrapper(_connection.prepareStatement(sql, columnNames));
		pstmtC.setLogSql(sql);
		return pstmtC;

	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		PreparedStatementLogWrapper pstmtC =  new PreparedStatementLogWrapper(_connection.prepareStatement(sql));
		pstmtC.setLogSql(sql);
		return pstmtC;
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		_connection.releaseSavepoint(savepoint);
	}

	public void rollback() throws SQLException {
		_connection.rollback();
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		_connection.rollback(savepoint);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		_connection.setAutoCommit(autoCommit);
	}

	public void setCatalog(String catalog) throws SQLException {
		_connection.setCatalog(catalog);
	}

	public void setHoldability(int holdability) throws SQLException {
		_connection.setHoldability(holdability);
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		_connection.setReadOnly(readOnly);
	}

	public Savepoint setSavepoint() throws SQLException {
		return _connection.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		return _connection.setSavepoint(name);
	}

	public void setTransactionIsolation(int level) throws SQLException {
		_connection.setTransactionIsolation(level);
	}

	@SuppressWarnings("rawtypes")
	public void setTypeMap(Map map) throws SQLException {
		_connection.setTypeMap(map);
	}
	
	public String toString(){
		return "LogWrrper(" + _connection.toString() + ")";
	}
}
