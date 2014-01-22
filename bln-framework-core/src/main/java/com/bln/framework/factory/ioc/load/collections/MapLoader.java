/**
 * @author gengw
 * Created on Mar 29, 2012
 */
package com.bln.framework.factory.ioc.load.collections;

import java.util.List;
import java.util.Map;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;

/**
 * 映射类型加载器
 */
public class MapLoader extends CollectionsLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.property.NewPropertyInstanceBase#newInstance(com.bln.framework.factory.IFactory, java.lang.String, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity)throws Throwable{
				
		//1.获得子元素信息
		List<IBLNFactoryConfigEntity> subProps = configEntity.getSubEntitys();
//		if(subProps == null || subProps.isEmpty()){
//			return null;
//		}
		
		//2.生成Map
		
		//获取Map名称
		String collectionClass = configEntity.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONCLASS);
		
		//生成Map
		Map<Object, Object> map = (Map<Object, Object>)Class.forName(collectionClass).newInstance();		
		
		//3.添加成员，第一个key, 第二个为value
		boolean isKey = true;
		Object key = null;
		for (IBLNFactoryConfigEntity propConfig : subProps){
			
			//3.1生成key
			if(isKey){
				key = this.loadObject(factory, propConfig);
			}
			
			//3.2生成value
			if(!isKey){
				Object value = this.loadObject(factory, propConfig);
				
				//3.3填充到Map中
				map.put(key, value);
			}
	
			//3.4翻转标志
			isKey = !isKey;
		}
		
		//返回对象
		return map;
	}
}
