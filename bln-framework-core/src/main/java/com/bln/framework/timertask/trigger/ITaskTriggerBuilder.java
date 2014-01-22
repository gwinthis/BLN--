package com.bln.framework.timertask.trigger;

import org.quartz.Trigger;

public interface ITaskTriggerBuilder<T extends Trigger> {

	/**
	 * ¹¹Ôì´¥·¢Æ÷
	 * @return Trigger
	 */
	public abstract Trigger buildTrigger();

}