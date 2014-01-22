package com.bln.framework.factory.ioc.config.loader;

import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;

/**
 * �����ļ�������
 * @param <P> ���ض��������
 */
public interface IConfigLoader {

	/**
	 * ���������ļ�
	 * @param p ����ԭ������
	 * @return �����ļ�
	 * @throws Throwable
	 */
	public abstract IBLNFactoryConfig load(String configUrl) throws Throwable;

}