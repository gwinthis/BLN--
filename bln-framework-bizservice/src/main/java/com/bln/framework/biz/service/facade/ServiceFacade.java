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
 * ������������
 */
public class ServiceFacade implements IServiceFacade{

	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(ServiceFacade.class);
	
	/**
	 * ҵ����񹤳�
	 */
	protected IFactory<Object> serviceFactory = null;
	
	/**
	 * ��Ϣ����ģ��
	 */
	protected IMoTemplate moTemplate = null;
	
	/**
	 * �쳣��Ϣӳ��
	 */
	protected IExceptionMessage exceptionMessage = null;
	
	/**
	 * ����������
	 */
	protected IServiceIntercepter[] serviceIntercepters = null;
	
	/**
	 * ���񴥷�֮ǰ���¼�������
	 */
	protected IServiceBeforeEventListener[] serviceBeforeEventlisteners = null;

	/**
	 * ���񴥷�֮����¼�������
	 */
	protected IServiceAfterEventListener[] serviceAfterEventlisteners = null;
	
	/**
	 * ���񼴽��������¼�������
	 */
	protected IServiceLastEventListener[] serviceLastEventlisteners = null;
	
	/**
	 * ��������쳣���¼�������
	 */
	protected IServiceExceptionEventListener[] serviceExceptionEventlisteners = null;
	
	/**
	 * ����ǰ׺
	 */
	protected String servicePrefix = null;
	
	/**
	 * �̳߳�ԭ��
	 */
	protected IThreadPoolPrototype threadPoolProto = null;

	/**
	 * δ֪�쳣������
	 */
	protected String errorCode4unknowError = null;
	
	/**
	 * δ֪�쳣��������
	 */
	protected String errorDesc4unknowError = null;
	

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IServiceFacade#service(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject service(IMessageObject reqMo){
		
		//У�����
		Assert.paramIsNotNull ( reqMo, "reqMo");
				
		//1.ִ�з�������������������Ϣ����Ϊ�գ�˵������Ϣ������Ҫ����Ҫִ�з��񣬷��ظ���Ϣ����
		try {
			interceptService(reqMo);
		} catch (Throwable e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage(), e);
			}
			reqMo = processException(reqMo, e);
			return reqMo;
		}
		
		//2.��÷������
		String serviceId = reqMo.getServiceId();
		
		//���ǰ׺
		if(!StringUtils.isEmpty(servicePrefix)){
			serviceId = servicePrefix + "." + serviceId;
		}
		
		//3.ִ�з���
		IMessageObject respMo = null;
		try {
			
			//��ȡ����
			IService service = (IService)serviceFactory.getNotNullInstance(serviceId);
			
			//ִ�з���
			respMo = executeService(service, reqMo);
			
		} catch (Throwable e) {
			
			if(log.isErrorEnabled()){
				log.error(e.getMessage(), e);
			}
			respMo = processException(reqMo, e);
			
			//��������ִ���쳣�¼�
			if(serviceExceptionEventlisteners != null && serviceExceptionEventlisteners.length > 0){
				for ( IServiceExceptionEventListener listener : serviceExceptionEventlisteners){
					listener.fire(respMo, e);
				}
			}
		}
				
		//��������ִ������¼�
		if(serviceLastEventlisteners != null && serviceLastEventlisteners.length > 0){
			for ( IServiceLastEventListener listener : serviceLastEventlisteners){
				listener.fire(reqMo);
			}
		}
				
		//4.������Ӧ��Ϣ
		moTemplate.requestEnd(respMo);
				
		//5.������Ӧ��Ϣ
		return respMo;
	}
	
	/**
	 * ִ��ҵ�����
	 * @param service �������
	 * @param reqMo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
	 * @throws Throwable
	 */
	protected IMessageObject executeService(IService service, IMessageObject reqMo)throws Throwable {
		
		//1.��������ִ����
		ServiceExecutor serviceExecutor = new ServiceExecutor(service, reqMo, serviceBeforeEventlisteners, serviceAfterEventlisteners);
		
		//2.ִ�з���
		
		//��������첽���ã�ֱ�ӵ���ִ�з��񷽷�
		IMessageObject respMo = null;
		
		IServiceConfig serviceConfig = service.getServiceConfig();
		if(!serviceConfig.isAsyncExecute()){
			
			//ִ�з���
			respMo = serviceExecutor.execute();
			
		}else{
			
			//�����첽ִ��
			threadPoolProto.getInstance().execute(serviceExecutor);
			
			//���÷�����Ϣ
			respMo = moTemplate.serviceSuccessfulEnd(reqMo);
		}
			
		//3.������Ӧ��Ϣ
		return respMo;
		
	}
	
	/**
	 * ���ط���
	 * @param reqMo ������Ϣ����
	 * @throws Throwable
	 */
	protected IMessageObject interceptService(IMessageObject reqMo) throws Throwable {
		
		//1.���˷������������Ҫ�󣬽��׳��쳣
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
	 * �����쳣��Ϣ
	 * @param reqMo
	 * @param e
	 * @return
	 */
	protected IMessageObject processException(IMessageObject reqMo, Throwable e){
		
		//������Ӧ��Ϣ����
		IMessageObject respMo = null;
		
		//1.��ȡ������
		String errorCode = null;
		String errorDesc = null;
		if(e instanceof ServcieExecuterException){
			//��������쳣�д�����Ϣ����ʹ�ø���Ϣ����
			ServcieExecuterException se = (ServcieExecuterException)e;
			if(se.getMessageObject() != null){
				respMo = se.getMessageObject();
			}
				
		}else{
			
			//ʹ��ԭ���쳣����ô�����
			Throwable cause = ExceptionUtils.getRootCause(e);
			if(cause != null){
				e = cause;
			}
			errorCode = exceptionMessage.getErrorCode(e.getClass().getName());
			errorDesc = exceptionMessage.getErrorDesc(e);
		}
		
		//Ĭ��ʹ��δ֪�쳣������ʹ�������
		if(errorCode == null){
			errorCode = this.errorCode4unknowError;
			if(!StringUtils.isEmpty(this.errorDesc4unknowError)){
				errorDesc = this.errorDesc4unknowError;
			}
		}

		//3.����MO��Ӧ��Ϣ
		if(respMo == null){
			respMo = reqMo;
		}
		respMo = moTemplate.serviceFailedEnd(respMo, errorCode, errorDesc);

		//4.����MO
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
