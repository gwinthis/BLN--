package com.bln.framework.persist.row.enhance.stru;

import java.util.Map;

import com.bln.framework.persist.jdbc.converter.util.IConverterUtil;
import com.bln.framework.persist.row.enhance.IEnhanceRow;

public interface IStruRow extends IEnhanceRow{

	/**
	 * 获得字段值，并转换成结构中的JDBC所要求的类型
	 * @param fieldNm 字段名称
	 * @return 转换后的值
	 */
	public abstract Object getValueAsObject(String fieldNm);

	/**
	 * @return the columnTypes
	 */
	public abstract Map<String, Integer> getColumnTypes();

	/**
	 * 设置字段类型
	 * @param columnTypes the columnTypes to set
	 */
	public abstract void setColumnTypes(Map<String, Integer> columnTypes);

	/**
	 * @return the converterUtil
	 */
	public abstract IConverterUtil getConverterUtil();

	/**
	 * 设置值转换器
	 * @param converterUtil the converterUtil to set
	 */
	public abstract void setConverterUtil(IConverterUtil converterUtil);

	/**
	 * 从值对象中设置值
	 * @param fieldNm 字段名称
	 * @param value 字段值
	 */
	public void setValueFromObject(String fieldNm, Object value);

}