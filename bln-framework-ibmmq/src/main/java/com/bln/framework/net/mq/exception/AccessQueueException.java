package com.bln.framework.net.mq.exception;

import com.bln.framework.exception.CheckedException;

/**
 * Queue∑√Œ “Ï≥£
 */
public class AccessQueueException extends CheckedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public AccessQueueException(){
		super();
	}
	
	public AccessQueueException(String info){
		super(info);
	}
}
