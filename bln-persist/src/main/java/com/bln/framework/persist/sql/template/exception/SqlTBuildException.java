/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.sql.template.exception;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ����sqltʱ���׳����쳣
 */
public class SqlTBuildException extends DeveloperConfigErrorException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999869390801206915L;

	public SqlTBuildException(){
		super();
	}
	
	public SqlTBuildException(String info){
		super(info);
	}
}
