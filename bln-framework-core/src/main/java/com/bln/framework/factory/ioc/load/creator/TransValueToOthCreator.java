/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import org.apache.commons.beanutils.ConvertUtils;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.IBLNFactoryConfig;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;

/**
 * 根据Value值转换成指定的类型
 */
public class TransValueToOthCreator  extends ObjectLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {

		//获得对象的类路径
		String clazz = configEntity.getAttr(IBLNFactoryConfig.ATTR_CLASS);
		
		//根据特性值转换成指定的对象
		String value = configEntity.getAttr(IBLNFactoryConfig.ATTR_VALUE);
		Object obj = ConvertUtils.convert(value, Class.forName(clazz));
		
		//返回对象
		return obj;
	}
	

}
