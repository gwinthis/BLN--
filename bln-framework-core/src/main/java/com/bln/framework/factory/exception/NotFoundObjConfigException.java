/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.factory.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * δ���ֶ��������쳣
 */
public class NotFoundObjConfigException extends DeveloperConfigErrorException{

	public NotFoundObjConfigException(){
		super();
	}
	
	public NotFoundObjConfigException(String info){
		super(info);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;

}
