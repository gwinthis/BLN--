/**
 * @author gengw
 * Created on May 31, 2012
 */
package com.bln.framework.timertask.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.ScheduleBuilder;

/**
 * Cron���񴥷���
 */
public class CronTaskTriggerBuilder extends TriggerBuilderBase<CronTrigger>{
	
	/**
	 * cron���ʽ
	 */
	protected String cronExpression = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.timertask.TriggerBase#buildScheduleBuilder()
	 */
	@Override
	protected ScheduleBuilder<CronTrigger> buildScheduleBuilder() {

		CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(cronExpression);
		
		//����builder
		return builder;
	}

	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}


	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
}
