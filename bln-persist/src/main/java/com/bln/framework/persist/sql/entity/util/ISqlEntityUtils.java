package com.bln.framework.persist.sql.entity.util;

public interface ISqlEntityUtils {

	/**
	 * 构造查询SQL
	 * @param displayTable 要查询的字段所在的表名
	 * @param fromAfter from后的信息
	 * @return SELECT SQL 
	 */
	public abstract String selectSql(String displayTable, String fromAfter);

}