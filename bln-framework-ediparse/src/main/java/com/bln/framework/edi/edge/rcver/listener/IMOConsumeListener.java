/**
 * 北京八度互联科技有限公司版权所有
 * Copyright (C) Badu Corporation. All Rights Reserved
 */
package com.bln.framework.edi.edge.rcver.listener;

import com.bln.framework.mo.IMessageObject;

/**
 * 报文接收者监听器
 * @author gengw(gengw@17guagua.com)
 * @version 2013-7-18 下午6:19:45
 */
public interface IMOConsumeListener {
	
	/**
	 * 请求服务之前触发的事件
	 * @param reqMo 请求消息对象
	 */
	public void beforExecuteService(IMessageObject reqMo);
	
	/**
	 * 请求服务之后触发的事件
	 * @param reqMo 请求消息对象
	 * @param respMo 响应消息对象
	 */
	public void afterExecuteService(IMessageObject reqMo, IMessageObject respMo);
	

}
