/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.factory.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ªÏ»Î—≠ª∑“¿¿µ“Ï≥£
 */
public class ConfigDependencyCircleException extends DeveloperConfigErrorException{

	public ConfigDependencyCircleException(){
		super();
	}
	
	public ConfigDependencyCircleException(String info){
		super(info);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;

}
