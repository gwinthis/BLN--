/**
 * @author gengw
 * Created on Mar 29, 2012
 */
package com.bln.framework.factory.ioc.load.collections;

import java.lang.reflect.Array;
import java.util.List;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;

/**
 * 数组类型加载器
 */
public class ArrayLoader extends CollectionsLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.property.NewPropertyInstanceBase#newInstance(com.bln.framework.factory.IFactory, java.lang.String, java.lang.String, java.lang.Class)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity)throws Throwable{
				
		//获得子元素信息
		List<IBLNFactoryConfigEntity> subProps = configEntity.getSubEntitys();
//		if(subProps == null || subProps.isEmpty()){
//			return null;
//		}
		
		//生成数组
		Class<?> componentClass = Class.forName(configEntity.getAttr(IBLNFactoryConfig.ATTR_COMPONENT_CLASS));

		//生成器类型
		String creatorType = configEntity.getAttr(IBLNFactoryConfig.ATTR_COMPONENT_CREATOR);

		int size = subProps.size();
		Object array = Array.newInstance(componentClass, size);
		for (int i = 0; i < size; i++){
			
			IBLNFactoryConfigEntity propConfig = subProps.get(i);
						
			//检查元素生成方式
			checkCreatorType(propConfig, IBLNFactoryConfig.ATTR_CREATOR, creatorType);

			//生成对象
			Object obj = this.loadObject(factory, propConfig);
			
			//设置数组元素
			Array.set(array, i, obj);
		}
		
		//返回对象
		return array;
	}
}
