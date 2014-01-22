/**
 * @author gengw
 * Created on Jun 12, 2012
 */
package com.bln.framework.net.client.exception;

import com.bln.framework.exception.CheckedException;

/**
 * Õ¯¬Á∑¢ÀÕ“Ï≥£
 */
public class NetSendException extends CheckedException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NetSendException(){
		super();
	}
	
	public NetSendException(String info){
		super(info);
	}

}
