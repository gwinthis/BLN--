package com.bln.framework.factory.ioc.center.loader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.config.loader.IConfigLoader;

public interface IFactoryCenterLoader {

	/**
	 * 加载所有工厂
	 * @param configLoaders 配置文件加载器
	 * @param factoryCache TODO
	 * @return 所有工厂
	 * @throws IOException 
	 */
	public abstract void loadAllFactory(
			List<IConfigLoader> configLoaders, Map<String, IBLNFactory> factoryCache) throws IOException;

}