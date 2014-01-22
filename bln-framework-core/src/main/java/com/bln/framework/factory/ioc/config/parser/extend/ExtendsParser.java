/**
 * @author gengw
 * Created on Nov 30, 2012
 */
package com.bln.framework.factory.ioc.config.parser.extend;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.bln.framework.factory.exception.ExtendsCircleException;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.parser.IConfigParser;

/**
 * 解析继承
 */
public class ExtendsParser implements IExtendsParser {
	
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(ExtendsParser.class);
	
	/**
	 * 配置文件加载器
	 */
	protected IConfigParser configParser = null;
	
	/**
	 * 配置文件路径
	 */
	protected String theConfigUrl = null;
	
	/**
	 * 配置文件对象
	 */
	protected IBLNFactoryConfig theConfig = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#parse()
	 */
	public IBLNFactoryConfig parse() throws IOException{
				
		IBLNFactoryConfig resultConfig = theConfig;
		String parentConfigUrl = theConfig.getParentConfigUrl();
		
		if(!StringUtils.isEmpty(parentConfigUrl)){
			resultConfig = configParser.parse(parentConfigUrl);
			log.debug("Process extends of factory " + resultConfig.getFactoryName() + " in the factory " + theConfig.getFactoryName());

			resultConfig.expand(theConfig, IBLNFactoryConfig.ExpandType.OVERRIDE_TYPE_ALL);
		}
		
		return resultConfig;		
	}
	
	public ExtendsParser(IConfigParser configParser, String theConfigUrl,
			IBLNFactoryConfig theConfig) {
		super();
		this.configParser = configParser;
		this.theConfigUrl = theConfigUrl;
		this.theConfig = theConfig;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#getConfigParser()
	 */
	public IConfigParser getConfigParser() {
		return configParser;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#setConfigParser(com.bln.framework.factory.ioc.config.parser.IConfigParser)
	 */
	public void setConfigParser(IConfigParser configParser) {
		this.configParser = configParser;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#getTheConfigUrl()
	 */
	public String getTheConfigUrl() {
		return theConfigUrl;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#setTheConfigUrl(java.lang.String)
	 */
	public void setTheConfigUrl(String theConfigUrl) {
		this.theConfigUrl = theConfigUrl;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#getTheConfig()
	 */
	public IBLNFactoryConfig getTheConfig() {
		return theConfig;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#setTheConfig(com.bln.framework.factory.ioc.config.IBLNFactoryConfig)
	 */
	public void setTheConfig(IBLNFactoryConfig theConfig) {
		this.theConfig = theConfig;
	}

}
