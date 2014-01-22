/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.builder.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ����ʱ���׳����쳣
 */
public class BuildException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public BuildException(){
		super();
	}
	
	public BuildException(String info){
		super(info);
	}
}
