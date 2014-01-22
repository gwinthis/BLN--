package com.bln.framework.factory.ioc.center.loader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.config.loader.IConfigLoader;

public interface IFactoryCenterLoader {

	/**
	 * �������й���
	 * @param configLoaders �����ļ�������
	 * @param factoryCache TODO
	 * @return ���й���
	 * @throws IOException 
	 */
	public abstract void loadAllFactory(
			List<IConfigLoader> configLoaders, Map<String, IBLNFactory> factoryCache) throws IOException;

}