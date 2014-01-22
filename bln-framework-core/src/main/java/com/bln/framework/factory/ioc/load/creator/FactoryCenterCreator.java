/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.center.BLNFactoryCenter;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;

/**
 * �ӹ��������л�ȡ��������
 */
public class FactoryCenterCreator  extends ObjectLoaderBase{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {

		//��ö������·��
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		//��ȡ��������
		Object object = null;
		if(IBLNFactoryConfig.VAL_CURRENT_FACTORY_IDENTIFIER.equals(clazz)){
			object = factory;
		}else{
			object = BLNFactoryCenter.singleton().getFactory(clazz);
		}
		
		//���ض���
		return object;
	}
	

}
