/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.jdbc.converter;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bln.framework.persist.jdbc.converter.ConverterBase;
import com.bln.framework.persist.jdbc.converter.IConverter;

/**
 * ×Ö·ûÀàÐÍ×ª»»Æ÷
 */
public class CharConverter extends ConverterBase implements IConverter{

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#convert(java.lang.String)
	 */
	public Object convert(String val) {
		return val;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.lang.Object)
	 */
	public String toString(Object obj) {
		
		String val = null;
		if(obj != null){
			val = obj.toString();
		}
		return val;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.sql.ResultSet, java.lang.String)
	 */
	public String toString(ResultSet rs, String colName) throws SQLException {
		return toString(rs.getString(colName));
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.sql.ResultSet, int)
	 */
	public String toString(ResultSet rs, int colIdx) throws SQLException {
		return toString(rs.getString(colIdx));
	}

}
