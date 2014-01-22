package com.bln.framework.factory.ioc.config.parser.extend;

import java.io.IOException;

import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.parser.IConfigParser;

public interface IExtendsParser {

	/**
	 * Ω‚Œˆ≈‰÷√Œƒº˛
	 * @param config
	 * @return
	 * @throws IOException 
	 */
	public abstract IBLNFactoryConfig parse() throws IOException;

	/**
	 * @return the configParser
	 */
	public abstract IConfigParser getConfigParser();

	/**
	 * @param configParser the configParser to set
	 */
	public abstract void setConfigParser(IConfigParser configParser);

	/**
	 * @return the theConfigUrl
	 */
	public abstract String getTheConfigUrl();

	/**
	 * @param theConfigUrl the theConfigUrl to set
	 */
	public abstract void setTheConfigUrl(String theConfigUrl);

	/**
	 * @return the theConfig
	 */
	public abstract IBLNFactoryConfig getTheConfig();

	/**
	 * @param theConfig the theConfig to set
	 */
	public abstract void setTheConfig(IBLNFactoryConfig theConfig);

}