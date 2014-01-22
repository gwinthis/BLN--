package com.bln.framework.persist.jdbc.converter.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public interface IConverterUtil {

	/**
	 * 把字符型数据转换成指定数据类型的数据
	 * @param val 字符型数据
	 * @param type 要转换成的数据类型
	 * @return 转换后的数据
	 * @throws ParseException 
	 */
	public abstract Object convert(String val, int type);

	/**
	 * 把字符型数据转换成指定数据类型的数据
	 * @param val 字符型数据
	 * @param type 要转换成的数据类型
	 * @return 转换后的数据
	 * @throws ParseException 
	 */
	public abstract String toString(Object val, int type);

	/**
	 * 从ResultSet中获取值，并转换成字符串
	 * @param rs 结果集
	 * @param colName 字段名
	 * @param type 字段类型
	 * @return 字符串
	 * @throws SQLException
	 */
	public String toString(ResultSet rs, String colName, int type)throws SQLException ;

	/**
	 * 从ResultSet中获取值，并转换成字符串
	 * @param rs 结果集
	 * @param colIdx 字段索引值
	 * @param type 字段类型
	 * @return 字符串
	 * @throws SQLException
	 */
	public String toString(ResultSet rs, int colIdx, int type)throws SQLException ;

}