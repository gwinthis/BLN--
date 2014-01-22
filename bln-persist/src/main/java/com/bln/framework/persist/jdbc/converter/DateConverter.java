/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.jdbc.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.bln.framework.persist.jdbc.converter.IConverter;
import com.bln.framework.persist.jdbc.converter.exception.ConvertException;

/**
 * 字符类型转换器
 */
public class DateConverter extends DateConverterBase implements IConverter{
	
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#convert(java.lang.String)
	 */
	public Object convert(String val) throws ParseException {
		
		if (StringUtils.isEmpty(val)){
			return null;
		}
		
		if(dateFormats == null || dateFormats.length <= 0){
			throw new ConvertException(" expect dateFormats is not null in class " + this.getClass().getName());
		}
		
		//解析成日期
		Date date = DateUtils.parseDateStrictly(val, dateFormats);
		Timestamp ts = new Timestamp(date.getTime());
		
		//返回时间戳
		return ts;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.lang.Object)
	 */
	public String toString(Object obj) {

		if(obj == null){
			return null;
		}
		
		if(dateFormats == null || dateFormats.length <= 0){
			throw new ConvertException(" expect dateFormats is not null in class " + this.getClass().getName());
		}
		
		Timestamp date = (Timestamp)obj;
		
		String tsStr = DateFormatUtils.format(date, dateFormats[0]);
		return tsStr;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.sql.ResultSet, java.lang.String)
	 */
	public String toString(ResultSet rs, String colName) throws SQLException {
		return toString(rs.getTimestamp(colName));
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.IConverter#toString(java.sql.ResultSet, int)
	 */
	public String toString(ResultSet rs, int colIdx) throws SQLException {
		return toString(rs.getTimestamp(colIdx));
	}
}
