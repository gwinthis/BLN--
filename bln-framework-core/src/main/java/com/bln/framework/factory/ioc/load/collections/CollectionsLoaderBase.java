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
 * �����������������
 */
public abstract class CollectionsLoaderBase extends BaseObj implements IObjectLoader{
	
	/**
	 * ���Ԫ�����ɷ�ʽ
	 * @param propertyConfig
	 * @param defaultCreatorType
	 */
	protected void checkCreatorType(IBLNFactoryConfigEntity propertyConfig, String attrName, String defaultCreatorType){
		
		if(StringUtils.isEmpty(propertyConfig.getAttr(attrName))){
			propertyConfig.setAttr(attrName, defaultCreatorType);
		}
	}
	
	/**
	 * ���ض���
	 * @param factory ��������
	 * @param configEntity ���ö���
	 * @return ���غ�Ķ���
	 * @throws Throwable 
	 * @throws Throwable
	 */
	protected Object loadObject(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable{
		
		//��ü�����
		IObjectLoader objectLoader = ObjectLoaderFactory.getObjLoader(configEntity);

		//���ض���
		Object object = objectLoader.load(factory, configEntity);
		
		return object;
	}
}
