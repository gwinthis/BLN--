package com.bln.framework.timertask.trigger;

import org.quartz.Trigger;

public interface ITaskTriggerBuilder<T extends Trigger> {

	/**
	 * ���촥����
	 * @return Trigger
	 */
	public abstract Trigger buildTrigger();

}