/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.ots.locator;

import com.bln.framework.base.cacheable.BaseObjCacheable;
import com.bln.framework.jndi.IContextPrototype;

/**
 * 外部服务定位器基类
 */
public abstract class OTSLocatorBase extends BaseObjCacheable implements IOTSLocator{

	/**
	 * 上下文对象的原型
	 */
	protected IContextPrototype contextPrototype = null;
	
	/**
	 * 服务关键字
	 */
	protected String serviceKey = null;

	/**
	 * @return the contextPrototype
	 */
	public IContextPrototype getContextPrototype() {
		return contextPrototype;
	}

	/**
	 * @param contextPrototype the contextPrototype to set
	 */
	public void setContextPrototype(IContextPrototype contextPrototype) {
		this.contextPrototype = contextPrototype;
	}

	/**
	 * @return the serviceKey
	 */
	public String getServiceKey() {
		return serviceKey;
	}

	/**
	 * @param serviceKey the serviceKey to set
	 */
	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}
}
