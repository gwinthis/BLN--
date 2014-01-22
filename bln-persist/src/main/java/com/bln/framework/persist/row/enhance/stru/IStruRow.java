package com.bln.framework.persist.row.enhance.stru;

import java.util.Map;

import com.bln.framework.persist.jdbc.converter.util.IConverterUtil;
import com.bln.framework.persist.row.enhance.IEnhanceRow;

public interface IStruRow extends IEnhanceRow{

	/**
	 * ����ֶ�ֵ����ת���ɽṹ�е�JDBC��Ҫ�������
	 * @param fieldNm �ֶ�����
	 * @return ת�����ֵ
	 */
	public abstract Object getValueAsObject(String fieldNm);

	/**
	 * @return the columnTypes
	 */
	public abstract Map<String, Integer> getColumnTypes();

	/**
	 * �����ֶ�����
	 * @param columnTypes the columnTypes to set
	 */
	public abstract void setColumnTypes(Map<String, Integer> columnTypes);

	/**
	 * @return the converterUtil
	 */
	public abstract IConverterUtil getConverterUtil();

	/**
	 * ����ֵת����
	 * @param converterUtil the converterUtil to set
	 */
	public abstract void setConverterUtil(IConverterUtil converterUtil);

	/**
	 * ��ֵ����������ֵ
	 * @param fieldNm �ֶ�����
	 * @param value �ֶ�ֵ
	 */
	public void setValueFromObject(String fieldNm, Object value);

}