package com.bln.framework.persist.sql.dialect.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * 不支持的的SQL异常
 */
public class NotSupportedSQLException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public NotSupportedSQLException(){
		super();
	}
	
	public NotSupportedSQLException(String info){
		super(info);
	}
}
