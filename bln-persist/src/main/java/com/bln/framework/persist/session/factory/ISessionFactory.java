package com.bln.framework.persist.session.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.bln.framework.persist.session.ISession;

public interface ISessionFactory {

	/**
	 * 根据SessionKey获取Session对象
	 * @param sessionKey
	 * @return
	 */
	public abstract ISession openSession(Object sessionKey);

	/**
	 *  根据Connection生成Session
	 * @param conn 连接原型
	 * @return 新生成的Session
	 */
	public abstract ISession openSession(Connection connection);

	/**
	 * 获得当前线程下sessionKey所对应的Session
	 * @param sessionKey session关键字
	 * @return 当前线程的Session
	 * @throws SQLException 
	 */
	public abstract ISession getCurrentSession(Object sessionKey) throws SQLException;

	/**
	 * 关闭当前线程下的所有Session
	 * @throws SQLException
	 */
	public void closeCurrentAllSession() throws SQLException;

	/**
	 * 获得当前线程下的所有Session
	 * @return 当前线程下的所有Session
	 */
	public List<ISession> getCurrentAllSession();

	/**
	 * 开启条件为空的Session
	 * @return ISession
	 */
	public ISession openSession();

	/**
	 * 获取当前线程下的Session，条件为空的Session
	 * @return 当前线程下的Session
	 * @throws SQLException
	 */
	public ISession getCurrentSession() throws SQLException;

}