package com.bln.framework.persist.jdbc.converter.factory;

import com.bln.framework.persist.jdbc.converter.IConverter;

public interface IConverterFactory {

	/**
	 * �����������ͻ��ʵ��
	 * @param type ��������
	 * @return �������͵�ת����
	 */
	public abstract IConverter getInstance(int type);

}