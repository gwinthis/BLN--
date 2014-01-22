/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.session.builder.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * 转换时所抛出的异常
 */
public class BuildSessionException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public BuildSessionException(){
		super();
	}
	
	public BuildSessionException(String info){
		super(info);
	}
}
