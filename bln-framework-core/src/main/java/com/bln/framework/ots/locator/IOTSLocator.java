package com.bln.framework.ots.locator;

import com.bln.framework.jndi.IContextPrototype;

/**
 * 外部服务定位器
 */
public interface IOTSLocator{

	/**
	 * 获得外部服务对象
	 * @param <T> 服务的类型
	 * @return 服务对象
	 * @throws Throwable
	 */
	public abstract <T> T getService() throws Throwable;

	/**
	 * @return the contextPrototype
	 */
	public IContextPrototype getContextPrototype();

	/**
	 * @param contextPrototype the contextPrototype to set
	 */
	public void setContextPrototype(IContextPrototype contextPrototype);

	/**
	 * @return the serviceKey
	 */
	public String getServiceKey();

	/**
	 * @param serviceKey the serviceKey to set
	 */
	public void setServiceKey(String serviceKey);

}