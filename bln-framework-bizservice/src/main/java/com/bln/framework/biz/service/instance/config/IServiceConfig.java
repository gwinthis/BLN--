package com.bln.framework.biz.service.instance.config;

import com.bln.framework.biz.service.IServiceDecorater;

public interface IServiceConfig {

	/**
	 * @return the isAsyncExecute
	 */
	public abstract boolean isAsyncExecute();

	/**
	 * @param isAsyncExecute the isAsyncExecute to set
	 */
	public abstract void setAsyncExecute(boolean isAsyncExecute);

	/**
	 * @return the serviceDecorater
	 */
	public abstract IServiceDecorater getServiceDecorater();

	/**
	 * @param serviceDecorater the serviceDecorater to set
	 */
	public abstract void setServiceDecorater(IServiceDecorater serviceDecorater);

}