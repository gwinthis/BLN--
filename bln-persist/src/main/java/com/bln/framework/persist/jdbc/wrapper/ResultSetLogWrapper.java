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
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * ResultSetÕ‚∏≤¿‡
 *
 */
public class ResultSetLogWrapper implements ResultSet{

	private ResultSet _resultSet = null;

	public ResultSetLogWrapper(ResultSet resultSet){
		_resultSet = resultSet;
	}

	public boolean absolute(int arg0) throws SQLException {
		return _resultSet.absolute(arg0);
	}

	public void afterLast() throws SQLException {
		_resultSet.afterLast();
	}

	public void beforeFirst() throws SQLException {
		_resultSet.beforeFirst();
	}

	public void cancelRowUpdates() throws SQLException {
		_resultSet.cancelRowUpdates();
	}

	public void clearWarnings() throws SQLException {
		_resultSet.clearWarnings();
	}

	public void close() throws SQLException {
		_resultSet.close();
	}

	public void deleteRow() throws SQLException {
		_resultSet.deleteRow();
	}

	public int findColumn(String arg0) throws SQLException {
		return _resultSet.findColumn(arg0);
	}

	public boolean first() throws SQLException {
		return _resultSet.first();
	}

	public Array getArray(int arg0) throws SQLException {
		return _resultSet.getArray(arg0);
	}

	public Array getArray(String arg0) throws SQLException {
		return _resultSet.getArray(arg0);
	}

	public InputStream getAsciiStream(int arg0) throws SQLException {
		return _resultSet.getAsciiStream(arg0);
	}

	public InputStream getAsciiStream(String arg0) throws SQLException {
		return _resultSet.getAsciiStream(arg0);
	}

