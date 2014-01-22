/**
 * @author gengw
 * Created on Mar 29, 2012
 */
package com.bln.framework.factory.ioc.load.collections;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;

/**
 * 映射类型加载器
 */
public class PropertiesLoader extends CollectionsLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.property.NewPropertyInstanceBase#newInstance(com.bln.framework.factory.IFactory, java.lang.String, java.lang.String, java.lang.Class)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity)throws Throwable{
				
		//1.获得子元素信息
		List<IBLNFactoryConfigEntity> subProps = configEntity.getSubEntitys();
		
		//获取Map类型
		String collectionClass = configEntity.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONCLASS);
		if(StringUtils.isEmpty(collectionClass)){
			collectionClass = "java.util.Properties";
		}

		//2.生成Properties
		@SuppressWarnings("unchecked")
		Map<Object, Object> properties = (Map<Object, Object>)Class.forName(collectionClass).newInstance();
		
		//3.添加成员，根据key属性和value属性
		for (IBLNFactoryConfigEntity propConfig : subProps){
			
			//获得key值
			String key = propConfig.getAttr(IBLNFactoryConfig.ATTR_KEY);

			//获得value值
			String value = propConfig.getAttr(IBLNFactoryConfig.ATTR_VALUE);
			
			//填充到配置对象中
			properties.put(key, value);
		}
		
		//返回对象
		return properties;
	}
}
