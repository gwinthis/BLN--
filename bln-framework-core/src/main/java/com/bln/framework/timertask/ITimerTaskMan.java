package com.bln.framework.timertask;

import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public interface ITimerTaskMan {

	/**
	 * 启动所有任务
	 * @throws ClassNotFoundException 
	 * @throws SchedulerException 
	 * @throws SchedulerException 
	 */
	public abstract void startAllTask() throws SchedulerException,
			ClassNotFoundException;

	/**
	 * 关闭所有任务
	 * @throws SchedulerException
	 */
	public abstract void shutdownAllTask() throws SchedulerException;

	/**
	 * 移出任务
	 * @param jobClazz
	 */
	public abstract void removeJob(String jobClazz);

	/**
	 * 添加任务
	 * @param job
	 * @param trigger
	 */
	public abstract void addJob(Job job, Trigger trigger);

}