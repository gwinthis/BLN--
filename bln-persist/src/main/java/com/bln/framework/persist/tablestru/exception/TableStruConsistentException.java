/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ��ṹװ��ʱ���׳����쳣
 */
public class TableStruConsistentException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public TableStruConsistentException(){
		super();
	}
	
	public TableStruConsistentException(String info){
		super(info);
	}
}
