/**
 * @author gengw
 * Created on May 9, 2012
 */
package com.bln.framework.factory.ioc.config.loader;

import java.io.IOException;

import com.bln.framework.base.BaseObj;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.xml.BLNFactoryXmlConfig;

/**
 * ������Ϣ������
 * @param <P> �����ļ����ط�ʽ
 */
public abstract class ConfigLoaderBase extends BaseObj implements IConfigLoader{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.loader.IConfigLoader#load(P)
	 */
	public IBLNFactoryConfig load(String configUrl) throws Throwable{
		
		byte[] bytes = loadConfig(configUrl);
		IBLNFactoryConfig config = new BLNFactoryXmlConfig();
		config.readFromData(bytes);
		
		return config;
		
	}
	
	/**
	 * ���������ļ����������ֽ�����
	 * @param configParam
	 * @return
	 * @throws IOException
	 */
	protected abstract byte[] loadConfig(String configUrl) throws IOException;
	
}
