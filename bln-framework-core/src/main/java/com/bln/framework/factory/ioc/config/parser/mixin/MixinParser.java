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
 * 解析混入
 */
public class MixinParser implements IMixinParser {
	
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(MixinParser.class);
	
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
	 * @see com.bln.framework.factory.ioc.config.parser.mixin.IMixinParser#parse()
	 */
	public IBLNFactoryConfig parse() throws IOException{
		
		//1.校验是否存在混入依赖
		//this.checkDependencyCircle();
		
		//2.获得混入配置信息
		List<IBLNFactoryConfig> mixinConfigs = this.getMixinConfigs();
		
		//3.扩展当前扩展配置信息
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
				
				//扩展配置信息
				if(first){
					allConfig = config;
					first = false;
				}else{
					allConfig.expand(config, IBLNFactoryConfig.ExpandType.OVERRIDE_TYPE_ALL);
				}
			}
		}
		
		//4.返回配置信息
		return allConfig;
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.IBLNFactoryConfig#getParentConfigs()
	 */
	protected List<IBLNFactoryConfig> getMixinConfigs() throws IOException{
		
		//1.获取混入配置文件
		String[] mixinConfigUrls = theConfig.getMixinConfigUrls();
		
		//2.加载混入配置文件
		List<IBLNFactoryConfig> mixinConfigs = null;
		if(mixinConfigUrls != null && mixinConfigUrls.length > 0){
			
			mixinConfigs = new ArrayList<IBLNFactoryConfig>(mixinConfigUrls.length);
			
			for (String configUrl : mixinConfigUrls){
				IBLNFactoryConfig config = configParser.parse(configUrl);
				mixinConfigs.add(config);
			}
		}
		
		//3.返回混入配置
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
