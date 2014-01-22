/**
 * @author gengw
 * Created on Mar 30, 2012
 */
package com.bln.framework.factory.ioc.load.bean;

import java.util.Map;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.exception.LoadInstanceErrorException;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;

/**
 * 加载本地对象
 */
public class BeanLoader extends ObjectLoaderBase{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.ILoadObj#loadObj(com.bln.framework.factory.IFactory, com.bln.framework.config.entity.ConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable{
		
		//获得对象的类路径
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		
		//生成对象
		Object obj;
		try {
			obj = Class.forName(clazz).newInstance();
		} catch (Throwable e) {
			LoadInstanceErrorException lie = new LoadInstanceErrorException("when load class " + clazz + " occurs error!");
			lie.initCause(e);
			
			throw lie;
		}
		
		//设置特性
		Map<String, IBLNFactoryConfigEntity> propertyConfigMap = configEntity.getPropertyMap();
		if(propertyConfigMap != null && !propertyConfigMap.isEmpty()){
			setAllPropertys(factory, obj, propertyConfigMap);
		}

		//返回对象
		return obj;
	}

}
