package com.bln.framework.biz.service;

import com.bln.framework.mo.IMessageObject;

/**
 * 服务拦截器
 */
public interface IServiceIntercepter {

	/**
	 * 拦截方法
	 * @param reqMo 请求消息对象
	 * @return 响应报文 null 继续请求, not 拒绝请求
	 */
	public IMessageObject intercept(IMessageObject reqMo) throws Throwable;


}