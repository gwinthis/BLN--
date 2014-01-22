/**
 * @author gengw
 * Created on 2012-03-28
 */
package com.bln.framework.biz.service;

import com.bln.framework.biz.service.instance.config.IServiceConfig;
import com.bln.framework.mo.IMessageObject;

/**
 * <p>服务控制器接口</p>
 * 处理请求消息对象
 */
public interface IService {


	/**
	 * 执行服务
	 * @param reqMo 请求消息对象
	 * @return 响应消息对象
	 * @throws Throwable 服务执行所抛出的异常
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable;


	/**
	 * @return the serviceConfig
	 */
	public IServiceConfig getServiceConfig();

}
