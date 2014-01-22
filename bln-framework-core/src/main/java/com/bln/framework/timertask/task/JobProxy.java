/**
 * @author gengw
 * Created on Nov 21, 2012
 */
package com.bln.framework.timertask.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 任务代理
 */
public class JobProxy implements Job{

	/**
	 * 实现类KEY
	 */
	public static final String JOB_IMPL_KEY = "job";
	
	/**
	 * 实际要执行的任务对象
	 */
	protected Job jobImpl = null;
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		if(jobImpl == null){
			jobImpl = (Job)context.getJobDetail().getJobDataMap().get(JOB_IMPL_KEY);
		}
		
		this.jobImpl.execute(context);
	}
}
