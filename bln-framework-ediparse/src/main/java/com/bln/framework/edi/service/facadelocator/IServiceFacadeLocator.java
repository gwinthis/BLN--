/**
 * @author gengw
 * created at 2014-02-14
 */
package com.bln.framework.edi.service.facadelocator;

import com.bln.framework.biz.service.IServiceFacade;
import com.bln.framework.mo.IMessageObject;

/**
 * 服务层门面对象定位器
 */
public interface IServiceFacadeLocator {

	/**
	 * 服务层门面对象定位器
	 * @param reqMo
	 * @param originalRequestObject
	 * @return IServiceFacade
	 */
	public IServiceFacade locate(IMessageObject reqMo, Object originalRequestObject);

}