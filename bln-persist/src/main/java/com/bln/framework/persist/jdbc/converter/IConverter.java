/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.jdbc.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * ����JdbcType��ֵת����
 */
public interface IConverter {
	
	/**
	 * �����ַ���ת���ɶ�Ӧ���͵Ķ���
	 * @param val �ַ���
	 * @return ����
	 */
	public Object convert(String val) throws ParseException;
	
	/**
	 * ���ݶ��������ַ�
	 * @param obj ����
	 * @return String 
	 */
	public String toString(Object obj);

	/**
	 * ��ResultSet�л�ȡֵ����ת�����ַ���
	 * @param rs 
	 * @param colName
	 * @return
	 */
	public String toString(ResultSet rs, String colName)throws SQLException ;

	/**
	 * ��ResultSet�л�ȡֵ����ת�����ַ���
	 * @param rs 
	 * @param colName
	 * @return
	 */
	public String toString(ResultSet rs, int colIdx)throws SQLException ;

	
}
