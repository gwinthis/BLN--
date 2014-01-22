/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.jdbc.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * 基于JdbcType的值转换器
 */
public interface IConverter {
	
	/**
	 * 根据字符串转换成对应类型的对象
	 * @param val 字符串
	 * @return 对象
	 */
	public Object convert(String val) throws ParseException;
	
	/**
	 * 根据对象生成字符
	 * @param obj 对象
	 * @return String 
	 */
	public String toString(Object obj);

	/**
	 * 从ResultSet中获取值，并转换成字符串
	 * @param rs 
	 * @param colName
	 * @return
	 */
	public String toString(ResultSet rs, String colName)throws SQLException ;

	/**
	 * 从ResultSet中获取值，并转换成字符串
	 * @param rs 
	 * @param colName
	 * @return
	 */
	public String toString(ResultSet rs, int colIdx)throws SQLException ;

	
}
