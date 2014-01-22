package com.bln.framework.biz.service.facade;

import com.bln.framework.biz.service.IService;
import com.bln.framework.biz.service.IServiceDecorater;
import com.bln.framework.biz.service.facade.listener.IServiceAfterEventListener;
import com.bln.framework.biz.service.facade.listener.IServiceBeforeEventListener;
import com.bln.framework.biz.service.instance.config.IServiceConfig;
import com.bln.framework.mo.IMessageObject;

public class ServiceExecutor implements Runnable{
	
	/**
	 * 请求消息对象
	 */
	IMessageObject reqMo = null;
	
	/**
	 * 服务对象
	 */
	IService service = null;
	
	/**
	 * 服务触发之前的事件监听器
	 */
	protected IServiceBeforeEventListener[] serviceBeforeEventlisteners = null;

	/**
	 * 服务触发之后的事件监听器
	 */
	protected IServiceAfterEventListener[] serviceAfterEventlisteners = null;
	/**
	 * @param reqMo
	 */
	public ServiceExecutor(){
		
	}

	/**
	 * 构造函数
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
	 * 执行业务服务
	 * @return 响应消息对象
	 * @throws Throwable
	 */
	protected IMessageObject execute()throws Throwable {
		
		//1.请求服务
		
		//服务执行之前的事件
		if(serviceBeforeEventlisteners != null && serviceBeforeEventlisteners.length > 0){
			for ( IServiceBeforeEventListener listener : serviceBeforeEventlisteners){
				listener.fire(reqMo);
			}
		}
		
		//如果不存在服务包装器，执行执行服务方法，否则采用服务包装器执行。
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
			
			//触发服务执行完毕事件
			if(serviceAfterEventlisteners != null && serviceAfterEventlisteners.length > 0){
				for ( IServiceAfterEventListener listener : serviceAfterEventlisteners){
					listener.fire(reqMo);
				}
			}
		}
		
		//2.返回响应信息
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
