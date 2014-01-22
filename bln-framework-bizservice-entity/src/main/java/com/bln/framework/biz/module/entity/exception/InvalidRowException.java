/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.biz.module.entity.exception;

import com.bln.framework.exception.CheckedException;

/**
 * 无效行异常
 */
public class InvalidRowException extends CheckedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public InvalidRowException(){
		super();
	}
	
	public InvalidRowException(String info){
		super(info);
	}
}
