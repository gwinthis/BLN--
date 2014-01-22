/**
 * @author gengw
 * Created on May 24, 2012
 */
package com.bln.framework.biz.service.exception;

import com.bln.framework.exception.CheckedException;

/**
 * Session��ʱ�쳣
 */
public class SessionTimeoutException extends CheckedException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ���캯��
	 */
	public SessionTimeoutException(){
		super();
	}
	
	/**
	 * ���캯��
	 * @param info ������Ϣ
	 */
	public SessionTimeoutException(String info){
		super(info);
	}

}
