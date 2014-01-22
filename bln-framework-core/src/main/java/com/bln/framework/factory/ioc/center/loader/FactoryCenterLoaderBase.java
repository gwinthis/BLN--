/**
 * @author gengw
 * Created on May 10, 2012
 */
package com.bln.framework.factory.ioc.center.loader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.factory.ioc.BLNFactory;
import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.loader.IConfigLoader;
import com.bln.framework.factory.ioc.config.parser.ConfigParser;
import com.bln.framework.factory.ioc.config.parser.IConfigParser;

/**
 * 所有工厂加载器的基类
 */
public abstract class FactoryCenterLoaderBase implements IFactoryCenterLoader{
		
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(FactoryCenterLoaderBase.class);
		
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.manager.loader.IAllFactoryLoader#loadAllFactory(java.util.List)
	 */
	public void loadAllFactory(List<IConfigLoader> configLoaders, Map<String, IBLNFactory> factoryCache) throws IOException{
		
		//1.获取配置文件路径
		List<String> configUrls = listConfigUrls();
		if(configUrls != null && configUrls.size() <= 0){
			return;
		}
		
		//2.生成配置对象
		IConfigParser configParser = new ConfigParser(configLoaders);
		List<IBLNFactoryConfig> configs = new ArrayList<IBLNFactoryConfig>(configUrls.size());
		
		for ( String configUrl : configUrls){
			
			log.debug("Parse Config " + configUrl);
			IBLNFactoryConfig config = configParser.parse(configUrl);
			if(config == null){
				log.info("can't load factory by configUrl " + configUrl + ", see more details in JVM System Log.");
			}else{
				configs.add(config);
			}
		}
		
		//3.静态替换虚工厂
		ReplaceVirtualFactory replaceVF = new ReplaceVirtualFactory(configs);
		replaceVF.replaceAllConfigs();
		
		//4.根据配置文件加载工厂
		
		//4.1排序配置对象
		SortFactoryConfigs sortFactorConfigs = new SortFactoryConfigs();
		configs = sortFactorConfigs.sort(configs);
		
		//4.2生成工厂
		for ( IBLNFactoryConfig config : configs){
			log.debug(config);
			IBLNFactory factory = new BLNFactory(config);
			if(factory != null){
				String factoryName = factory.getFactoryName();
				factoryCache.put(factoryName, factory);
				if(log.isInfoEnabled()){
					log.info("it loaded factory " + factoryName + " sucessfully!");
				}
			}
		}
	}
	
	/**
	 * 列出所有配置文件路径
	 * @return 所有工厂配置文件路径
	 * @throws IOException 
	 */
	protected abstract List<String> listConfigUrls() throws IOException;
}
