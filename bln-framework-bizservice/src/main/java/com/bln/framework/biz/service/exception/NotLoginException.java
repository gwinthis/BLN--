/**
 * @author gengw
 * Created on May 24, 2012
 */
package com.bln.framework.biz.service.exception;

import com.bln.framework.exception.CheckedException;

/**
 * δ��¼ϵͳ�쳣
 */
public class NotLoginException extends CheckedException{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ���캯��
	 */
	public NotLoginException(){
		super();
	}
	
	/**
	 * ���캯��
	 * @param info ������Ϣ
	 */
	public NotLoginException(String info){
		super(info);
	}

}
