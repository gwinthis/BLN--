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
 * �������ͼ�����
 */
public class ArrayLoader extends CollectionsLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.property.NewPropertyInstanceBase#newInstance(com.bln.framework.factory.IFactory, java.lang.String, java.lang.String, java.lang.Class)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity)throws Throwable{
				
		//�����Ԫ����Ϣ
		List<IBLNFactoryConfigEntity> subProps = configEntity.getSubEntitys();
//		if(subProps == null || subProps.isEmpty()){
//			return null;
//		}
		
		//��������
		Class<?> componentClass = Class.forName(configEntity.getAttr(IBLNFactoryConfig.ATTR_COMPONENT_CLASS));

		//����������
		String creatorType = configEntity.getAttr(IBLNFactoryConfig.ATTR_COMPONENT_CREATOR);

		int size = subProps.size();
		Object array = Array.newInstance(componentClass, size);
		for (int i = 0; i < size; i++){
			
			IBLNFactoryConfigEntity propConfig = subProps.get(i);
						
			//���Ԫ�����ɷ�ʽ
			checkCreatorType(propConfig, IBLNFactoryConfig.ATTR_CREATOR, creatorType);

			//���ɶ���
			Object obj = this.loadObject(factory, propConfig);
			
			//��������Ԫ��
			Array.set(array, i, obj);
		}
		
		//���ض���
		return array;
	}
}
