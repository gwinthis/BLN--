/**
 * @author gengw
 * Created on Apr 13, 2012
 */
package com.bln.framework.persist.jdbc;

/**
 * Jdbc����ö��
 */
public enum EJdbcType {
	
	/**
	 * ö��ֵ
	 */
	BIT(-7), TINYINT(-6), SMALLINT(5), INTEGER(4), BIGINT(-5), FLOAT(6), REAL(7), DOUBLE(8), NUMERIC(2), DECIMAL(3), CHAR(1), VARCHAR(12), LONGVARCHAR(-1), DATE(91), TIME(92), TIMESTAMP(93), BINARY(-2), VARBINARY(-3), LONGVARBINARY(-4), NULL(0), OTHER(1111), BLOB(2004), CLOB(2005);
	
	/**
	 * ���캯��
	 * @param typeValue ��������Ӧ��ֵ
	 */
	private EJdbcType(int typeValue){
		this.typeValue = typeValue;
	}
	
	/**
	 * ��������Ӧ��ֵ
	 */
	public int typeValue;
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString(){
		return Integer.toString(typeValue);
	}
	
	/**
	 * ���ֵ
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
