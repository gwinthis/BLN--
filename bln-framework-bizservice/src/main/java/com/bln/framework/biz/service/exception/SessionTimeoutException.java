/**
 * @author gengw
 * Created on May 24, 2012
 */
package com.bln.framework.biz.service.exception;

import com.bln.framework.exception.CheckedException;

/**
 * Session超时异常
 */
public class SessionTimeoutException extends CheckedException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 */
	public SessionTimeoutException(){
		super();
	}
	
	/**
	 * 构造函数
	 * @param info 错误消息
	 */
	public SessionTimeoutException(String info){
		super(info);
	}

}
