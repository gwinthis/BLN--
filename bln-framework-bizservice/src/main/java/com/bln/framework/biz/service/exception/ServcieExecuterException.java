/**
 * @author gengw
 * Created on May 24, 2012
 */
package com.bln.framework.biz.service.exception;

import com.bln.framework.exception.CheckedException;
import com.bln.framework.mo.IMessageObject;

/**
 * ҵ�����ִ���쳣
 */
public class ServcieExecuterException extends CheckedException{
	
	/**
	 * ����������Ϣ����
	 */
	protected IMessageObject messageObject = null;
	
	/**
	 * ���к�
	 */
	private static final long serialVersionUID = -1486076973409308003L;
	
	/**
	 * ���캯��
	 */
	public ServcieExecuterException(){
		super();
	}
	
	/**
	 * ���캯��
	 * @param info ������Ϣ
	 * @param messageObject Ҫ���ص���Ϣ����
	 */
	public ServcieExecuterException(String info, IMessageObject messageObject){
		super(info);
		this.messageObject = messageObject;
	}

	/**
	 * @return the messageObject
	 */
	public IMessageObject getMessageObject() {
		return messageObject;
	}

	/**
	 * @param messageObject the messageObject to set
	 */
	public void setMessageObject(IMessageObject messageObject) {
		this.messageObject = messageObject;
	}
}
