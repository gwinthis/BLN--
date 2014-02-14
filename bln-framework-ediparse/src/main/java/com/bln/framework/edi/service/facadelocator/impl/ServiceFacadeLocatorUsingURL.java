/**
 * @author gengw
 * created at 2014-02-14
 */
package com.bln.framework.edi.service.facadelocator.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bln.framework.biz.service.IServiceFacade;
import com.bln.framework.edi.service.facadelocator.IServiceFacadeLocator;
import com.bln.framework.edi.service.facadelocator.exception.CantLocateServiceFacadeException;
import com.bln.framework.mo.IMessageObject;

/**
 * 根据URL映射的服务门面对象定位器
 */
public class ServiceFacadeLocatorUsingURL implements IServiceFacadeLocator{

	/**
	 * URL和服务门面对象的映射
	 * @return
	 */
	protected Map<String, IServiceFacade> urlToServiceFacade = null;
	
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.service.facadelocator.IServiceFacadeLocator#locate(com.bln.framework.mo.IMessageObject, java.lang.Object)
	 */
	public IServiceFacade locate(IMessageObject reqMo, Object originalRequestObject) {
		
		HttpServletRequest request = (HttpServletRequest)originalRequestObject;
		String url = request.getPathInfo();
		
		IServiceFacade serviceFacade = urlToServiceFacade.get(url);
		if(serviceFacade == null){
			CantLocateServiceFacadeException clsfe = new CantLocateServiceFacadeException("can't found serviceFacade in urlToFacade, please define the url mapping!");
			
			clsfe.addContextValue("urlToFacade", urlToServiceFacade);
			clsfe.addContextValue("requestUrl", url);
			
			throw clsfe;
		}
		
		return serviceFacade;
	}


	public Map<String, IServiceFacade> getUrlToServiceFacade() {
		return urlToServiceFacade;
	}


	public void setUrlToServiceFacade(Map<String, IServiceFacade> urlToServiceFacade) {
		this.urlToServiceFacade = urlToServiceFacade;
	}
}
