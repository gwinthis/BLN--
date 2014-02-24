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
 * ��ָ�������л�ȡ���󣬴�BLNFactoryCenter�л�ȡ����������ȡָ�������е�ָ������
 */
public class FromFactoryCreator  extends ObjectLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {
		
		//���������������
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		
		//�ӹ��������л�ȡ������������
		IFactory<Object> theFactory = BLNFactoryCenter.singleton().getFactory(clazz);
		
		//�����Ҫ������������ȡ�Ķ���
		String value = configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		
		//��ȡ����
		Object object = theFactory.getInstance(value);
		
		//����ʵ��
		return object;
	}
}
