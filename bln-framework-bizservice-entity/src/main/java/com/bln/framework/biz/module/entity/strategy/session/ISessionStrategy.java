package com.bln.framework.biz.module.entity.strategy.session;


import java.sql.SQLException;

import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.factory.ISessionFactory;

public interface ISessionStrategy{

	/**
	 * ���Session
	 * @param sessionFactory session����
	 * @param key ���Session�Ĳ���
	 * @return Session����
	 * @throws SQLException 
	 */
	public ISession getSession(ISessionFactory sessionFactory, Object key) throws SQLException;

	/**
	 * �����ݱ�����ȡSession
	 * @param sessionFactory
	 * @return
	 * @throws SQLException
	 */
	public ISession getSession(ISessionFactory sessionFactory) throws SQLException;

}