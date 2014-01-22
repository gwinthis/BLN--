package com.bln.framework.factory.ioc.config.parser;

import java.io.IOException;

import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;

public interface IConfigParser {

	/**
	 * 解析成配置对象
	 * @param configUrl
	 * @return
	 * @throws IOException 
	 */
	public abstract IBLNFactoryConfig parse(String configUrl) throws IOException;

}