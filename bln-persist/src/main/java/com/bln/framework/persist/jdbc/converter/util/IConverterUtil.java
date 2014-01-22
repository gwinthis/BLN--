package com.bln.framework.persist.jdbc.converter.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public interface IConverterUtil {

	/**
	 * ���ַ�������ת����ָ���������͵�����
	 * @param val �ַ�������
	 * @param type Ҫת���ɵ���������
	 * @return ת���������
	 * @throws ParseException 
	 */
	public abstract Object convert(String val, int type);

	/**
	 * ���ַ�������ת����ָ���������͵�����
	 * @param val �ַ�������
	 * @param type Ҫת���ɵ���������
	 * @return ת���������
	 * @throws ParseException 
	 */
	public abstract String toString(Object val, int type);

	/**
	 * ��ResultSet�л�ȡֵ����ת�����ַ���
	 * @param rs �����
	 * @param colName �ֶ���
	 * @param type �ֶ�����
	 * @return �ַ���
	 * @throws SQLException
	 */
	public String toString(ResultSet rs, String colName, int type)throws SQLException ;

	/**
	 * ��ResultSet�л�ȡֵ����ת�����ַ���
	 * @param rs �����
	 * @param colIdx �ֶ�����ֵ
	 * @param type �ֶ�����
	 * @return �ַ���
	 * @throws SQLException
	 */
	public String toString(ResultSet rs, int colIdx, int type)throws SQLException ;

}