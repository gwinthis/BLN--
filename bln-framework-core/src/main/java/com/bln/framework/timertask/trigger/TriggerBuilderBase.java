/**
 * @author gengw
 * Created on May 31, 2012
 */
package com.bln.framework.timertask.trigger;

import java.util.Date;

import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * 触发器基类
 */
public abstract class TriggerBuilderBase<T extends Trigger> implements ITaskTriggerBuilder<T> {
	
	/**
	 * 是否立即启动
	 */
	boolean startNow = true;
	
	/**
	 * 开始时间
	 */
	protected Date startDate = null;
		
	/* (non-Javadoc)
	 * @see com.bln.framework.timertask.trigger.ITaskTriggerBuilder#buildTrigger()
	 */
	public Trigger buildTrigger(){
		
		//1.构造规划生成器
		ScheduleBuilder<T> scheduleBuilder = buildScheduleBuilder();
		
		//2.构造触发器
		
		//初始构造器
		TriggerBuilder<T> triggerBuilder = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder);
		if(startNow){
			triggerBuilder.startNow();
		}else{
			triggerBuilder.startAt(startDate);
		}
		
		//构造触发器
		Trigger trigger = triggerBuilder.build();
		
		//3.返回触发器
		return trigger;
	}
	
	/**
	 * 构造规划生成器
	 * @return
	 */
	protected abstract ScheduleBuilder<T> buildScheduleBuilder();

	/**
	 * @return the startNow
	 */
	public boolean isStartNow() {
		return startNow;
	}

	/**
	 * @param startNow the startNow to set
	 */
	public void setStartNow(boolean startNow) {
		this.startNow = startNow;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
