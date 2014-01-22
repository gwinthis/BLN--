package com.bln.framework.biz.module.entity.strategy.session;


import java.sql.SQLException;

import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.factory.ISessionFactory;

public interface ISessionStrategy{

	/**
	 * 获得Session
	 * @param sessionFactory session工厂
	 * @param key 获得Session的参数
	 * @return Session对象
	 * @throws SQLException 
	 */
	public ISession getSession(ISessionFactory sessionFactory, Object key) throws SQLException;

	/**
	 * 不根据表名获取Session
	 * @param sessionFactory
	 * @return
	 * @throws SQLException
	 */
	public ISession getSession(ISessionFactory sessionFactory) throws SQLException;

}