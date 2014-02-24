/**
 * @author gengw
 * created at 2014-02-14
 */
package com.bln.framework.edi.service.facadelocator.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bln.framework.base.cacheable.BaseObjCacheable;
import com.bln.framework.biz.service.IServiceFacade;
import com.bln.framework.edi.service.facadelocator.IServiceFacadeLocator;
import com.bln.framework.edi.service.facadelocator.exception.CantLocateServiceFactoryException;
import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.center.BLNFactoryCenter;
import com.bln.framework.mo.IMessageObject;

/**
 * ����URLӳ��ķ����������λ��
 */
public class ServiceFacadeLocatorUsingURL extends BaseObjCacheable implements IServiceFacadeLocator{

	/**
	 * URL�ͷ�����������ӳ��
	 * @return
	 */
	protected Map<String, String> urlToServiceFactory = null;
	
	/**
	 * �������Ĺ���·��
	 */
	protected String serviceFacadePath = "bizservice.facade.Default";
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.service.facadelocator.IServiceFacadeLocator#locate(com.bln.framework.mo.IMessageObject, java.lang.Object)
	 */
	public IServiceFacade locate(IMessageObject reqMo, Object originalRequestObject) {
		
		HttpServletRequest request = (HttpServletRequest)originalRequestObject;
		String url = request.getPathInfo();
		
		IServiceFacade serviceFacade = null;
		if(this.containsKey(url)){
			serviceFacade = (IServiceFacade)this.getFromCache(url);
		}else{
			String factoryName = urlToServiceFactory.get(url);
			if(factoryName == null){
				CantLocateServiceFactoryException clsfe = new CantLocateServiceFactoryException("can't found serviceFacade in urlToFacade, please define the url mapping!");
				
				clsfe.addContextValue("urlToFacade", urlToServiceFactory);
				clsfe.addContextValue("requestUrl", url);
				
				throw clsfe;
			}
			
			serviceFacade = this.getServiceFacadeFromFactory(factoryName);
			this.putToCache(url, serviceFacade);
		}
		
		return serviceFacade;
	}
	
	/**
	 * �ӹ����л�ȡserviceFacade����
	 * @param factoryName ��������
	 * @return
	 */
	protected IServiceFacade getServiceFacadeFromFactory(String factoryName){
		
		IBLNFactory blnFactory = BLNFactoryCenter.singleton().getFactory(factoryName);
		return (IServiceFacade)blnFactory.getInstance(serviceFacadePath);
	}

	public Map<String, String> getUrlToServiceFactory() {
		return urlToServiceFactory;
	}

	public void setUrlToServiceFactory(Map<String, String> urlToServiceFactory) {
		this.urlToServiceFactory = urlToServiceFactory;
	}

	public String getServiceFacadePath() {
		return serviceFacadePath;
	}

	public void setServiceFacadePath(String serviceFacadePath) {
		this.serviceFacadePath = serviceFacadePath;
	}
}
