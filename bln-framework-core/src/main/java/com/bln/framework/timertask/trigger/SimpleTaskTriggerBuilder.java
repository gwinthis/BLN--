/**
 * @author gengw
 * Created on May 31, 2012
 */
package com.bln.framework.timertask.trigger;

import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.DateBuilder.IntervalUnit;

import com.bln.framework.exception.UnsupportedException;

/**
 * �����񴥷���
 */
public class SimpleTaskTriggerBuilder extends TriggerBuilderBase<SimpleTrigger>{
	
	/**
	 * ʱ����
	 */
	protected int interval = 0;
	
	/**
	 * �����λ
	 */
	protected String intervalUnit = null;
	
	/**
	 * ���ô���
	 */
	protected int repeatCount = -1;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.timertask.TriggerBase#buildScheduleBuilder()
	 */
	@Override
	protected ScheduleBuilder<SimpleTrigger> buildScheduleBuilder() {
		
		SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule();
		
		//���ʱ��
		IntervalUnit unit = IntervalUnit.valueOf(this.intervalUnit);
		if(IntervalUnit.MILLISECOND.equals(unit)){
			builder.withIntervalInMilliseconds(interval);
		}else if(IntervalUnit.SECOND.equals(unit)){
			builder.withIntervalInSeconds(interval);
		}else if(IntervalUnit.MINUTE.equals(unit)){
			builder.withIntervalInMinutes(interval);
		}else if(IntervalUnit.HOUR.equals(unit)){
			builder.withIntervalInHours(interval);
		}else{
			throw new UnsupportedException(" it does not support intervalUnit " + intervalUnit);
		}
		
		//���ô���
		if(repeatCount == -1){
			builder.repeatForever();
		}else{
			builder.withRepeatCount(repeatCount);
		}
		
		//����builder
		return builder;
	}

	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/**
	 * @return the repeatCount
	 */
	public int getRepeatCount() {
		return repeatCount;
	}

	/**
	 * @param repeatCount the repeatCount to set
	 */
	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	/**
	 * @return the intervalUnit
	 */
	public String getIntervalUnit() {
		return intervalUnit;
	}

	/**
	 * @param intervalUnit the intervalUnit to set
	 */
	public void setIntervalUnit(String intervalUnit) {
		this.intervalUnit = intervalUnit;
	}
	
}
