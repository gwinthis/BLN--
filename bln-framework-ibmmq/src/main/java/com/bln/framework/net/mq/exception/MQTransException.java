package com.bln.framework.net.mq.exception;

import com.bln.framework.exception.CheckedException;

/**
 * MQ事务处理异常
 */
public class MQTransException extends CheckedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public MQTransException(){
		super();
	}
	
	public MQTransException(String info){
		super(info);
	}
}
