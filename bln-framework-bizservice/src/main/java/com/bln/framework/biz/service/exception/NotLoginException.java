/**
 * @author gengw
 * Created on May 24, 2012
 */
package com.bln.framework.biz.service.exception;

import com.bln.framework.exception.CheckedException;

/**
 * 未登录系统异常
 */
public class NotLoginException extends CheckedException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 */
	public NotLoginException(){
		super();
	}
	
	/**
	 * 构造函数
	 * @param info 错误消息
	 */
	public NotLoginException(String info){
		super(info);
	}

}
