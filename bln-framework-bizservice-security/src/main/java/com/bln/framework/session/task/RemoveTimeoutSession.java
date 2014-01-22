/**
 * @author gengw
 * Created on Jan 26, 2013
 */
package com.bln.framework.session.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bln.framework.session.ISessionMan;

/**
 * É¾³ý³¬Ê±Session
 */
public class RemoveTimeoutSession implements Job{

	
	/**
	 * 
	 */
	protected ISessionMan sessionMan = null;
	
	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		sessionMan.removeAllTimeoutSession();
	}

	/**
	 * @return the sessionMan
	 */
	public ISessionMan getSessionMan() {
		return sessionMan;
	}

	/**
	 * @param sessionMan the sessionMan to set
	 */
	public void setSessionMan(ISessionMan sessionMan) {
		this.sessionMan = sessionMan;
	}

}
