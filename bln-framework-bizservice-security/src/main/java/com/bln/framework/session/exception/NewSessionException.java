/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.session.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ����Session�쳣
 */
public class NewSessionException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;
	
	public NewSessionException(){
		
	}
	
	public NewSessionException(String info){
		super(info);
	}

}
