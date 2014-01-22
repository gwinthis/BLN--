package com.bln.framework.net.mq.exception;

import com.bln.framework.exception.CheckedException;

/**
 * Queue�����쳣
 */
public class ConnectionException extends CheckedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public ConnectionException(){
		super();
	}
	
	public ConnectionException(String info){
		super(info);
	}
}
