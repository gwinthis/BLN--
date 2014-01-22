/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.session.exception;

import com.bln.framework.exception.CheckedException;

/**
 * Œ¥∑¢œ÷SessionInfo“Ï≥£
 */
public class NotFoundSessionInfoException extends CheckedException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;
	
	public NotFoundSessionInfoException(){
		
	}
	
	public NotFoundSessionInfoException(String info){
		super(info);
	}

}
