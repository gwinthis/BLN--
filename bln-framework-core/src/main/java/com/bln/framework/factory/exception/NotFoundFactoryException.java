/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.factory.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * 未发现工厂异常
 */
public class NotFoundFactoryException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;
	
	public NotFoundFactoryException(){
		
	}
	
	public NotFoundFactoryException(String info){
		super(info);
	}

}
