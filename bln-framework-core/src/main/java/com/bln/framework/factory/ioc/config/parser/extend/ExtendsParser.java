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
 * �����̳�
 */
public class ExtendsParser implements IExtendsParser {
	
	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(ExtendsParser.class);
	
	/**
	 * �����ļ�������
	 */
	protected IConfigParser configParser = null;
	
	/**
	 * �����ļ�·��
	 */
	protected String theConfigUrl = null;
	
	/**
	 * �����ļ�����
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
