/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.center.BLNFactoryCenter;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;

/**
 * 从工厂中心中获取工厂对象
 */
public class FactoryCenterCreator  extends ObjectLoaderBase{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {

		//获得对象的类路径
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		//获取工厂对象
		Object object = null;
		if(IBLNFactoryConfig.VAL_CURRENT_FACTORY_IDENTIFIER.equals(clazz)){
			object = factory;
		}else{
			object = BLNFactoryCenter.singleton().getFactory(clazz);
		}
		
		//返回对象
		return object;
	}
	

}
