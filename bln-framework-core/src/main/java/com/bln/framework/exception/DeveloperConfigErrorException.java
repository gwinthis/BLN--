/**
 * @author gengw
 * Created on 2012-03-17
 */
package com.bln.framework.exception;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

/**
 * <p>开发人员配置错误异常</p>
 * <p>凡是JAVA编译器可通过，可是在运行时刻，如果遇到框架不希望看到的现象，即会抛出该异常。</p>
 * <p>比如从工厂中没有取到实例等错误。</p>
 */
public class DeveloperConfigErrorException extends ContextedRuntimeException{

	public DeveloperConfigErrorException(){
		super();
	}
	
	public DeveloperConfigErrorException(String info){
		super(info);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1314004625899496703L;

}
