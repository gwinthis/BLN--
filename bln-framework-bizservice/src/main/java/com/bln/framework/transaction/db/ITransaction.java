/**
 * @author gengw
 * Created on Mar 30, 2012
 */
package com.bln.framework.transaction.db;

import com.bln.framework.biz.service.IService;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.transaction.exception.TransException;

/**
 * 事务处理
 */
public interface ITransaction {
	
	/**
	 * 启动新的事物处理
	 * @param serviceObj 服务对象
	 * @param reqMo 请求消息对象
	 * @return 响应消息对象
	 * @throws Throwable 服务时所抛出的异常
	 */
	public IMessageObject requireNewTrans(IService serviceObj, IMessageObject reqMo) throws TransException;

	/**
	 * 如果当前有事务，添加到当前事务中，否则启动新的事物
	 * @param serviceObj 服务对象
	 * @param reqMo 请求消息对象
	 * @return 响应消息对象
	 * @throws Throwable 服务时所抛出的异常
	 */
	public IMessageObject requireTrans(IService serviceObj, IMessageObject reqMo) throws TransException;

}
