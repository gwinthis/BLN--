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
 * ӳ�����ͼ�����
 */
public class MapLoader extends CollectionsLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.property.NewPropertyInstanceBase#newInstance(com.bln.framework.factory.IFactory, java.lang.String, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity)throws Throwable{
				
		//1.�����Ԫ����Ϣ
		List<IBLNFactoryConfigEntity> subProps = configEntity.getSubEntitys();
//		if(subProps == null || subProps.isEmpty()){
//			return null;
//		}
		
		//2.����Map
		
		//��ȡMap����
		String collectionClass = configEntity.getAttr(IBLNFactoryConfig.ATTR_COLLECTIONCLASS);
		
		//����Map
		Map<Object, Object> map = (Map<Object, Object>)Class.forName(collectionClass).newInstance();		
		
		//3.��ӳ�Ա����һ��key, �ڶ���Ϊvalue
		boolean isKey = true;
		Object key = null;
		for (IBLNFactoryConfigEntity propConfig : subProps){
			
			//3.1����key
			if(isKey){
				key = this.loadObject(factory, propConfig);
			}
			
			//3.2����value
			if(!isKey){
				Object value = this.loadObject(factory, propConfig);
				
				//3.3��䵽Map��
				map.put(key, value);
			}
	
			//3.4��ת��־
			isKey = !isKey;
		}
		
		//���ض���
		return map;
	}
}
