/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;

/**
 * �����������л�ȡ����
 */
public class FromOtherFactoryCreator  extends ObjectLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	@SuppressWarnings("unchecked")
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {
		
		//���������������
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		
		//�ӹ����л�ȡ������������
		IFactory<Object> otherFactory = (IFactory<Object>)factory.getInstance(clazz);
		
		//�����Ҫ������������ȡ�Ķ���
		String value = configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		//��ȡ����
		Object object = otherFactory.getInstance(value);
		
		//����ʵ��
		return object;
	}
}
