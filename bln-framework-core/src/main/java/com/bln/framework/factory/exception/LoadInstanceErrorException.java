/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.factory.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ��ȡ����ʱ�����������׳����쳣
 */
public class LoadInstanceErrorException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;
	
	public LoadInstanceErrorException(){
		
	}
	
	public LoadInstanceErrorException(String info){
		super(info);
	}

}
