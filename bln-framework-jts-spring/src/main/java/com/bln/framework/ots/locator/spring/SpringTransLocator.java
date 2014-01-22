/**
 * @author gengw
 * Created on Jun 20, 2012
 */
package com.bln.framework.ots.locator.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bln.framework.ots.locator.IOTSLocator;
import com.bln.framework.ots.locator.OTSLocatorBase;

/**
 * Spring������λ��
 */
public class SpringTransLocator extends OTSLocatorBase implements IOTSLocator{

	/**
	 * Spring�Ĺ�����
	 */
	protected ApplicationContext context = null;
	
	/**
	 *  Spring�����������ļ�·��
	 */
	protected String contextUrl = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.ots.locator.IOTSLocator#getService()
	 */
	@SuppressWarnings("unchecked")
	public <T> T getService() throws Throwable {
		checkSpringFactory();
		return (T)context.getBean(serviceKey);
	}
	
	/**
	 * У��Spring�����Ƿ��ʼ��
	 */
	protected void checkSpringFactory(){
		if(context == null){
			context = new ClassPathXmlApplicationContext(contextUrl);
		}
	}

	/**
	 * @return the contextUrl
	 */
	public String getContextUrl() {
		return contextUrl;
	}

	/**
	 * @param contextUrl the contextUrl to set
	 */
	public void setContextUrl(String contextUrl) {
		this.contextUrl = contextUrl;
	}

	/**
	 * @return the context
	 */
	public ApplicationContext getContext() {
		checkSpringFactory();
		return context;
	}
}
