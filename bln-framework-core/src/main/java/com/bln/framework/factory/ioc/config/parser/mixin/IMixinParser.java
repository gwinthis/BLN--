package com.bln.framework.factory.ioc.config.parser.mixin;

import java.io.IOException;

import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.parser.IConfigParser;

public interface IMixinParser {

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#parse()
	 */
	public abstract IBLNFactoryConfig parse() throws IOException;

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#getConfigParser()
	 */
	public abstract IConfigParser getConfigParser();

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#setConfigParser(com.bln.framework.factory.ioc.config.parser.IConfigParser)
	 */
	public abstract void setConfigParser(IConfigParser configParser);

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#getTheConfigUrl()
	 */
	public abstract String getTheConfigUrl();

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#setTheConfigUrl(java.lang.String)
	 */
	public abstract void setTheConfigUrl(String theConfigUrl);

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#getTheConfig()
	 */
	public abstract IBLNFactoryConfig getTheConfig();

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#setTheConfig(com.bln.framework.factory.ioc.config.IBLNFactoryConfig)
	 */
	public abstract void setTheConfig(IBLNFactoryConfig theConfig);

}