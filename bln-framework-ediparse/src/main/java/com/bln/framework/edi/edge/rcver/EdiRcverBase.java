/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.edi.edge.rcver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.edi.edge.EdiEdgeBase;
import com.bln.framework.edi.edge.rcver.listener.IRcverListener;
import com.bln.framework.mo.IMessageObject;

/**
 * EDI������
 */
public abstract class EdiRcverBase extends EdiEdgeBase implements IEdiRcver {
	
	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(EdiRcverBase.class);
	
	/**
	 * ������
	 */
	protected IRcverListener[] listeners = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.rcver.EdiRcverBase#receive(byte[])
	 */
	public byte[] receive(byte[] reqEdiData, Object originalRequestObject) throws Throwable{
		
		byte[] respData = null;
		try{
			
			//����������
			if(listeners != null && listeners.length > 0){
				for ( IRcverListener listener : listeners){
					try{
						listener.startRequest(reqEdiData, originalRequestObject);
					}catch(Throwable e){
						log.warn("call listener " + listener + " startRequest error! ", e);
					}
				}
			}
			
			//1.����������
			reqEdiData = this.requestEdiFilter(reqEdiData);	
			
			//2.�������EDI
			if(this.outputRequestEdi){
				this.ediOutput(reqEdiData, encoding);
			}
			
			//3.������Ϣ����
			IMessageObject reqMo = moBuilder.buildMo(reqEdiData);
			
			//���������־
			if(log.isInfoEnabled()){
				StringBuilder info = new StringBuilder("it received request action ");
				info.append(reqMo.getServiceId());
				info.append(", starting process!");
				
				log.info(info);
			}
			
			//4.����������Ϣ����
			IMessageObject respMo = this.consumeReqMo(reqMo, originalRequestObject);
			
			//5.������Ӧ��Ϣ���ֽ�����
			respData = fileBuilderByMo.buildFile(respMo);
			
			//�����Ӧ����
			if(outputResponseEdi){
				this.ediOutput(respData, encoding);
			}
			
			//6.������Ӧ����
			respData = this.responseEdiFilter(respData);
			
			//���������־
			if(log.isInfoEnabled()){
				StringBuilder info = new StringBuilder("action ");
				info.append(reqMo.getServiceId());
				info.append(" has been processed sucessfully!");
				
				log.info(info);
			}
			
		}finally{
			//����������
			if(listeners != null && listeners.length > 0){
				for ( IRcverListener listener : listeners){
					try{
						listener.finishRequest(reqEdiData, respData, originalRequestObject);
					}catch(Throwable e){
						log.warn("call listener " + listener + " finishRequest error! ", e);
					}
				}
			}
		}
		
		//7.������Ӧ��Ϣ
		return respData;

	}
	
	/**
	 * ���ѽ��յ�����Ϣ����
	 * @param reqMo ��Ϣ����ͨ�����յ���EDI������װ����
	 * @param originalRequestObject ԭʼ�����������HTTPServletRequest
	 * @return ��Ӧ��Ϣ����Ϣ����
	 * @throws Throwable
	 */
	protected abstract IMessageObject consumeReqMo(IMessageObject reqMo, Object originalRequestObject) throws Throwable;

	/**
	 * @return the listeners
	 */
	public IRcverListener[] getListeners() {
		return listeners;
	}

	/**
	 * @param listeners the listeners to set
	 */
	public void setListeners(IRcverListener[] listeners) {
		this.listeners = listeners;
	}

}
