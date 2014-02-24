/**
 * @author gengw
 * Created on May 9, 2012
 */
package com.bln.framework.factory.ioc;

import java.util.List;

import com.bln.framework.factory.IFactory;

/**
 * BLN工厂的接口
 */
public interface IBLNFactory extends IFactory<Object>{
	
	/**
	 * 获得工厂名称
	 * @return the factoryName
	 */
	public String getFactoryName();

	/**
	 * 获取所有对象路径名
	 * @return 所有对象路径名
	 */
	public abstract List<String> getObjectNames();

}
