package com.bln.framework.biz.service.instance.config;

import com.bln.framework.biz.service.IServiceDecorater;

/**
 * ��������
 */
public class ServiceConfig implements IServiceConfig {
	
	/**
	 * �Ƿ��첽ִ�з���
	 */
	public boolean asyncExecute = false;
	
	/**
	 * ����װ����
	 */
	protected IServiceDecorater serviceDecorater = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.instance.IServiceConfig#isAsyncExecute()
	 */
	public boolean isAsyncExecute() {
		return asyncExecute;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.instance.IServiceConfig#setAsyncExecute(boolean)
	 */
	public void setAsyncExecute(boolean isAsyncExecute) {
		this.asyncExecute = isAsyncExecute;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.instance.IServiceConfig#getServiceDecorater()
	 */
	public IServiceDecorater getServiceDecorater() {
		return serviceDecorater;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.instance.IServiceConfig#setServiceDecorater(com.bln.framework.biz.service.IServiceDecorater)
	 */
	public void setServiceDecorater(IServiceDecorater serviceDecorater) {
		this.serviceDecorater = serviceDecorater;
	}



}
