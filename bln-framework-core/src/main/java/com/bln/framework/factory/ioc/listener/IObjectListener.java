/**
 * @author gengw
 * Created on Jan 20, 2013
 */
package com.bln.framework.factory.ioc.listener;

import com.bln.framework.factory.ioc.IBLNFactory;

/**
 * IOC工厂所托管对象的监听器
 */
public interface IObjectListener {
	
	/**
	 * load之后的事件
	 */
	public void afterLoad(IBLNFactory factory);
	
	/**
	 * 该对象需要使用
	 */
	public void actived(IBLNFactory factory);

}
