/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.valgenerator.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ����������ֵ���׳����쳣
 */
public class GeneratorException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public GeneratorException(){
		super();
	}
	
	public GeneratorException(String info){
		super(info);
	}
}
