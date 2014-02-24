/**
 * @author gengw
 * created at 2014-02-14
 */
package com.bln.framework.edi.service.facadelocator.impl;

import com.bln.framework.biz.service.IServiceFacade;
import com.bln.framework.edi.service.facadelocator.IServiceFacadeLocator;
import com.bln.framework.edi.service.facadelocator.exception.CantLocateServiceFactoryException;
import com.bln.framework.mo.IMessageObject;

/**
 * ����㶨λ��
 */
public class ServiceFacadeLocatorSingle implements IServiceFacadeLocator {
	
	/**
	 * ҵ����������
	 */
	IServiceFacade serviceFacade = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.service.facadelocator.IServiceFacadeLocator#locate(com.bln.framework.mo.IMessageObject, java.lang.Object)
	 */
	public IServiceFacade locate(IMessageObject reqMo, Object originalRequestObject){
		if(serviceFacade == null){
			CantLocateServiceFactoryException clsfe = new CantLocateServiceFactoryException("property serviceFacade of ServiceFacadeLocatorSingle is null, please define the property!");
			throw clsfe;
		}
		return serviceFacade;
	}

	public IServiceFacade getServiceFacade() {
		return serviceFacade;
	}

	public void setServiceFacade(IServiceFacade serviceFacade) {
		this.serviceFacade = serviceFacade;
	}

}
