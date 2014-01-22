package com.bln.framework.factory.ioc.load;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;

public interface IObjectLoader {

	/**
	 * 通过配置属性加载对象，并设置特性
	 * @param configEntity 对象的配置信息
	 * @return 新加载的对象
	 * @throws Throwable TODO
	 */
	public abstract Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable;

}