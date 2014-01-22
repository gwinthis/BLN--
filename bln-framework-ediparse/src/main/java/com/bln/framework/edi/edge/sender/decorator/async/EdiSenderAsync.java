/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.edi.edge.sender.decorator.async;

import com.bln.framework.edi.edge.sender.IEdiSender;
import com.bln.framework.edi.edge.sender.decorator.async.param.OnFailedParam;
import com.bln.framework.edi.edge.sender.decorator.async.param.OnSuccessParam;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.threadpool.IThreadPoolPrototype;
import com.bln.framework.util.callback.ICallBackable;

/**
 * EDI�첽����
 */
public class EdiSenderAsync implements IEdiSenderDecorator{

	/**
	 * �̳߳�ԭ��
	 */
	IThreadPoolPrototype threadPoolProto = null;
	
	/**
	 * ��������
	 */
	SendCommand sendCmd = new SendCommand();
	
	/**
	 * ���캯��
	 * @param ediSender EDI������
	 * @param onSuccessHandler ���ͳɹ�������
	 * @param onFailedHandler ����ʧ�ܴ�����
	 */
	public EdiSenderAsync(IEdiSender ediSender, ICallBackable onSuccessHandler, ICallBackable onFailedHandler){
		
		sendCmd.setEdiSender(ediSender);
		sendCmd.setOnSuccessHandler(onSuccessHandler);
		sendCmd.setOnFailedHandler(onFailedHandler);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.sender.IEdiSender#send(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject send(IMessageObject reqMo) throws Throwable {
		
		//1.װ���첽�����������
		
		//1.1����Ҫ���͵�����
		sendCmd.setReqMo(reqMo);
		
		//2.���÷�������
		threadPoolProto.getInstance().execute(sendCmd);
		
		//3.���ؿ�
		return null;
	}
	
	/**
	 * ��������
	 */
	protected class SendCommand implements Runnable{

		/**
		 * EDI������
		 */
		IEdiSender ediSender = null;

		/**
		 * Ҫ���͵�����
		 */
		protected IMessageObject reqMo = null;
		
		/**
		 * �����ɹ��Ĵ�����
		 */
		protected ICallBackable onSuccessHandler = null;

		/**
		 * ����ʧ�ܵĴ�����
		 */
		protected ICallBackable onFailedHandler = null;

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			
			try {
				
				//EDI����
				IMessageObject respMo = ediSender.send(reqMo);
				
				//����ɹ���������Ϊ�գ����óɹ�������
				if(onSuccessHandler != null){
					
					//�������
					OnSuccessParam param = new OnSuccessParam();
					param.setRespData(respMo);
					
					//ִ�лص�����
					onSuccessHandler.callBack(param);
				}

			} catch (Throwable e) {
				
				//��ʧ�ܴ�������Ϊ�գ�����ʧ�ܴ�����
				if(onFailedHandler != null){
					
					//�������
					OnFailedParam param = new OnFailedParam();
					param.setException(e);
					
					//ִ�лص�����
					onFailedHandler.callBack(param);
				}
			}
		}

		public ICallBackable getOnSuccessHandler() {
			return onSuccessHandler;
		}


		public void setOnSuccessHandler(ICallBackable onSuccessHandler) {
			this.onSuccessHandler = onSuccessHandler;
		}


		public ICallBackable getOnFailedHandler() {
			return onFailedHandler;
		}


		public void setOnFailedHandler(ICallBackable onFailedHandler) {
			this.onFailedHandler = onFailedHandler;
		}


		public IEdiSender getEdiSender() {
			return ediSender;
		}


		public void setEdiSender(IEdiSender ediSender) {
			this.ediSender = ediSender;
		}


		public IMessageObject getReqMo() {
			return reqMo;
		}


		public void setReqMo(IMessageObject reqMo) {
			this.reqMo = reqMo;
		}
		
	}

	/**
	 * @return
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#getEdiSender()
	 */
	public IEdiSender getEdiSender() {
		return sendCmd.getEdiSender();
	}

	/**
	 * @return
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#getOnFailedHandler()
	 */
	public ICallBackable getOnFailedHandler() {
		return sendCmd.getOnFailedHandler();
	}

	/**
	 * @return
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#getOnSuccessHandler()
	 */
	public ICallBackable getOnSuccessHandler() {
		return sendCmd.getOnSuccessHandler();
	}

	/**
	 * @param ediSender
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#setEdiSender(com.bln.framework.edi.edge.sender.IEdiSender)
	 */
	public void setEdiSender(IEdiSender ediSender) {
		sendCmd.setEdiSender(ediSender);
	}

	/**
	 * @param onFailedHandler
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#setOnFailedHandler(com.bln.framework.util.callback.ICallBackable)
	 */
	public void setOnFailedHandler(ICallBackable onFailedHandler) {
		sendCmd.setOnFailedHandler(onFailedHandler);
	}

	/**
	 * @param onSuccessHandler
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#setOnSuccessHandler(com.bln.framework.util.callback.ICallBackable)
	 */
	public void setOnSuccessHandler(ICallBackable onSuccessHandler) {
		sendCmd.setOnSuccessHandler(onSuccessHandler);
	}

	/**
	 * @return the threadPoolProto
	 */
	public IThreadPoolPrototype getThreadPoolProto() {
		return threadPoolProto;
	}

	/**
	 * @param threadPoolProto the threadPoolProto to set
	 */
	public void setThreadPoolProto(IThreadPoolPrototype threadPoolProto) {
		this.threadPoolProto = threadPoolProto;
	}

}