	@SuppressWarnings("deprecation")
	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		return _resultSet.getBigDecimal(arg0, arg1);
	}

	public BigDecimal getBigDecimal(int arg0) throws SQLException {
		return _resultSet.getBigDecimal(arg0);
	}

	@SuppressWarnings("deprecation")
	public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
		return _resultSet.getBigDecimal(arg0, arg1);
	}

	public BigDecimal getBigDecimal(String arg0) throws SQLException {
		return _resultSet.getBigDecimal(arg0);
	}

	public InputStream getBinaryStream(int arg0) throws SQLException {
		return _resultSet.getBinaryStream(arg0);
	}

	public InputStream getBinaryStream(String arg0) throws SQLException {
		return _resultSet.getBinaryStream(arg0);
	}

	public Blob getBlob(int arg0) throws SQLException {
		return _resultSet.getBlob(arg0);
	}

	public Blob getBlob(String arg0) throws SQLException {
		return _resultSet.getBlob(arg0);
	}

	public boolean getBoolean(int arg0) throws SQLException {
		return _resultSet.getBoolean(arg0);
	}

	public boolean getBoolean(String arg0) throws SQLException {
		return _resultSet.getBoolean(arg0);
	}

	public byte getByte(int arg0) throws SQLException {
		return _resultSet.getByte(arg0);
	}

	public byte getByte(String arg0) throws SQLException {
		return _resultSet.getByte(arg0);
	}

	public byte[] getBytes(int arg0) throws SQLException {
		return _resultSet.getBytes(arg0);
	}

	public byte[] getBytes(String arg0) throws SQLException {
		return _resultSet.getBytes(arg0);
	}

	public Reader getCharacterStream(int arg0) throws SQLException {
		return _resultSet.getCharacterStream(arg0);
	}

	public Reader getCharacterStream(String arg0) throws SQLException {
		return _resultSet.getCharacterStream(arg0);
	}

	public Clob getClob(int arg0) throws SQLException {
		return _resultSet.getClob(arg0);
	}

	public Clob getClob(String arg0) throws SQLException {
		return _resultSet.getClob(arg0);
	}

	public int getConcurrency() throws SQLException {
		return _resultSet.getConcurrency();
	}

	public String getCursorName() throws SQLException {
		return _resultSet.getCursorName();
	}

	public Date getDate(int arg0, Calendar arg1) throws SQLException {
		return _resultSet.getDate(arg0, arg1);
	}

	public Date getDate(int arg0) throws SQLException {
		return _resultSet.getDate(arg0);
	}

	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		return _resultSet.getDate(arg0, arg1);
	}

	public Date getDate(String arg0) throws SQLException {
		return _resultSet.getDate(arg0);
	}

	public double getDouble(int arg0) throws SQLException {
		return _resultSet.getDouble(arg0);
	}

	public double getDouble(String arg0) throws SQLException {
		return _resultSet.getDouble(arg0);
	}

	public int getFetchDirection() throws SQLException {
		return _resultSet.getFetchDirection();
	}

	public int getFetchSize() throws SQLException {
		return _resultSet.getFetchSize();
	}

	public float getFloat(int arg0) throws SQLException {
		return _resultSet.getFloat(arg0);
	}

	public float getFloat(String arg0) throws SQLException {
		return _resultSet.getFloat(arg0);
	}

	public int getInt(int arg0) throws SQLException {
		return _resultSet.getInt(arg0);
	}

	public int getInt(String arg0) throws SQLException {
		return _resultSet.getInt(arg0);
	}

	public long getLong(int arg0) throws SQLException {
		return _resultSet.getLong(arg0);
	}

	public long getLong(String arg0) throws SQLException {
		return _resultSet.getLong(arg0);
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return _resultSet.getMetaData();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getObject(int arg0, Map arg1) throws SQLException {
		return _resultSet.getObject(arg0, arg1);
	}

	public Object getObject(int arg0) throws SQLException {
		return _resultSet.getObject(arg0);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getObject(String arg0, Map arg1) throws SQLException {
		return _resultSet.getObject(arg0, arg1);
	}

	public Object getObject(String arg0) throws SQLException {
		return _resultSet.getObject(arg0);
	}

	public Ref getRef(int arg0) throws SQLException {
		return _resultSet.getRef(arg0);
	}

	public Ref getRef(String arg0) throws SQLException {
		return _resultSet.getRef(arg0);
	}

	public int getRow() throws SQLException {
		return _resultSet.getRow();
	}

	public short getShort(int arg0) throws SQLException {
		return _resultSet.getShort(arg0);
	}

	public short getShort(String arg0) throws SQLException {
		return _resultSet.getShort(arg0);
	}

	public Statement getStatement() throws SQLException {
		return _resultSet.getStatement();
	}

	public String getString(int arg0) throws SQLException {
		return _resultSet.getString(arg0);
	}

	public String getString(String arg0) throws SQLException {
		return _resultSet.getString(arg0);
	}

	public Time getTime(int arg0, Calendar arg1) throws SQLException {
		return _resultSet.getTime(arg0, arg1);
	}

	public Time getTime(int arg0) throws SQLException {
		return _resultSet.getTime(arg0);
	}

	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		return _resultSet.getTime(arg0, arg1);
	}

	public Time getTime(String arg0) throws SQLException {
		return _resultSet.getTime(arg0);
	}

	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		return _resultSet.getTimestamp(arg0, arg1);
	}

	public Timestamp getTimestamp(int arg0) throws SQLException {
		return _resultSet.getTimestamp(arg0);
	}

	public Timestamp getTimestamp(String arg0, Calendar arg1) throws SQLException {
		return _resultSet.getTimestamp(arg0, arg1);
	}

	public Timestamp getTimestamp(String arg0) throws SQLException {
		return _resultSet.getTimestamp(arg0);
	}

	public int getType() throws SQLException {
		return _resultSet.getType();
	}

	@SuppressWarnings("deprecation")
	public InputStream getUnicodeStream(int arg0) throws SQLException {
		return _resultSet.getUnicodeStream(arg0);
	}

	@SuppressWarnings("deprecation")
	public InputStream getUnicodeStream(String arg0) throws SQLException {
		return _resultSet.getUnicodeStream(arg0);
	}

	public URL getURL(int arg0) throws SQLException {
		return _resultSet.getURL(arg0);
	}

	public URL getURL(String arg0) throws SQLException {
		return _resultSet.getURL(arg0);
	}

	public SQLWarning getWarnings() throws SQLException {
		return _resultSet.getWarnings();
	}

	public void insertRow() throws SQLException {
		_resultSet.insertRow();
	}

	public boolean isAfterLast() throws SQLException {
		return _resultSet.isAfterLast();
	}

	public boolean isBeforeFirst() throws SQLException {
		return _resultSet.isBeforeFirst();
	}

	public boolean isFirst() throws SQLException {
		return _resultSet.isFirst();
	}

	public boolean isLast() throws SQLException {
		return _resultSet.isLast();
	}

	public boolean last() throws SQLException {
		return _resultSet.last();
	}

	public void moveToCurrentRow() throws SQLException {
		_resultSet.moveToCurrentRow();
	}

	public void moveToInsertRow() throws SQLException {
		_resultSet.moveToInsertRow();
	}

	public boolean next() throws SQLException {
		return _resultSet.next();
	}

	public boolean previous() throws SQLException {
		return _resultSet.previous();
	}

	public void refreshRow() throws SQLException {
		_resultSet.refreshRow();
	}

	public boolean relative(int arg0) throws SQLException {
		return _resultSet.relative(arg0);
	}

	public boolean rowDeleted() throws SQLException {
		return _resultSet.rowDeleted();
	}

	public boolean rowInserted() throws SQLException {
		return _resultSet.rowInserted();
	}

	public boolean rowUpdated() throws SQLException {
		return _resultSet.rowUpdated();
	}

	public void setFetchDirection(int arg0) throws SQLException {
		_resultSet.setFetchDirection(arg0);
	}

	public void setFetchSize(int arg0) throws SQLException {
		_resultSet.setFetchSize(arg0);
	}

	public void updateArray(int arg0, Array arg1) throws SQLException {
		_resultSet.updateArray(arg0, arg1);
	}

	public void updateArray(String arg0, Array arg1) throws SQLException {
		_resultSet.updateArray(arg0, arg1);
	}

	public void updateAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
		_resultSet.updateAsciiStream(arg0, arg1, arg2);
	}

	public void updateAsciiStream(String arg0, InputStream arg1, int arg2) throws SQLException {
		_resultSet.updateAsciiStream(arg0, arg1, arg2);
	}

	public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		_resultSet.updateBigDecimal(arg0, arg1);
	}

	public void updateBigDecimal(String arg0, BigDecimal arg1) throws SQLException {
		_resultSet.updateBigDecimal(arg0, arg1);
	}

	public void updateBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
		_resultSet.updateBinaryStream(arg0, arg1, arg2);
	}

	public void updateBinaryStream(String arg0, InputStream arg1, int arg2) throws SQLException {
		_resultSet.updateBinaryStream(arg0, arg1, arg2);
	}

	public void updateBlob(int arg0, Blob arg1) throws SQLException {
		_resultSet.updateBlob(arg0, arg1);
	}

	public void updateBlob(String arg0, Blob arg1) throws SQLException {
		_resultSet.updateBlob(arg0, arg1);
	}

	public void updateBoolean(int arg0, boolean arg1) throws SQLException {
		_resultSet.updateBoolean(arg0, arg1);
	}

	public void updateBoolean(String arg0, boolean arg1) throws SQLException {
		_resultSet.updateBoolean(arg0, arg1);
	}

	public void updateByte(int arg0, byte arg1) throws SQLException {
		_resultSet.updateByte(arg0, arg1);
	}

	public void updateByte(String arg0, byte arg1) throws SQLException {
		_resultSet.updateByte(arg0, arg1);
	}

	public void updateBytes(int arg0, byte[] arg1) throws SQLException {
		_resultSet.updateBytes(arg0, arg1);
	}

	public void updateBytes(String arg0, byte[] arg1) throws SQLException {
		_resultSet.updateBytes(arg0, arg1);
	}

	public void updateCharacterStream(int arg0, Reader arg1, int arg2) throws SQLException {
		_resultSet.updateCharacterStream(arg0, arg1, arg2);
	}

	public void updateCharacterStream(String arg0, Reader arg1, int arg2) throws SQLException {
		_resultSet.updateCharacterStream(arg0, arg1, arg2);
	}

	public void updateClob(int arg0, Clob arg1) throws SQLException {
		_resultSet.updateClob(arg0, arg1);
	}

	public void updateClob(String arg0, Clob arg1) throws SQLException {
		_resultSet.updateClob(arg0, arg1);
	}

	public void updateDate(int arg0, Date arg1) throws SQLException {
		_resultSet.updateDate(arg0, arg1);
	}

	public void updateDate(String arg0, Date arg1) throws SQLException {
		_resultSet.updateDate(arg0, arg1);
	}

	public void updateDouble(int arg0, double arg1) throws SQLException {
		_resultSet.updateDouble(arg0, arg1);
	}

	public void updateDouble(String arg0, double arg1) throws SQLException {
		_resultSet.updateDouble(arg0, arg1);
	}

	public void updateFloat(int arg0, float arg1) throws SQLException {
		_resultSet.updateFloat(arg0, arg1);
	}

	public void updateFloat(String arg0, float arg1) throws SQLException {
		_resultSet.updateFloat(arg0, arg1);
	}

	public void updateInt(int arg0, int arg1) throws SQLException {
		_resultSet.updateInt(arg0, arg1);
	}

	public void updateInt(String arg0, int arg1) throws SQLException {
		_resultSet.updateInt(arg0, arg1);
	}

	public void updateLong(int arg0, long arg1) throws SQLException {
		_resultSet.updateLong(arg0, arg1);
	}

	public void updateLong(String arg0, long arg1) throws SQLException {
		_resultSet.updateLong(arg0, arg1);
	}

	public void updateNull(int arg0) throws SQLException {
		_resultSet.updateNull(arg0);
	}

	public void updateNull(String arg0) throws SQLException {
		_resultSet.updateNull(arg0);
	}

	public void updateObject(int arg0, Object arg1, int arg2) throws SQLException {
		_resultSet.updateObject(arg0, arg1, arg2);
	}

	public void updateObject(int arg0, Object arg1) throws SQLException {
		_resultSet.updateObject(arg0, arg1);
	}

	public void updateObject(String arg0, Object arg1, int arg2) throws SQLException {
		_resultSet.updateObject(arg0, arg1, arg2);
	}

	public void updateObject(String arg0, Object arg1) throws SQLException {
		_resultSet.updateObject(arg0, arg1);
	}

	public void updateRef(int arg0, Ref arg1) throws SQLException {
		_resultSet.updateRef(arg0, arg1);
	}

	public void updateRef(String arg0, Ref arg1) throws SQLException {
		_resultSet.updateRef(arg0, arg1);
	}

	public void updateRow() throws SQLException {
		_resultSet.updateRow();
	}

	public void updateShort(int arg0, short arg1) throws SQLException {
		_resultSet.updateShort(arg0, arg1);
	}

	public void updateShort(String arg0, short arg1) throws SQLException {
		_resultSet.updateShort(arg0, arg1);
	}

	public void updateString(int arg0, String arg1) throws SQLException {
		_resultSet.updateString(arg0, arg1);
	}

	public void updateString(String arg0, String arg1) throws SQLException {
		_resultSet.updateString(arg0, arg1);
	}

	public void updateTime(int arg0, Time arg1) throws SQLException {
		_resultSet.updateTime(arg0, arg1);
	}

	public void updateTime(String arg0, Time arg1) throws SQLException {
		_resultSet.updateTime(arg0, arg1);
	}

	public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
		_resultSet.updateTimestamp(arg0, arg1);
	}

	public void updateTimestamp(String arg0, Timestamp arg1) throws SQLException {
		_resultSet.updateTimestamp(arg0, arg1);
	}

	public boolean wasNull() throws SQLException {
		return _resultSet.wasNull();
	}

}
