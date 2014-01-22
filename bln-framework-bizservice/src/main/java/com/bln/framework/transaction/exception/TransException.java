/**
 * @author gengw
 * Created on Mar 31, 2012
 */
package com.bln.framework.transaction.exception;

import com.bln.framework.exception.CheckedException;

/**
 * 事务处理所抛出的异常
 */
public class TransException extends CheckedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9037230099212156117L;
		
	/**
	 * 
	 */
	public TransException(){
		super();
	}
	
	/**
	 * @param info
	 * @param causeException
	 */
	public TransException(String info){
		super(info);
	}
}
