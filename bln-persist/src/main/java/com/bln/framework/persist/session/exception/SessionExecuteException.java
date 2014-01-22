/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.session.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * Session执行所抛出的异常
 */
public class SessionExecuteException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public SessionExecuteException(){
		super();
	}
	
	public SessionExecuteException(String info){
		super(info);
	}
}
