/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.session.factory.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ת��ʱ���׳����쳣
 */
public class OpenSessionException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public OpenSessionException(){
		super();
	}
	
	public OpenSessionException(String info){
		super(info);
	}
}
