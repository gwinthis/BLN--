/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.session.exception;

import com.bln.framework.exception.CheckedException;

/**
 * 持久化陈旧实体所抛出的异常
 */
public class PersistStaleEntityException extends CheckedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public PersistStaleEntityException(){
		super();
	}
	
	public PersistStaleEntityException(String info){
		super(info);
	}
}
