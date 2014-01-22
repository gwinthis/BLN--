/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.factory.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * 获取对象时发生错误，所抛出的异常
 */
public class SetPropertyErrorException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;
	
	public SetPropertyErrorException(){
		
	}
	
	public SetPropertyErrorException(String info){
		super(info);
	}

}
