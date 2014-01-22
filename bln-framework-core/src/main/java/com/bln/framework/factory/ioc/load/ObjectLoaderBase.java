/**
 * @author gengw
 * Created on Mar 30, 2012
 */
package com.bln.framework.factory.ioc.load;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.bln.framework.base.BaseObj;
import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.exception.SetPropertyErrorException;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;

/**
 * �������������
 */
public abstract class ObjectLoaderBase extends BaseObj implements IObjectLoader{
	
		
	/**
	 * �����������Ե�ʵ��
	 * @param bean Ҫ���õĶ���
	 * @param propertyConfigMap ��������
	 * @throws Throwable 
	 */
	protected void setAllPropertys(IFactory<Object> factory, Object bean, Map<String, IBLNFactoryConfigEntity> propertyConfigMap){
		
		for (Map.Entry<String, IBLNFactoryConfigEntity> propertyConfigEntry : propertyConfigMap.entrySet()){
			
			//���������Ϣ
			IBLNFactoryConfigEntity propertyConfigEntity = propertyConfigEntry.getValue();
			
			//��ֵ����
			try {
				
				//��ö��������
				IObjectLoader objectLoader = ObjectLoaderFactory.getObjLoader(propertyConfigEntity);
				
				//��������
				Object object = objectLoader.load(factory, propertyConfigEntity);

				//��ֵ����
				BeanUtils.setProperty(bean, propertyConfigEntry.getKey(), object);

			} catch (Throwable e) {
				SetPropertyErrorException spe = new SetPropertyErrorException("when set property " + propertyConfigEntry.getKey().toString() + " occurs error!");
				spe.initCause(e);
				
				throw spe;
			}
			
		}
	}

}
