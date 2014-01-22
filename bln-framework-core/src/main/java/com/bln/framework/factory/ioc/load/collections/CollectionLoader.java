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
 * �������ͼ�����
 */
public class CollectionLoader extends CollectionsLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.property.NewPropertyInstanceBase#newInstance(com.bln.framework.factory.IFactory, java.lang.String, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity)throws Throwable{
				
		//�����Ԫ����Ϣ
		List<IBLNFactoryConfigEntity> subProps = configEntity.getSubEntitys();
//		if(subProps == null || subProps.isEmpty()){
//			return null;
//		}
		
		//����������
		String creatorType = configEntity.getAttr(IBLNFactoryConfig.ATTR_COMPONENT_CREATOR);

		//��������
		String collectionClass = configEntity.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONCLASS);
		
		//��������
		Collection<Object> collection = (Collection<Object>)Class.forName(collectionClass).newInstance();		
		for (IBLNFactoryConfigEntity propConfig : subProps){
			
			//���Ԫ�����ɷ�ʽ
			checkCreatorType(propConfig, IBLNFactoryConfig.ATTR_CREATOR, creatorType);

			//���ɶ���
			Object obj = this.loadObject(factory, propConfig);
			
			//��ӵ�������
			collection.add(obj);
		}
		
		//���ض���
		return collection;
		
	}

}
