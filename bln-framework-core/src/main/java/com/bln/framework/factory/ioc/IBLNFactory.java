/**
 * @author gengw
 * Created on May 9, 2012
 */
package com.bln.framework.factory.ioc;

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

}
