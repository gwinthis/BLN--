/**
 * @author gengw
 * Created on 2012-03-17
 */
package com.bln.framework.exception;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

/**
 * <p>������Ա���ô����쳣</p>
 * <p>����JAVA��������ͨ��������������ʱ�̣����������ܲ�ϣ�����������󣬼����׳����쳣��</p>
 * <p>����ӹ�����û��ȡ��ʵ���ȴ���</p>
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
