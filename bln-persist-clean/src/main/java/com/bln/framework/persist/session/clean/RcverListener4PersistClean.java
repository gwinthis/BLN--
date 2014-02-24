/**
 * @author gengw
 * Created at 2013-08-04
 */
package com.bln.framework.persist.session.clean;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.edi.edge.rcver.listener.IRcverListener;
import com.bln.framework.persist.session.factory.ISessionFactory;

/**
 * 用于persist清理的接收者监听器
 */
public class RcverListener4PersistClean implements IRcverListener{

	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(RcverListener4PersistClean.class);
	
	/**
	 * 数据库会话工厂
	 */
	Map<String, Object>[] sessionFactories = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.rcver.listener.IRcverListener#startRequest(byte[], java.lang.Object)
	 */
	public void startRequest(byte[] reqEdiData, Object originalRequestObject) {
	
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.rcver.listener.IRcverListener#finishRequest(byte[], byte[], java.lang.Object)
	 */
	public void finishRequest(byte[] reqEdiData, byte[] respData, Object originalRequestObject) {
		if(sessionFactories != null && sessionFactories.length > 0){
			for (Map<String, Object> sessionFactoryMap : sessionFactories ){
				
				for (Map.Entry<String, Object> sessionFactoryEntry : sessionFactoryMap.entrySet()){
					
					if(sessionFactoryEntry.getValue() instanceof ISessionFactory){
						ISessionFactory sessionFactory = (ISessionFactory)sessionFactoryEntry.getValue();
						
						try {
							log.debug("close db persist session from " + sessionFactoryEntry.getKey() + "......");
							sessionFactory.closeCurrentAllSession();
						} catch (SQLException e) {
							log.error("close current session of sessionFactory error, sessionFactory " + sessionFactory, e);
						}
					}
				}
								
			}
		}
	}

	public Map<String, Object>[] getSessionFactories() {
		return sessionFactories;
	}

	public void setSessionFactories(Map<String, Object>[] sessionFactories) {
		this.sessionFactories = sessionFactories;
	}
	
}
