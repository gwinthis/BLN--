/**
 * @author gengw
 * Created at 2013-08-04
 */
package com.bln.framework.persist.session.clean;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.edi.edge.rcver.listener.IRcverListener;
import com.bln.framework.persist.session.factory.ISessionFactory;

/**
 * ����persist����Ľ����߼�����
 */
public class RcverListener4PersistClean implements IRcverListener{

	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(RcverListener4PersistClean.class);
	
	/**
	 * ���ݿ�Ự����
	 */
	ISessionFactory[] sessionFactories = null;

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
			for (ISessionFactory sessionFactory : sessionFactories ){
				try {
					sessionFactory.closeCurrentAllSession();
				} catch (SQLException e) {
					log.error("close current session of sessionFactory error, sessionFactory " + sessionFactory, e);
				}				
			}
		}
	}
	

	/**
	 * @return the sessionFactories
	 */
	public ISessionFactory[] getSessionFactories() {
		return sessionFactories;
	}

	/**
	 * @param sessionFactories the sessionFactories to set
	 */
	public void setSessionFactories(ISessionFactory[] sessionFactories) {
		this.sessionFactories = sessionFactories;
	}
}
