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
 * 定时任务管理
 */
public class TimerTaskMan implements ITimerTaskMan {
	
	/**
	 * 获得当前对象的Log对象
	 */
	private final static Log log  = LogFactory.getLog(TimerTaskMan.class);
	
	/**
	 * 任务Map
	 */
	protected Map<Job, Trigger> taskMap = new HashMap<Job, Trigger>();
	
	/**
	 * 任务计划器
	 */
	protected Scheduler scheduler = null;
		
	/**
	 * 构造函数
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
		
		//1.安排所有任务
		for ( Map.Entry<Job, Trigger> taskEntry : taskMap.entrySet()){
			this.scheduleTask(taskEntry.getKey(), taskEntry.getValue());
		}
		
		//2.启动任务
		scheduler.start();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.timertask.ITimerTaskMan#shutdownAllTask()
	 */
	public void shutdownAllTask() throws SchedulerException{
		scheduler.shutdown();
	}
	
	/**
	 * 安排任务
	 * @param jobClazz 任务类
	 * @param trigger 任务触发器
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	protected void scheduleTask(Job job, Trigger trigger) throws SchedulerException, ClassNotFoundException{
		
		//1.建立任务对象
		JobDetail jobDetail = JobBuilder.newJob(JobProxy.class).build();
		jobDetail.getJobDataMap().put(JobProxy.JOB_IMPL_KEY, job);
		
		//2.安排任务
		log.info("load timer task " + job.getClass().getSimpleName());
		scheduler.scheduleJob(jobDetail, trigger);
		
	}

	/**
	 * 添加定时任务
	 * @param jobClazz 任务类路径
	 * @param trigger 触发器
	 */
	public void addJob(Job job, Trigger trigger){
		this.taskMap.put(job, trigger);
	}
	
	/**
	 * 删除定时任务
	 * @param jobClazz 任务类路径
	 */
	public void removeJob(String jobClazz){
		this.taskMap.remove(jobClazz);
	}

}
