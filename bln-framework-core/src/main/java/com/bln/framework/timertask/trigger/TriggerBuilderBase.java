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
 * ����������
 */
public abstract class TriggerBuilderBase<T extends Trigger> implements ITaskTriggerBuilder<T> {
	
	/**
	 * �Ƿ���������
	 */
	boolean startNow = true;
	
	/**
	 * ��ʼʱ��
	 */
	protected Date startDate = null;
		
	/* (non-Javadoc)
	 * @see com.bln.framework.timertask.trigger.ITaskTriggerBuilder#buildTrigger()
	 */
	public Trigger buildTrigger(){
		
		//1.����滮������
		ScheduleBuilder<T> scheduleBuilder = buildScheduleBuilder();
		
		//2.���촥����
		
		//��ʼ������
		TriggerBuilder<T> triggerBuilder = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder);
		if(startNow){
			triggerBuilder.startNow();
		}else{
			triggerBuilder.startAt(startDate);
		}
		
		//���촥����
		Trigger trigger = triggerBuilder.build();
		
		//3.���ش�����
		return trigger;
	}
	
	/**
	 * ����滮������
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
