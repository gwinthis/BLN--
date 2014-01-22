package com.bln.framework.persist.jdbc.converter.factory;

import com.bln.framework.persist.jdbc.converter.IConverter;

public interface IConverterFactory {

	/**
	 * 根据数据类型获得实例
	 * @param type 数据类型
	 * @return 数据类型的转换器
	 */
	public abstract IConverter getInstance(int type);

}