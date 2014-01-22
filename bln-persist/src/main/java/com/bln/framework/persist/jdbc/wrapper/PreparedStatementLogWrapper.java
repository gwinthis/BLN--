/**
 * @author Gengw
 * Created at 2008-05-05
 */
package com.bln.framework.persist.jdbc.wrapper;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import com.bln.framework.persist.jdbc.wrapper.log.LogSql;

/**
 * PreparedStatementÕ‚∏≤¿‡
 */
public class PreparedStatementLogWrapper implements PreparedStatement{

	private PreparedStatement _preparedStatement = null;
	
	private LogSql log = new LogSql();

	private String preparedSql = null;
	
	public void setLogSql(String sql){
		this.preparedSql = sql;
		log.initSQL(preparedSql);
	}
	
	public PreparedStatementLogWrapper(PreparedStatement pStatement){
		_preparedStatement = pStatement;
	}
	
	public void addBatch() throws SQLException {
		log.addSQL(log.getSql());
		log.initSQL(preparedSql);
		_preparedStatement.addBatch();
	}

	public void addBatch(String arg0) throws SQLException {
		log.initSQL(arg0);
		_preparedStatement.addBatch(arg0);
	}

	public void cancel() throws SQLException {
		_preparedStatement.cancel();
	}

	public void clearBatch() throws SQLException {
		log.clearSQL();
		_preparedStatement.clearBatch();
	}

	public void clearParameters() throws SQLException {
		_preparedStatement.clearParameters();
	}

	public void clearWarnings() throws SQLException {
		_preparedStatement.clearWarnings();
	}

	public void close() throws SQLException {
		_preparedStatement.close();
	}

	public boolean execute() throws SQLException {
		log.logSQL();
		return _preparedStatement.execute();
	}

	public boolean execute(String arg0, int arg1) throws SQLException {
		log.logSQL(arg0);
		return _preparedStatement.execute(arg0, arg1);
	}

	public boolean execute(String arg0, int[] arg1) throws SQLException {
		log.logSQL(arg0);
		return _preparedStatement.execute(arg0, arg1);
	}

	public boolean execute(String arg0, String[] arg1) throws SQLException {
		log.logSQL(arg0);
		return _preparedStatement.execute(arg0, arg1);
	}

	public boolean execute(String arg0) throws SQLException {
		log.logSQL(arg0);
		return _preparedStatement.execute(arg0);
	}

	public int[] executeBatch() throws SQLException {
		log.logSQLS();
		return _preparedStatement.executeBatch();
	}

	public ResultSet executeQuery() throws SQLException {
		log.logSQL();
		return new ResultSetLogWrapper(_preparedStatement.executeQuery());
	}

	public ResultSet executeQuery(String arg0) throws SQLException {
		return new ResultSetLogWrapper(_preparedStatement.executeQuery(arg0));
	}

	public int executeUpdate() throws SQLException {
		log.logSQL();
		return _preparedStatement.executeUpdate();
	}

