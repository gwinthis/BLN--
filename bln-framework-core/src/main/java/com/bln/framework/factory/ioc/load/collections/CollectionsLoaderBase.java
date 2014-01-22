/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.factory.ioc.load.collections;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.base.BaseObj;
import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.IObjectLoader;
import com.bln.framework.factory.ioc.load.ObjectLoaderFactory;

/**
 * 容器对象加载器基类
 */
public abstract class CollectionsLoaderBase extends BaseObj implements IObjectLoader{
	
	/**
	 * 检查元素生成方式
	 * @param propertyConfig
	 * @param defaultCreatorType
	 */
	protected void checkCreatorType(IBLNFactoryConfigEntity propertyConfig, String attrName, String defaultCreatorType){
		
		if(StringUtils.isEmpty(propertyConfig.getAttr(attrName))){
			propertyConfig.setAttr(attrName, defaultCreatorType);
		}
	}
	
	/**
	 * 加载对象
	 * @param factory 工厂对象
	 * @param configEntity 配置对象
	 * @return 加载后的对象
	 * @throws Throwable 
	 * @throws Throwable
	 */
	protected Object loadObject(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable{
		
		//获得加载器
		IObjectLoader objectLoader = ObjectLoaderFactory.getObjLoader(configEntity);

		//加载对象
		Object object = objectLoader.load(factory, configEntity);
		
		return object;
	}
}
