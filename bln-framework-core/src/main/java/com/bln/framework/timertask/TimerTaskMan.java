/**
 * @author gengw
 * Created on May 31, 2012
 */
package com.bln.framework.timertask;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.bln.framework.timertask.task.JobProxy;


/**
 * ��ʱ�������
 */
public class TimerTaskMan implements ITimerTaskMan {
	
	/**
	 * ��õ�ǰ�����Log����
	 */
	private final static Log log  = LogFactory.getLog(TimerTaskMan.class);
	
	/**
	 * ����Map
	 */
	protected Map<Job, Trigger> taskMap = new HashMap<Job, Trigger>();
	
	/**
	 * ����ƻ���
	 */
	protected Scheduler scheduler = null;
		
	/**
	 * ���캯��
	 * @throws SchedulerException 
	 */
	public TimerTaskMan(){
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			log.error("get default scheduler from StdSchedulerFactory error!", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.timertask.ITimerTaskMan#startAllTask()
	 */
	public void startAllTask() throws SchedulerException, ClassNotFoundException{
		
		if(taskMap == null || taskMap.isEmpty()){
			return;
		}
		
		//1.������������
		for ( Map.Entry<Job, Trigger> taskEntry : taskMap.entrySet()){
			this.scheduleTask(taskEntry.getKey(), taskEntry.getValue());
		}
		
		//2.��������
		scheduler.start();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.timertask.ITimerTaskMan#shutdownAllTask()
	 */
	public void shutdownAllTask() throws SchedulerException{
		scheduler.shutdown();
	}
	
	/**
	 * ��������
	 * @param jobClazz ������
	 * @param trigger ���񴥷���
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	protected void scheduleTask(Job job, Trigger trigger) throws SchedulerException, ClassNotFoundException{
		
		//1.�����������
		JobDetail jobDetail = JobBuilder.newJob(JobProxy.class).build();
		jobDetail.getJobDataMap().put(JobProxy.JOB_IMPL_KEY, job);
		
		//2.��������
		log.info("load timer task " + job.getClass().getSimpleName());
		scheduler.scheduleJob(jobDetail, trigger);
		
	}

	/**
	 * ��Ӷ�ʱ����
	 * @param jobClazz ������·��
	 * @param trigger ������
	 */
	public void addJob(Job job, Trigger trigger){
		this.taskMap.put(job, trigger);
	}
	
	/**
	 * ɾ����ʱ����
	 * @param jobClazz ������·��
	 */
	public void removeJob(String jobClazz){
		this.taskMap.remove(jobClazz);
	}

}
