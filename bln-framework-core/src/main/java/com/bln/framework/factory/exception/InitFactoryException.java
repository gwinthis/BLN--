/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.factory.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * 初始化工厂时发生错误，所抛出的异常
 */
public class InitFactoryException extends DeveloperConfigErrorException{

	public InitFactoryException(){
		super();
	}
	
	public InitFactoryException(String info){
		super(info);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;

}
