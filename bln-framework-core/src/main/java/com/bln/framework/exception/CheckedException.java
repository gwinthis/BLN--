/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.exception;

import org.apache.commons.lang3.exception.ContextedException;
import org.apache.commons.lang3.exception.ExceptionContext;

/**
 * 可捕获的异常，该异常
 */
public class CheckedException extends ContextedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5700446535465374268L;

	
	public CheckedException(String message, Throwable cause, ExceptionContext context) {
		super(message, cause, context);
		
	}

	public CheckedException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CheckedException(Throwable cause) {
		super(cause);
		
	}


	public CheckedException(){
		super();
	}
	
	public CheckedException(String info){
		super(info);
	}
}
