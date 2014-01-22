/**
 * @author gengw
 * Created on Mar 30, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import java.util.Map;

import com.bln.framework.builder.IBuilder1Step;
import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;

/**
 * 通过构造器加载本地对象
 */
public class BuilderCreator extends ObjectLoaderBase{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.ILoadObj#loadObj(com.bln.framework.factory.IFactory, com.bln.framework.config.entity.ConfigEntity)
	 */
	@SuppressWarnings("unchecked")
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable{
		
		//1.获得构造器
		
		//1.1获得构造器的类路径
		String builderClazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		
		//1.2获得构造器
		IBuilder1Step<Object, String> builder = (IBuilder1Step<Object, String>)factory.getInstance(builderClazz);
		
		//2.生成对象
		Object obj = builder.build(configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE));
		
		//设置特性
		Map<String, IBLNFactoryConfigEntity> propertyConfigMap = configEntity.getPropertyMap();
		if(propertyConfigMap != null && !propertyConfigMap.isEmpty()){
			setAllPropertys(factory, obj, propertyConfigMap);
		}

		//返回对象
		return obj;
	}

}
