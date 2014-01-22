package com.bln.framework.factory.ioc.config.parser;

import java.io.IOException;

import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;

public interface IConfigParser {

	/**
	 * ���������ö���
	 * @param configUrl
	 * @return
	 * @throws IOException 
	 */
	public abstract IBLNFactoryConfig parse(String configUrl) throws IOException;

}