package com.bln.framework.persistutil.task.executor;

import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.persistutil.task.ITaskConfig;

public interface ITaskExecutor {

	//protected static Map<String, IExecuteBuilder>
	/**
	 * Ö´ÐÐÈÎÎñ
	 * @param taskConfig
	 * @throws Throwable 
	 */
	public abstract void execute(ITaskConfig taskConfig) throws Throwable;

	/**
	 * @return the persistFactory
	 */
	public abstract IBLNFactory getPersistFactory();

	/**
	 * @param persistFactory the persistFactory to set
	 */
	public abstract void setPersistFactory(IBLNFactory persistFactory);

}