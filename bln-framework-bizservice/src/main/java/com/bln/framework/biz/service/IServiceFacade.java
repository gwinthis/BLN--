/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.biz.service;

import com.bln.framework.mo.IMessageObject;

/**
 * 服务门面接口
 */
public interface IServiceFacade {
	
	/**
	 * @param reqMo 请求消息对象
	 * @return 响应消息对象
	 * @throws Throwable 服务层所抛出的异常
	 */
	public IMessageObject service(IMessageObject reqMo);

}
