/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.factory.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * —≠ª∑“¿¿µ“Ï≥£
 */
public class DependencyCircleException extends DeveloperConfigErrorException{

	public DependencyCircleException(){
		super();
	}
	
	public DependencyCircleException(String info){
		super(info);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;

}
