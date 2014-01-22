/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.biz.service.decorater;

import com.bln.framework.biz.service.IService;
import com.bln.framework.biz.service.IServiceDecorater;

/**
 * 服务装饰器基类
 */
public abstract class ServiceDecoraterBase implements IServiceDecorater{
	
	/**
	 * 服务对象
	 */
	public IService service = null;

	/**
	 * 获得服务对象
	 * @return the service
	 */
	public IService getService() {
		return service;
	}

	/**
	 * 设置服务对象
	 * @param service the service to set
	 */
	public void setService(IService service) {
		this.service = service;
	}
}
