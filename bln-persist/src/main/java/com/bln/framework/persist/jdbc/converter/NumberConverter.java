/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.jdbc.converter;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.jdbc.converter.ConverterBase;
import com.bln.framework.persist.jdbc.converter.IConverter;

/**
 * 数值类型转换器
 */
public class NumberConverter extends ConverterBase implements IConverter{

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#convert(java.lang.String)
	 */
	public Object convert(String val) {
		if (StringUtils.isEmpty(val)){
			return null;
		}
		return new BigDecimal(val);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.lang.Object)
	 */
	public String toString(Object obj) {
		
//		String info = null;
//		if(obj != null){
//			if(obj instanceof BigDecimal){
//				info = 
//			}
//		}
//		return info;
		return obj == null ? null : obj.toString();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.sql.ResultSet, java.lang.String)
	 */
	public String toString(ResultSet rs, String colName) throws SQLException {
		return toString(rs.getBigDecimal(colName));
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.sql.ResultSet, int)
	 */
	public String toString(ResultSet rs, int colIdx) throws SQLException {
		return toString(rs.getBigDecimal(colIdx));
	}

}
