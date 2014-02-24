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
 * 从指定工厂中获取对象，从BLNFactoryCenter中获取工厂，并获取指定工厂中的指定对象
 */
public class FromFactoryCreator  extends ObjectLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {
		
		//获得其他工厂对象
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		
		//从工厂中心中获取其他工厂对象
		IFactory<Object> theFactory = BLNFactoryCenter.singleton().getFactory(clazz);
		
		//获得需要从其他工厂获取的对象
		String value = configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		//获取对象
		Object object = theFactory.getInstance(value);
		
		//返回实例
		return object;
	}
}
