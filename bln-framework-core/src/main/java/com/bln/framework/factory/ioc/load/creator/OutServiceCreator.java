/**
 * @author gengw
 * Created on Mar 30, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import java.util.Map;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;
import com.bln.framework.ots.locator.IOTSLocator;

/**
 * �ⲿ��������
 */
public class OutServiceCreator extends ObjectLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.ILoadObj#loadObj(com.bln.framework.factory.IFactory, com.bln.framework.config.entity.ConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {
		
		//����ⲿ����λ��
		String otsLocatorClassNm = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		IOTSLocator otsLocator = (IOTSLocator)factory.getInstance(otsLocatorClassNm);
		
		//��������
		Map<String, IBLNFactoryConfigEntity> propertyConfigMap = configEntity.getPropertyMap();
		if(propertyConfigMap != null && !propertyConfigMap.isEmpty()){
			setAllPropertys(factory, otsLocator, propertyConfigMap);
		}
		
		//��÷���
		Object outService = otsLocator.getService();
		
		//���ط���
		return outService;
	}
}
