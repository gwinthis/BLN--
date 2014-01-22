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
 * Spring的事务定位器
 */
public class SpringTransLocator extends OTSLocatorBase implements IOTSLocator{

	/**
	 * Spring的工厂类
	 */
	protected ApplicationContext context = null;
	
	/**
	 *  Spring工厂的配置文件路径
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
	 * 校验Spring工厂是否初始化
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
