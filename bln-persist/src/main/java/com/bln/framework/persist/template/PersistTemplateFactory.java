/**
 * @author gengw
 * Created at 2014-01-08
 */
package com.bln.framework.persist.template;

import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.center.BLNFactoryCenter;
import com.bln.framework.util.asserter.Assert;

/**
 * 
 * 持久层模板工厂
 */
public class PersistTemplateFactory {

	/**
	 * 获取持久层模板
	 * @param factoryName
	 * @param sessionFactoryPath
	 * @return
	 */
	public IPersistTemplate getPersistTemplate(String factoryName, String sessionFactoryPath, String sessionKey){
		
		Assert.notEmpty("parameter factoryName can't be null!", factoryName);
		Assert.notEmpty("parameter sessionFactoryPath can't be null!", sessionFactoryPath);
		Assert.notEmpty("parameter sessionKey can't be null!", sessionKey);
		
		if(!BLNFactoryCenter.singleton().isLoadAllFactory()){
			BLNFactoryCenter.singleton().loadAllFactory();
		}
		
		IBLNFactory persistFactory = BLNFactoryCenter.singleton().getFactory(factoryName);
		return new PersistTemplate(persistFactory, sessionFactoryPath, sessionKey);
	}
	
	public static PersistTemplateFactory singleton(){
		return instance;
	}
	private static PersistTemplateFactory instance = new PersistTemplateFactory();
	
	private PersistTemplateFactory(){
		
	}
}
