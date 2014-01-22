package com.bln.framework.timertask;

import org.quartz.Job;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public interface ITimerTaskMan {

	/**
	 * ������������
	 * @throws ClassNotFoundException 
	 * @throws SchedulerException 
	 * @throws SchedulerException 
	 */
	public abstract void startAllTask() throws SchedulerException,
			ClassNotFoundException;

	/**
	 * �ر���������
	 * @throws SchedulerException
	 */
	public abstract void shutdownAllTask() throws SchedulerException;

	/**
	 * �Ƴ�����
	 * @param jobClazz
	 */
	public abstract void removeJob(String jobClazz);

	/**
	 * �������
	 * @param job
	 * @param trigger
	 */
	public abstract void addJob(Job job, Trigger trigger);

}