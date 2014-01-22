/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import java.util.Map;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;

/**
 * 通过类路径生成对象
 */
public class NewObjectCreator  extends ObjectLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {

		//1.生成对象
		//获得对象的类路径
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		Object object = Class.forName(clazz).newInstance();
		
		//2.赋值特性
		Map<String, IBLNFactoryConfigEntity> propertyMap = configEntity.getPropertyMap();
		if(propertyMap != null && !propertyMap.isEmpty()){
			this.setAllPropertys(factory, object, propertyMap);
		}
		
		//返回对象
		return object;
	}
	

}
