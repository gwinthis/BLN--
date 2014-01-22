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
 * ���й����������Ļ���
 */
public abstract class FactoryCenterLoaderBase implements IFactoryCenterLoader{
		
	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(FactoryCenterLoaderBase.class);
		
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.manager.loader.IAllFactoryLoader#loadAllFactory(java.util.List)
	 */
	public void loadAllFactory(List<IConfigLoader> configLoaders, Map<String, IBLNFactory> factoryCache) throws IOException{
		
		//1.��ȡ�����ļ�·��
		List<String> configUrls = listConfigUrls();
		if(configUrls != null && configUrls.size() <= 0){
			return;
		}
		
		//2.�������ö���
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
		
		//3.��̬�滻�鹤��
		ReplaceVirtualFactory replaceVF = new ReplaceVirtualFactory(configs);
		replaceVF.replaceAllConfigs();
		
		//4.���������ļ����ع���
		
		//4.1�������ö���
		SortFactoryConfigs sortFactorConfigs = new SortFactoryConfigs();
		configs = sortFactorConfigs.sort(configs);
		
		//4.2���ɹ���
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
	 * �г����������ļ�·��
	 * @return ���й��������ļ�·��
	 * @throws IOException 
	 */
	protected abstract List<String> listConfigUrls() throws IOException;
}
