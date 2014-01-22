/**
 * @author gengw
 * Created on Apr 13, 2012
 */
package com.bln.framework.persist.jdbc;

/**
 * Jdbc类型枚举
 */
public enum EJdbcType {
	
	/**
	 * 枚举值
	 */
	BIT(-7), TINYINT(-6), SMALLINT(5), INTEGER(4), BIGINT(-5), FLOAT(6), REAL(7), DOUBLE(8), NUMERIC(2), DECIMAL(3), CHAR(1), VARCHAR(12), LONGVARCHAR(-1), DATE(91), TIME(92), TIMESTAMP(93), BINARY(-2), VARBINARY(-3), LONGVARBINARY(-4), NULL(0), OTHER(1111), BLOB(2004), CLOB(2005);
	
	/**
	 * 构造函数
	 * @param typeValue 类型所对应的值
	 */
	private EJdbcType(int typeValue){
		this.typeValue = typeValue;
	}
	
	/**
	 * 类型所对应的值
	 */
	public int typeValue;
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		return Integer.toString(typeValue);
	}
	
	/**
	 * 获得值
	 * @param val
	 * @return
	 */
	public static String toString(int val){
		EJdbcType[] jdbcTypes =  EJdbcType.values();
		
		String info = "";
		for (EJdbcType jdbcType : jdbcTypes){
			if (jdbcType.typeValue == val){
				info = jdbcType.name();
				break;
			}
		}
		return info;
	}
}
