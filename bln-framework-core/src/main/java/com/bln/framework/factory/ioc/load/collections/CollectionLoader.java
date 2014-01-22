/**
 * @author gengw
 * Created on Mar 29, 2012
 */
package com.bln.framework.factory.ioc.load.collections;

import java.util.Collection;
import java.util.List;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;

/**
 * 容器类型加载器
 */
public class CollectionLoader extends CollectionsLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.property.NewPropertyInstanceBase#newInstance(com.bln.framework.factory.IFactory, java.lang.String, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity)throws Throwable{
				
		//获得子元素信息
		List<IBLNFactoryConfigEntity> subProps = configEntity.getSubEntitys();
//		if(subProps == null || subProps.isEmpty()){
//			return null;
//		}
		
		//生成器类型
		String creatorType = configEntity.getAttr(IBLNFactoryConfig.ATTR_COMPONENT_CREATOR);

		//容器类型
		String collectionClass = configEntity.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONCLASS);
		
		//生成容器
		Collection<Object> collection = (Collection<Object>)Class.forName(collectionClass).newInstance();		
		for (IBLNFactoryConfigEntity propConfig : subProps){
			
			//检查元素生成方式
			checkCreatorType(propConfig, IBLNFactoryConfig.ATTR_CREATOR, creatorType);

			//生成对象
			Object obj = this.loadObject(factory, propConfig);
			
			//添加到容器中
			collection.add(obj);
		}
		
		//返回对象
		return collection;
		
	}

}
