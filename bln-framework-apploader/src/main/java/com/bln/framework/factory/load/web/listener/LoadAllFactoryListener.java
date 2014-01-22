/**
 *  @author gengw
 *  Created at 201308-06
 */
package com.bln.framework.factory.load.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.factory.ioc.center.BLNFactoryCenter;

/**
 * �������й���
 */
public class LoadAllFactoryListener implements ServletContextListener{

	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(LoadAllFactoryListener.class);

	
	public void contextDestroyed(ServletContextEvent arg0) {}

	public void contextInitialized(ServletContextEvent event) {

		log.debug("bln framework load all factory......");
		BLNFactoryCenter.singleton().loadAllFactory();
		log.debug("bln framework load all factory finish ......");
	}

}
