package com.bln.framework.app.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.app.context.AppContext;

public class AppRequestListener implements ServletRequestListener{
	
	private static final Log log = LogFactory.getLog(AppRequestListener.class);
	
	public void requestDestroyed(ServletRequestEvent arg0) {
		log.debug("appContext clear start....");
		AppContext.singleton().clearCurrentThreadValues();
		log.debug("appContext clear finish....");
		
	}

	public void requestInitialized(ServletRequestEvent requestEvent) {}

}
