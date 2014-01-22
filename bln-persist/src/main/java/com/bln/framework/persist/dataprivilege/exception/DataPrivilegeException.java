/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.dataprivilege.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * 处理数据权限时所抛出的异常
 */
public class DataPrivilegeException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public DataPrivilegeException(){
		super();
	}
	
	public DataPrivilegeException(String info){
		super(info);
	}
}
