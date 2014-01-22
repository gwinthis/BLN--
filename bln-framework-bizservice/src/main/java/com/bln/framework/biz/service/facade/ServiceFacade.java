/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.biz.service.facade;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.biz.service.IService;
import com.bln.framework.biz.service.IServiceFacade;
import com.bln.framework.biz.service.IServiceIntercepter;
import com.bln.framework.biz.service.exception.ServcieExecuterException;
import com.bln.framework.biz.service.facade.listener.IServiceAfterEventListener;
import com.bln.framework.biz.service.facade.listener.IServiceBeforeEventListener;
import com.bln.framework.biz.service.facade.listener.IServiceExceptionEventListener;
import com.bln.framework.biz.service.facade.listener.IServiceLastEventListener;
import com.bln.framework.biz.service.facade.message.IExceptionMessage;
import com.bln.framework.biz.service.instance.config.IServiceConfig;
import com.bln.framework.factory.IFactory;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.mo.template.IMoTemplate;
import com.bln.framework.threadpool.IThreadPoolPrototype;
import com.bln.framework.util.asserter.Assert;

/**
 * 服务层门面对象
 */
public class ServiceFacade implements IServiceFacade{

	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(ServiceFacade.class);
	
	/**
	 * 业务服务工厂
	 */
	protected IFactory<Object> serviceFactory = null;
	
	/**
	 * 消息对象模板
	 */
	protected IMoTemplate moTemplate = null;
	
	/**
	 * 异常消息映射
	 */
	protected IExceptionMessage exceptionMessage = null;
	
	/**
	 * 服务拦截器
	 */
	protected IServiceIntercepter[] serviceIntercepters = null;
	
	/**
	 * 服务触发之前的事件监听器
	 */
	protected IServiceBeforeEventListener[] serviceBeforeEventlisteners = null;

	/**
	 * 服务触发之后的事件监听器
	 */
	protected IServiceAfterEventListener[] serviceAfterEventlisteners = null;
	
	/**
	 * 服务即将结束的事件监听器
	 */
	protected IServiceLastEventListener[] serviceLastEventlisteners = null;
	
	/**
	 * 服务出现异常的事件监听器
	 */
	protected IServiceExceptionEventListener[] serviceExceptionEventlisteners = null;
	
	/**
	 * 服务前缀
	 */
	protected String servicePrefix = null;
	
	/**
	 * 线程池原型
	 */
	protected IThreadPoolPrototype threadPoolProto = null;

	/**
	 * 未知异常错误码
	 */
	protected String errorCode4unknowError = null;
	
	/**
	 * 未知异常错误描述
	 */
	protected String errorDesc4unknowError = null;
	

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IServiceFacade#service(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject service(IMessageObject reqMo){
		
		//校验参数
		Assert.paramIsNotNull ( reqMo, "reqMo");
				
