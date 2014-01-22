package com.bln.framework.biz.service.facade;

import com.bln.framework.biz.service.IService;
import com.bln.framework.biz.service.IServiceDecorater;
import com.bln.framework.biz.service.facade.listener.IServiceAfterEventListener;
import com.bln.framework.biz.service.facade.listener.IServiceBeforeEventListener;
import com.bln.framework.biz.service.instance.config.IServiceConfig;
import com.bln.framework.mo.IMessageObject;

public class ServiceExecutor implements Runnable{
	
	/**
	 * ������Ϣ����
	 */
	IMessageObject reqMo = null;
	
	/**
	 * �������
	 */
	IService service = null;
	
	/**
	 * ���񴥷�֮ǰ���¼�������
	 */
	protected IServiceBeforeEventListener[] serviceBeforeEventlisteners = null;

	/**
	 * ���񴥷�֮����¼�������
	 */
	protected IServiceAfterEventListener[] serviceAfterEventlisteners = null;
	/**
	 * @param reqMo
	 */
	public ServiceExecutor(){
		
	}

	/**
	 * ���캯��
	 * @param reqMo
	 * @param service
	 */
	public ServiceExecutor(IService service, IMessageObject reqMo, IServiceBeforeEventListener[] serviceBeforeEventlisteners, IServiceAfterEventListener[] serviceAfterEventlisteners) {
		super();
		
		this.reqMo = reqMo;
		this.service = service;
		this.serviceBeforeEventlisteners = serviceBeforeEventlisteners;
		this.serviceAfterEventlisteners = serviceAfterEventlisteners;
	}
	
	/**
	 * ִ��ҵ�����
	 * @return ��Ӧ��Ϣ����
	 * @throws Throwable
	 */
	protected IMessageObject execute()throws Throwable {
		
		//1.�������
		
		//����ִ��֮ǰ���¼�
		if(serviceBeforeEventlisteners != null && serviceBeforeEventlisteners.length > 0){
			for ( IServiceBeforeEventListener listener : serviceBeforeEventlisteners){
				listener.fire(reqMo);
			}
		}
		
		//��������ڷ����װ����ִ��ִ�з��񷽷���������÷����װ��ִ�С�
		IMessageObject respMo = null;
		
		IServiceConfig serviceConfig = service.getServiceConfig();
		IServiceDecorater serviceDecorater = serviceConfig.getServiceDecorater();
		try {
			if (serviceDecorater == null) {
				respMo = service.execute(reqMo);
			} else {
				serviceDecorater.setService(service);
				respMo = serviceDecorater.service(reqMo);
			}
		} catch (Throwable e) {
			
			throw e;
			
		}finally{
			
			//��������ִ������¼�
			if(serviceAfterEventlisteners != null && serviceAfterEventlisteners.length > 0){
				for ( IServiceAfterEventListener listener : serviceAfterEventlisteners){
					listener.fire(reqMo);
				}
			}
		}
		
		//2.������Ӧ��Ϣ
		return respMo;
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			this.execute();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @return the reqMo
	 */
	public IMessageObject getReqMo() {
		return reqMo;
	}

	/**
	 * @param reqMo the reqMo to set
	 */
	public void setReqMo(IMessageObject reqMo) {
		this.reqMo = reqMo;
	}

	/**
	 * @return the service
	 */
	public IService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(IService service) {
		this.service = service;
	}

	public IServiceBeforeEventListener[] getServiceBeforeEventlisteners() {
		return serviceBeforeEventlisteners;
	}

	public void setServiceBeforeEventlisteners(
			IServiceBeforeEventListener[] serviceBeforeEventlisteners) {
		this.serviceBeforeEventlisteners = serviceBeforeEventlisteners;
	}

	public IServiceAfterEventListener[] getServiceAfterEventlisteners() {
		return serviceAfterEventlisteners;
	}

	public void setServiceAfterEventlisteners(
			IServiceAfterEventListener[] serviceAfterEventlisteners) {
		this.serviceAfterEventlisteners = serviceAfterEventlisteners;
	}
}
