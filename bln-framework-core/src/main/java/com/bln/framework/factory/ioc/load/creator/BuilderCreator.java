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
 * ͨ�����������ر��ض���
 */
public class BuilderCreator extends ObjectLoaderBase{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.ILoadObj#loadObj(com.bln.framework.factory.IFactory, com.bln.framework.config.entity.ConfigEntity)
	 */
	@SuppressWarnings("unchecked")
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable{
		
		//1.��ù�����
		
		//1.1��ù���������·��
		String builderClazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		
		//1.2��ù�����
		IBuilder1Step<Object, String> builder = (IBuilder1Step<Object, String>)factory.getInstance(builderClazz);
		
		//2.���ɶ���
		Object obj = builder.build(configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE));
		
		//��������
		Map<String, IBLNFactoryConfigEntity> propertyConfigMap = configEntity.getPropertyMap();
		if(propertyConfigMap != null && !propertyConfigMap.isEmpty()){
			setAllPropertys(factory, obj, propertyConfigMap);
		}

		//���ض���
		return obj;
	}

}
