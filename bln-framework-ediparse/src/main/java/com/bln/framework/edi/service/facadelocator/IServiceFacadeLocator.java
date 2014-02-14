/**
 * @author gengw
 * created at 2014-02-14
 */
package com.bln.framework.edi.service.facadelocator;

import com.bln.framework.biz.service.IServiceFacade;
import com.bln.framework.mo.IMessageObject;

/**
 * ������������λ��
 */
public interface IServiceFacadeLocator {

	/**
	 * ������������λ��
	 * @param reqMo
	 * @param originalRequestObject
	 * @return IServiceFacade
	 */
	public IServiceFacade locate(IMessageObject reqMo, Object originalRequestObject);

}