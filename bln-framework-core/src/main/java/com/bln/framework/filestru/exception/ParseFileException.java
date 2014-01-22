/**
 * @author gengw
 * Created at 2012-03-17
 */
package com.bln.framework.filestru.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * 解析文件异常
 */
public class ParseFileException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8911484059013885264L;
	
	public ParseFileException(){
		
	}
	
	public ParseFileException(String info){
		super(info);
	}

}
