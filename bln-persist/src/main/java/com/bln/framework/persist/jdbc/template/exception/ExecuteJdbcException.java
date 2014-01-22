/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.jdbc.template.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * 转换时所抛出的异常
 */
public class ExecuteJdbcException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public ExecuteJdbcException(){
		super();
	}
	
	public ExecuteJdbcException(String info){
		super(info);
	}
}
