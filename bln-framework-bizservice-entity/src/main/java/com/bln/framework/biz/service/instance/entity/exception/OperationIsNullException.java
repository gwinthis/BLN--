/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.biz.service.instance.entity.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * operationÎª¿ÕÒì³£
 */
public class OperationIsNullException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;
	
	public OperationIsNullException(){
		
	}
	
	public OperationIsNullException(String info){
		super(info);
	}

}
