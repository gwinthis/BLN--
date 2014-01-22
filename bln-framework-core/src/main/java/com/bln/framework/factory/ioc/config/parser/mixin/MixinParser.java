/**
 * @author gengw
 * Created on Nov 30, 2012
 */
package com.bln.framework.factory.ioc.config.parser.mixin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.parser.IConfigParser;

/**
 * ��������
 */
public class MixinParser implements IMixinParser {
	
	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(MixinParser.class);
	
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
	 * @see com.bln.framework.factory.ioc.config.parser.mixin.IMixinParser#parse()
	 */
	public IBLNFactoryConfig parse() throws IOException{
		
		//1.У���Ƿ���ڻ�������
		//this.checkDependencyCircle();
		
		//2.��û���������Ϣ
		List<IBLNFactoryConfig> mixinConfigs = this.getMixinConfigs();
		
		//3.��չ��ǰ��չ������Ϣ
		IBLNFactoryConfig allConfig = null;
		if(mixinConfigs == null || mixinConfigs.size() <= 0){
			allConfig = this.theConfig;
			
		}else{
			mixinConfigs.add(theConfig);
			
			boolean first = true;
			for (IBLNFactoryConfig config : mixinConfigs){
				
				if(log.isDebugEnabled()){
					log.debug("Process mixin factory " +  config.getFactoryName() + " in the factory " + theConfig.getFactoryName());
				}
				
				//��չ������Ϣ
				if(first){
					allConfig = config;
					first = false;
				}else{
					allConfig.expand(config, IBLNFactoryConfig.ExpandType.OVERRIDE_TYPE_ALL);
				}
			}
		}
		
		//4.����������Ϣ
		return allConfig;
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.IBLNFactoryConfig#getParentConfigs()
	 */
	protected List<IBLNFactoryConfig> getMixinConfigs() throws IOException{
		
		//1.��ȡ���������ļ�
		String[] mixinConfigUrls = theConfig.getMixinConfigUrls();
		
		//2.���ػ��������ļ�
		List<IBLNFactoryConfig> mixinConfigs = null;
		if(mixinConfigUrls != null && mixinConfigUrls.length > 0){
			
			mixinConfigs = new ArrayList<IBLNFactoryConfig>(mixinConfigUrls.length);
			
			for (String configUrl : mixinConfigUrls){
				IBLNFactoryConfig config = configParser.parse(configUrl);
				mixinConfigs.add(config);
			}
		}
		
		//3.���ػ�������
		return mixinConfigs;
		
	}

	public MixinParser(IConfigParser configParser, String theConfigUrl,
			IBLNFactoryConfig theConfig) {
		super();
		this.configParser = configParser;
		this.theConfigUrl = theConfigUrl;
		this.theConfig = theConfig;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#getConfigParser()
	 */
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.mixin.IMixinParser#getConfigParser()
	 */
	public IConfigParser getConfigParser() {
		return configParser;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.extend.IExtendsParser#setConfigParser(com.bln.framework.factory.ioc.config.parser.IConfigParser)
	 */
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.mixin.IMixinParser#setConfigParser(com.bln.framework.factory.ioc.config.parser.IConfigParser)
	 */
	public void setConfigParser(IConfigParser configParser) {
		this.configParser = configParser;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.mixin.IMixinParser#getTheConfigUrl()
	 */
	public String getTheConfigUrl() {
		return theConfigUrl;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.mixin.IMixinParser#setTheConfigUrl(java.lang.String)
	 */
	public void setTheConfigUrl(String theConfigUrl) {
		this.theConfigUrl = theConfigUrl;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.mixin.IMixinParser#getTheConfig()
	 */
	public IBLNFactoryConfig getTheConfig() {
		return theConfig;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.config.parser.mixin.IMixinParser#setTheConfig(com.bln.framework.factory.ioc.config.IBLNFactoryConfig)
	 */
	public void setTheConfig(IBLNFactoryConfig theConfig) {
		this.theConfig = theConfig;
	}

}
