package com.bln.framework.factory.ioc.load;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.ioc.config.entity.IBLNFactoryConfigEntity;

public interface IObjectLoader {

	/**
	 * ͨ���������Լ��ض��󣬲���������
	 * @param configEntity �����������Ϣ
	 * @return �¼��صĶ���
	 * @throws Throwable TODO
	 */
	public abstract Object load(IFactory<Object> factory, IBLNFactoryConfigEntity configEntity) throws Throwable;

}