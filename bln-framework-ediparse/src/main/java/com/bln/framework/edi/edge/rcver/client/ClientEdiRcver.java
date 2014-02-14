/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.edi.edge.rcver.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.biz.service.IServiceFacade;
import com.bln.framework.edi.edge.rcver.EdiRcverBase;
import com.bln.framework.edi.edge.rcver.listener.IMOConsumeListener;
import com.bln.framework.edi.service.facadelocator.IServiceFacadeLocator;
import com.bln.framework.mo.IMessageObject;

/**
 * �ͻ��˱��Ľ�����
 * ��������Ϣ�����ύ�������
 */
public class ClientEdiRcver extends EdiRcverBase{

	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(ClientEdiRcver.class);

	
	/**
	 * ������������λ��
	 */
	IServiceFacadeLocator serviceFacadeLocator = null;
	
	/**
	 * MO���Ѽ�����
	 */
	IMOConsumeListener[] moConsumeListeners = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.rcver.EdiRcverBase#consumeReqMo(com.bln.framework.mo.IMessageObject)
	 */
	@Override
	protected IMessageObject consumeReqMo(IMessageObject reqMo, Object originalRequestObject) throws Throwable {
		
		//1.��λ��������
		IServiceFacade serviceFacade = serviceFacadeLocator.locate(reqMo, originalRequestObject);
		
		//2.ִ�з���
		
		//2.1��������ִ��ǰ�¼�
		if(moConsumeListeners != null && moConsumeListeners.length > 0){
			for ( IMOConsumeListener listener : moConsumeListeners ){
				try {
					listener.beforExecuteService(reqMo);
				}
				catch (Exception e) {
					log.warn("execute listener " + listener + " beforExecuteService error!", e);
				}
			}
		}
		
		//2.2��������ִ��
		IMessageObject respMo = serviceFacade.service(reqMo);
		
		//2.3��������ִ�����¼�
		if(moConsumeListeners != null && moConsumeListeners.length > 0){
			for ( IMOConsumeListener listener : moConsumeListeners ){
				try {
					listener.afterExecuteService(reqMo, respMo);
				}
				catch (Exception e) {
					log.warn("execute listener " + listener + " afterExecuteService error!", e);
				}
			}
		}
		
		//3.������Ӧ����
		return respMo;
	}

	public IServiceFacadeLocator getServiceFacadeLocator() {
		return serviceFacadeLocator;
	}

	public void setServiceFacadeLocator(IServiceFacadeLocator serviceFacadeLocator) {
		this.serviceFacadeLocator = serviceFacadeLocator;
	}

}
