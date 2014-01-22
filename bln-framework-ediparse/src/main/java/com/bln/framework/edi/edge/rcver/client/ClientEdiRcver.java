/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.edi.edge.rcver.client;

import com.bln.framework.biz.service.IServiceFacade;
import com.bln.framework.edi.edge.rcver.EdiRcverBase;
import com.bln.framework.mo.IMessageObject;

/**
 * �ͻ��˱��Ľ�����
 * ��������Ϣ�����ύ�������
 */
public class ClientEdiRcver extends EdiRcverBase{

	/**
	 * ҵ����������
	 */
	IServiceFacade serviceFacade = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.rcver.EdiRcverBase#consumeReqMo(com.bln.framework.mo.IMessageObject)
	 */
	@Override
	protected IMessageObject consumeReqMo(IMessageObject reqMo, Object originalRequestObject) throws Throwable {
		
		//1.��������Ϣ���������
		return serviceFacade.service(reqMo);
	}

	/**
	 * @return the serviceFacade
	 */
	public IServiceFacade getServiceFacade() {
		return serviceFacade;
	}

	/**
	 * @param serviceFacade the serviceFacade to set
	 */
	public void setServiceFacade(IServiceFacade serviceFacade) {
		this.serviceFacade = serviceFacade;
	}
}
