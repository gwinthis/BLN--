/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.lib.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ����ʱ���׳����쳣
 */
public class LoadTableStruException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public LoadTableStruException(){
		super();
	}
	
	public LoadTableStruException(String info){
		super(info);
	}
}
