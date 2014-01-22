/**
 * @author gengw
 * Created on May 9, 2012
 */
package com.bln.framework.biz.module.entity.strategy.session;

import java.sql.SQLException;

import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.factory.ISessionFactory;

/**
 * 获取当前线程下的session的策略基类
 */
public class SessionStrategy implements ISessionStrategy {

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.strategy.session.SessionStrategyBase#getSession(com.bln.framework.persist.session.factory.ISessionFactory, java.lang.String)
	 */
	public final ISession getSession(ISessionFactory sessionFactory, Object key) throws SQLException {
		
		ISession session = sessionFactory.getCurrentSession(key);
		return session;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.strategy.session.ISessionStrategy#getSession(com.bln.framework.persist.session.factory.ISessionFactory)
	 */
	public ISession getSession(ISessionFactory sessionFactory) throws SQLException {
		return this.getSession(sessionFactory, null);
	}
	
}
