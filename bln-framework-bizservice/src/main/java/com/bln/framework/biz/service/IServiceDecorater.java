/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.biz.service;

import com.bln.framework.mo.IMessageObject;

/**
 * 服务装饰接口
 */
public interface IServiceDecorater {

	/**
	 * 获得服务对象
	 * @return the service
	 */
	public IService getService();

	/**
	 * 设置服务对象
	 * @param service the service to set
	 */
	public void setService(IService service);

	/**
	 * 服务处理
	 * @param reqMo 请求消息对象
	 * @return 响应消息对象
	 * @throws Throwable 服务时所抛出的异常
	 */
	public IMessageObject service(IMessageObject reqMo) throws Throwable;
}
