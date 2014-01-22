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
 * ӳ�����ͼ�����
 */
public class PropertiesLoader extends CollectionsLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.property.NewPropertyInstanceBase#newInstance(com.bln.framework.factory.IFactory, java.lang.String, java.lang.String, java.lang.Class)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity)throws Throwable{
				
		//1.�����Ԫ����Ϣ
		List<IBLNFactoryConfigEntity> subProps = configEntity.getSubEntitys();
		
		//��ȡMap����
		String collectionClass = configEntity.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONCLASS);
		if(StringUtils.isEmpty(collectionClass)){
			collectionClass = "java.util.Properties";
		}

		//2.����Properties
		@SuppressWarnings("unchecked")
		Map<Object, Object> properties = (Map<Object, Object>)Class.forName(collectionClass).newInstance();
		
		//3.��ӳ�Ա������key���Ժ�value����
		for (IBLNFactoryConfigEntity propConfig : subProps){
			
			//���keyֵ
			String key = propConfig.getAttr(IBLNFactoryConfig.ATTR_KEY);

			//���valueֵ
			String value = propConfig.getAttr(IBLNFactoryConfig.ATTR_VALUE);
			
			//��䵽���ö�����
			properties.put(key, value);
		}
		
		//���ض���
		return properties;
	}
}
