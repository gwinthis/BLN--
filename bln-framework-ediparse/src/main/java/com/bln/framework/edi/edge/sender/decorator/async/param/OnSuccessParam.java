/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.edi.edge.sender.decorator.async.param;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.util.callback.CallbackParam;

/**
 * ��������ʧ��ʱ�����õĲ���
 */
public class OnSuccessParam extends CallbackParam{

	/**
	 * ��Ӧ��Ϣ
	 */
	protected IMessageObject respData = null;

	/**
	 * �����Ӧ��Ϣ
	 * @return
	 */
	public IMessageObject getRespData() {
		return respData;
	}

	/**
	 * ������Ӧ��Ϣ
	 * @param respData
	 */
	public void setRespData(IMessageObject respData) {
		this.respData = respData;
	}
}
