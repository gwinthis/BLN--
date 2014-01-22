/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.edi.edge.sender.decorator.async.param;

import com.bln.framework.util.callback.CallbackParam;

/**
 * ��������ɹ�ʱ�����õĲ���
 */
public class OnFailedParam extends CallbackParam{

	/**
	 * �쳣��Ϣ
	 */
	protected Throwable exception = null;

	/**
	 * ����쳣��Ϣ
	 * @return
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * �����쳣��Ϣ
	 * @param exception
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
}
