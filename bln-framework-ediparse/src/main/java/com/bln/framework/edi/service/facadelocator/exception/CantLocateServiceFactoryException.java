/**
 * @author gengw
 * Created at 2014-02-14
 */
package com.bln.framework.edi.service.facadelocator.exception;

import org.apache.commons.lang3.exception.ExceptionContext;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * �޷���λ�������������쳣
 */
public class CantLocateServiceFactoryException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CantLocateServiceFactoryException() {
		super();
		
	}

	public CantLocateServiceFactoryException(String info) {
		super(info);
	}
	
	public CantLocateServiceFactoryException(String message, Throwable cause,
			ExceptionContext context) {
		super(message, cause, context);
	}

	public CantLocateServiceFactoryException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public CantLocateServiceFactoryException(Throwable cause) {
		super(cause);
		
	}

}