		//1.执行服务过滤器，如果返回消息对象不为空，说明该消息对象需要不需要执行服务，返回该消息对象
		try {
			interceptService(reqMo);
		} catch (Throwable e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage(), e);
			}
			reqMo = processException(reqMo, e);
			return reqMo;
		}
		
		//2.获得服务对象
		String serviceId = reqMo.getServiceId();
		
		//添加前缀
		if(!StringUtils.isEmpty(servicePrefix)){
			serviceId = servicePrefix + "." + serviceId;
		}
		
		//3.执行服务
		IMessageObject respMo = null;
		try {
			
			//获取服务
			IService service = (IService)serviceFactory.getNotNullInstance(serviceId);
			
			//执行服务
			respMo = executeService(service, reqMo);
			
		} catch (Throwable e) {
			
			if(log.isErrorEnabled()){
				log.error(e.getMessage(), e);
			}
			respMo = processException(reqMo, e);
			
			//触发服务执行异常事件
			if(serviceExceptionEventlisteners != null && serviceExceptionEventlisteners.length > 0){
				for ( IServiceExceptionEventListener listener : serviceExceptionEventlisteners){
					listener.fire(respMo, e);
				}
			}
		}
				
		//触发服务执行完毕事件
		if(serviceLastEventlisteners != null && serviceLastEventlisteners.length > 0){
			for ( IServiceLastEventListener listener : serviceLastEventlisteners){
				listener.fire(reqMo);
			}
		}
				
		//4.设置响应信息
		moTemplate.requestEnd(respMo);
				
		//5.返回响应信息
		return respMo;
	}
	
	/**
	 * 执行业务服务
	 * @param service 服务对象
	 * @param reqMo 请求消息对象
	 * @return 响应消息对象
	 * @throws Throwable
	 */
	protected IMessageObject executeService(IService service, IMessageObject reqMo)throws Throwable {
		
		//1.建立服务执行器
		ServiceExecutor serviceExecutor = new ServiceExecutor(service, reqMo, serviceBeforeEventlisteners, serviceAfterEventlisteners);
		
		//2.执行服务
		
		//如果不是异步调用，直接调用执行服务方法
		IMessageObject respMo = null;
		
		IServiceConfig serviceConfig = service.getServiceConfig();
		if(!serviceConfig.isAsyncExecute()){
			
			//执行服务
			respMo = serviceExecutor.execute();
			
		}else{
			
			//调用异步执行
			threadPoolProto.getInstance().execute(serviceExecutor);
			
			//设置返回信息
			respMo = moTemplate.serviceSuccessfulEnd(reqMo);
		}
			
		//3.返回响应信息
		return respMo;
		
	}
	
	/**
	 * 拦截服务
	 * @param reqMo 请求消息对象
	 * @throws Throwable
	 */
	protected IMessageObject interceptService(IMessageObject reqMo) throws Throwable {
		
		//1.过滤服务，如果不符合要求，将抛出异常
		IMessageObject respMo = null;
		if(serviceIntercepters != null && serviceIntercepters.length > 0){
			for (IServiceIntercepter serviceIntercepter : serviceIntercepters){
				try {
					respMo = serviceIntercepter.intercept(reqMo);
					if(respMo != null){
						break;
					}
				} catch (Exception e) {
					log.warn("execute intercepter " + serviceIntercepter + " intercept error!", e);
				}
			}
		}
		
		return respMo;
	}
		
	/**
	 * 处理异常信息
	 * @param reqMo
	 * @param e
	 * @return
	 */
	protected IMessageObject processException(IMessageObject reqMo, Throwable e){
		
		//定义响应消息对象
		IMessageObject respMo = null;
		
		//1.获取错误码
		String errorCode = null;
		String errorDesc = null;
		if(e instanceof ServcieExecuterException){
			//如果服务异常中存在消息对象，使用该消息对象
			ServcieExecuterException se = (ServcieExecuterException)e;
			if(se.getMessageObject() != null){
				respMo = se.getMessageObject();
			}
				
		}else{
			
			//使用原因异常来获得错误码
			Throwable cause = ExceptionUtils.getRootCause(e);
			if(cause != null){
				e = cause;
			}
			errorCode = exceptionMessage.getErrorCode(e.getClass().getName());
			errorDesc = exceptionMessage.getErrorDesc(e);
		}
		
		//默认使用未知异常错误码和错误描述
		if(errorCode == null){
			errorCode = this.errorCode4unknowError;
			if(!StringUtils.isEmpty(this.errorDesc4unknowError)){
				errorDesc = this.errorDesc4unknowError;
			}
		}

		//3.设置MO响应信息
		if(respMo == null){
			respMo = reqMo;
		}
		respMo = moTemplate.serviceFailedEnd(respMo, errorCode, errorDesc);

		//4.返回MO
		return respMo;
		
	}

	
	/**
	 * @return the moTemplate
	 */
	public IMoTemplate getMoTemplate() {
		return moTemplate;
	}

	/**
	 * @param moTemplate the moTemplate to set
	 */
	public void setMoTemplate(IMoTemplate moTemplate) {
		this.moTemplate = moTemplate;
	}

	/**
	 * @return the serviceFilters
	 */
	public IServiceIntercepter[] getServiceFilters() {
		return serviceIntercepters;
	}

	/**
	 * @param serviceFilters the serviceFilters to set
	 */
	public void setServiceFilters(IServiceIntercepter[] serviceFilters) {
		this.serviceIntercepters = serviceFilters;
	}

	/**
	 * @return the serviceFactory
	 */
	public IFactory<Object> getServiceFactory() {
		return serviceFactory;
	}

	/**
	 * @param serviceFactory the serviceFactory to set
	 */
	public void setServiceFactory(IFactory<Object> serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	/**
	 * @return the servicePrefix
	 */
	public String getServicePrefix() {
		return servicePrefix;
	}

	/**
	 * @param servicePrefix the servicePrefix to set
	 */
	public void setServicePrefix(String servicePrefix) {
		this.servicePrefix = servicePrefix;
	}

	/**
	 * @return the exceptionMessage
	 */
	public IExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}

	/**
	 * @param exceptionMessage the exceptionMessage to set
	 */
	public void setExceptionMessage(IExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
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
	
	/**
	 * @return the serviceIntercepters
	 */
	public IServiceIntercepter[] getServiceIntercepters() {
		return serviceIntercepters;
	}

	/**
	 * @param serviceIntercepters the serviceIntercepters to set
	 */
	public void setServiceIntercepters(IServiceIntercepter[] serviceIntercepters) {
		this.serviceIntercepters = serviceIntercepters;
	}

	/**
	 * @return the errorCode4unknowError
	 */
	public String getErrorCode4unknowError() {
		return errorCode4unknowError;
	}

	/**
	 * @param errorCode4unknowError the errorCode4unknowError to set
	 */
	public void setErrorCode4unknowError(String errorCode4unknowError) {
		this.errorCode4unknowError = errorCode4unknowError;
	}

	/**
	 * @return the errorDesc4unknowError
	 */
	public String getErrorDesc4unknowError() {
		return errorDesc4unknowError;
	}

	/**
	 * @param errorDesc4unknowError the errorDesc4unknowError to set
	 */
	public void setErrorDesc4unknowError(String errorDesc4unknowError) {
		this.errorDesc4unknowError = errorDesc4unknowError;
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

	public IServiceLastEventListener[] getServiceLastEventlisteners() {
		return serviceLastEventlisteners;
	}

	public void setServiceLastEventlisteners(
			IServiceLastEventListener[] serviceLastEventlisteners) {
		this.serviceLastEventlisteners = serviceLastEventlisteners;
	}

	public IServiceExceptionEventListener[] getServiceExceptionEventlisteners() {
		return serviceExceptionEventlisteners;
	}

	public void setServiceExceptionEventlisteners(
			IServiceExceptionEventListener[] serviceExceptionEventlisteners) {
		this.serviceExceptionEventlisteners = serviceExceptionEventlisteners;
	}
}
