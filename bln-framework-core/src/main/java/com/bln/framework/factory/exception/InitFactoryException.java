/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.factory.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ��ʼ������ʱ�����������׳����쳣
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
