package com.bln.framework.persist.sql.dialect.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ��֧�ֵĵ�SQL�쳣
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
