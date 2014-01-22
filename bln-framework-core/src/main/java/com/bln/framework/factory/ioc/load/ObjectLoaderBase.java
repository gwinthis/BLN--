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
 * 对象加载器基类
 */
public abstract class ObjectLoaderBase extends BaseObj implements IObjectLoader{
	
		
	/**
	 * 生成所有特性的实例
	 * @param bean 要设置的对象
	 * @param propertyConfigMap 特性配置
	 * @throws Throwable 
	 */
	protected void setAllPropertys(IFactory<Object> factory, Object bean, Map<String, IBLNFactoryConfigEntity> propertyConfigMap){
		
		for (Map.Entry<String, IBLNFactoryConfigEntity> propertyConfigEntry : propertyConfigMap.entrySet()){
			
			//获得特性信息
			IBLNFactoryConfigEntity propertyConfigEntity = propertyConfigEntry.getValue();
			
			//赋值特性
			try {
				
				//获得对象加载器
				IObjectLoader objectLoader = ObjectLoaderFactory.getObjLoader(propertyConfigEntity);
				
				//生成特性
				Object object = objectLoader.load(factory, propertyConfigEntity);

				//赋值特性
				BeanUtils.setProperty(bean, propertyConfigEntry.getKey(), object);

			} catch (Throwable e) {
				SetPropertyErrorException spe = new SetPropertyErrorException("when set property " + propertyConfigEntry.getKey().toString() + " occurs error!");
				spe.initCause(e);
				
				throw spe;
			}
			
		}
	}

}
