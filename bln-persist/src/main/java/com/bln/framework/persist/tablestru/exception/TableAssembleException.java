/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * 表结构装配时所抛出的异常
 */
public class TableAssembleException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public TableAssembleException(){
		super();
	}
	
	public TableAssembleException(String info){
		super(info);
	}
}
