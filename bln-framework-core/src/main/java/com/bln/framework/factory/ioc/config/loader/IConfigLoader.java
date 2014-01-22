package com.bln.framework.factory.ioc.config.loader;

import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;

/**
 * 配置文件加载器
 * @param <P> 加载对象的类型
 */
public interface IConfigLoader {

	/**
	 * 加载配置文件
	 * @param p 加载原料类型
	 * @return 配置文件
	 * @throws Throwable
	 */
	public abstract IBLNFactoryConfig load(String configUrl) throws Throwable;

}