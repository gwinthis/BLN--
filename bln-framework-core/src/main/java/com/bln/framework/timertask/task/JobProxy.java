/**
 * @author gengw
 * Created on Nov 21, 2012
 */
package com.bln.framework.timertask.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * �������
 */
public class JobProxy implements Job{

	/**
	 * ʵ����KEY
	 */
	public static final String JOB_IMPL_KEY = "job";
	
	/**
	 * ʵ��Ҫִ�е��������
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
