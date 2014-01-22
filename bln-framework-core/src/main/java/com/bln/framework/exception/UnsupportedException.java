/**
 * @author gengw
 * Created on 2012-03-17
 */
package com.bln.framework.exception;

/**
 * 不支持该功能的异常
 */
public class UnsupportedException extends DeveloperConfigErrorException{

	public UnsupportedException(){
		super();
	}
	
	public UnsupportedException(String info){
		super(info);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1314004625899496703L;

}
