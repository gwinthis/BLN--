/**
 * @author gengw
 * Created on May 24, 2012
 */
package com.bln.framework.biz.service.exception;

import com.bln.framework.exception.CheckedException;
import com.bln.framework.mo.IMessageObject;

/**
 * 业务服务执行异常
 */
public class ServcieExecuterException extends CheckedException{
	
	/**
	 * 所包含的消息对象
	 */
	protected IMessageObject messageObject = null;
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1486076973409308003L;
	
	/**
	 * 构造函数
	 */
	public ServcieExecuterException(){
		super();
	}
	
	/**
	 * 构造函数
	 * @param info 错误消息
	 * @param messageObject 要返回的消息对象
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