	public int executeUpdate(String arg0, int arg1) throws SQLException {
		log.logSQL(arg0);
		return _preparedStatement.executeUpdate(arg0, arg1);
	}

	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		log.logSQL(arg0);
		return _preparedStatement.executeUpdate(arg0, arg1);
	}

	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		log.logSQL(arg0);
		return _preparedStatement.executeUpdate(arg0, arg1);
	}

	public int executeUpdate(String arg0) throws SQLException {
		log.logSQL(arg0);
		return _preparedStatement.executeUpdate(arg0);
	}

	public Connection getConnection() throws SQLException {
		return _preparedStatement.getConnection();
	}

	public int getFetchDirection() throws SQLException {
		return _preparedStatement.getFetchDirection();
	}

	public int getFetchSize() throws SQLException {
		return _preparedStatement.getFetchSize();
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		return new ResultSetLogWrapper(_preparedStatement.getGeneratedKeys());
	}

	public int getMaxFieldSize() throws SQLException {
		return _preparedStatement.getMaxFieldSize();
	}

	public int getMaxRows() throws SQLException {
		return _preparedStatement.getMaxRows();
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return _preparedStatement.getMetaData();
	}

	public boolean getMoreResults() throws SQLException {
		return _preparedStatement.getMoreResults();
	}

	public boolean getMoreResults(int arg0) throws SQLException {
		return _preparedStatement.getMoreResults(arg0);
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		return _preparedStatement.getParameterMetaData();
	}

	public int getQueryTimeout() throws SQLException {
		return _preparedStatement.getQueryTimeout();
	}

	public ResultSet getResultSet() throws SQLException {
		return new ResultSetLogWrapper(_preparedStatement.getResultSet());
	}

	public int getResultSetConcurrency() throws SQLException {
		return _preparedStatement.getResultSetConcurrency();
	}

	public int getResultSetHoldability() throws SQLException {
		return _preparedStatement.getResultSetHoldability();
	}

	public int getResultSetType() throws SQLException {
		return _preparedStatement.getResultSetType();
	}

	public int getUpdateCount() throws SQLException {
		return _preparedStatement.getUpdateCount();
	}

	public SQLWarning getWarnings() throws SQLException {
		return _preparedStatement.getWarnings();
	}

	public void setArray(int arg0, Array arg1) throws SQLException {
		_preparedStatement.setArray(arg0, arg1);
		log.setParam(arg1);
	}

	public void setAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
		_preparedStatement.setAsciiStream(arg0, arg1, arg2);
		log.setParam(arg1);
	}

	public void setBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		_preparedStatement.setBigDecimal(arg0, arg1);
		log.setParam(arg1);
	}

	public void setBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
		_preparedStatement.setBinaryStream(arg0, arg1, arg2);
		log.setParam(arg1);
	}

	public void setBlob(int arg0, Blob arg1) throws SQLException {
		_preparedStatement.setBlob(arg0, arg1);
		log.setParam(arg1);
	}

	public void setBoolean(int arg0, boolean arg1) throws SQLException {
		_preparedStatement.setBoolean(arg0, arg1);
		log.setParam(arg1);
	}

	public void setByte(int arg0, byte arg1) throws SQLException {
		_preparedStatement.setByte(arg0, arg1);
		log.setParam(arg1);
	}

	public void setBytes(int arg0, byte[] arg1) throws SQLException {
		_preparedStatement.setBytes(arg0, arg1);
		log.setParam(arg1);
	}

	public void setCharacterStream(int arg0, Reader arg1, int arg2) throws SQLException {
		_preparedStatement.setCharacterStream(arg0, arg1, arg2);
		log.setParam(arg1);
	}

	public void setClob(int arg0, Clob arg1) throws SQLException {
		_preparedStatement.setClob(arg0, arg1);
		log.setParam(arg1);
	}

	public void setCursorName(String arg0) throws SQLException {
		_preparedStatement.setCursorName(arg0);
	}

	public void setDate(int arg0, Date arg1, Calendar arg2) throws SQLException {
		_preparedStatement.setDate(arg0, arg1, arg2);
		log.setParam(arg1);
	}

	public void setDate(int arg0, Date arg1) throws SQLException {
		_preparedStatement.setDate(arg0, arg1);
		log.setParam(arg1);
	}

	public void setDouble(int arg0, double arg1) throws SQLException {
		_preparedStatement.setDouble(arg0, arg1);
		log.setParam(arg1);
	}

	public void setEscapeProcessing(boolean arg0) throws SQLException {
		_preparedStatement.setEscapeProcessing(arg0);
	}

	public void setFetchDirection(int arg0) throws SQLException {
		_preparedStatement.setFetchDirection(arg0);
	}

	public void setFetchSize(int arg0) throws SQLException {
		_preparedStatement.setFetchSize(arg0);
	}

	public void setFloat(int arg0, float arg1) throws SQLException {
		_preparedStatement.setFloat(arg0, arg1);
		log.setParam(arg1);
	}

	public void setInt(int arg0, int arg1) throws SQLException {
		_preparedStatement.setInt(arg0, arg1);
		log.setParam(arg1);
	}

	public void setLong(int arg0, long arg1) throws SQLException {
		_preparedStatement.setLong(arg0, arg1);
		log.setParam(arg1);
	}

	public void setMaxFieldSize(int arg0) throws SQLException {
		_preparedStatement.setMaxFieldSize(arg0);
	}

	public void setMaxRows(int arg0) throws SQLException {
		_preparedStatement.setMaxRows(arg0);
	}

	public void setNull(int arg0, int arg1, String arg2) throws SQLException {
		_preparedStatement.setNull(arg0, arg1, arg2);
		Object obj = null;
		log.setParam(obj);

	}

	public void setNull(int arg0, int arg1) throws SQLException {
		_preparedStatement.setNull(arg0, arg1);
		Object obj = null;
		log.setParam(obj);

	}

	public void setObject(int arg0, Object arg1, int arg2, int arg3) throws SQLException {
		_preparedStatement.setObject(arg0, arg1, arg2, arg3);
		log.setParam(arg1);
	}

	public void setObject(int arg0, Object arg1, int arg2) throws SQLException {
		_preparedStatement.setObject(arg0, arg1, arg2);
		log.setParam(arg1);
	}

	public void setObject(int arg0, Object arg1) throws SQLException {
		_preparedStatement.setObject(arg0, arg1);
		log.setParam(arg1);
	}

	public void setQueryTimeout(int arg0) throws SQLException {
		_preparedStatement.setQueryTimeout(arg0);
	}

	public void setRef(int arg0, Ref arg1) throws SQLException {
		_preparedStatement.setRef(arg0, arg1);
	}

	public void setShort(int arg0, short arg1) throws SQLException {
		_preparedStatement.setShort(arg0, arg1);
		log.setParam(arg1);

	}

	public void setString(int arg0, String arg1) throws SQLException {
		_preparedStatement.setString(arg0, arg1);
		log.setParam(arg1);
	}

	public void setTime(int arg0, Time arg1, Calendar arg2) throws SQLException {
		_preparedStatement.setTime(arg0, arg1, arg2);
		log.setParam(arg1);
	}

	public void setTime(int arg0, Time arg1) throws SQLException {
		_preparedStatement.setTime(arg0, arg1);
		log.setParam(arg1);
	}

	public void setTimestamp(int arg0, Timestamp arg1, Calendar arg2) throws SQLException {
		_preparedStatement.setTimestamp(arg0, arg1, arg2);
		log.setParam(arg1);
	}

	public void setTimestamp(int arg0, Timestamp arg1) throws SQLException {
		_preparedStatement.setTimestamp(arg0, arg1);
		log.setParam(arg1);
	}

	@SuppressWarnings("deprecation")
	public void setUnicodeStream(int arg0, InputStream arg1, int arg2) throws SQLException {
		_preparedStatement.setUnicodeStream(arg0, arg1, arg2);
		log.setParam(arg1);
	}

	public void setURL(int arg0, URL arg1) throws SQLException {
		_preparedStatement.setURL(arg0, arg1);
		log.setParam(arg1);
	}
}
