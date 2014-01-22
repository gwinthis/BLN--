/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.biz.service.decorater;

import com.bln.framework.biz.service.IService;
import com.bln.framework.biz.service.IServiceDecorater;

/**
 * ����װ��������
 */
public abstract class ServiceDecoraterBase implements IServiceDecorater{
	
	/**
	 * �������
	 */
	public IService service = null;

	/**
	 * ��÷������
	 * @return the service
	 */
	public IService getService() {
		return service;
	}

	/**
	 * ���÷������
	 * @param service the service to set
	 */
	public void setService(IService service) {
		this.service = service;
	}
}
