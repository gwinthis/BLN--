/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.factory.ioc.load.creator;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;
import com.bln.framework.factory.ioc.load.ObjectLoaderBase;

/**
 * 根据Value值转换成指定的类型
 */
public class NullObjectCreator  extends ObjectLoaderBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.load.IObjLoader#load(com.bln.framework.factory.IFactory, com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity)
	 */
	public Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable {
		return null;
	}
	

}
