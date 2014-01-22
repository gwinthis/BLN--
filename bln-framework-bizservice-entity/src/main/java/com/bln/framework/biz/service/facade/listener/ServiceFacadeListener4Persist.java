/**
 * @author gengw
 * Create at 2013-07-15
 */
package com.bln.framework.biz.service.facade.listener;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.base.BaseObj;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.session.factory.ISessionFactory;

/**
 * ����������������ڳ־ò��session���
 */
public class ServiceFacadeListener4Persist extends BaseObj implements IServiceAfterEventListener{

	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(ServiceFacadeListener4Persist.class);
	
	/**
	 * ���ݿ�Ự����
	 */
	ISessionFactory[] sessionFactories = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.util.listener.IEvtListener1Param#fire(java.lang.Object)
	 */
	public void fire(IMessageObject respMo) {
		
		if ( sessionFactories != null && sessionFactories.length > 0){
			for ( ISessionFactory sessionFactory : sessionFactories){
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
