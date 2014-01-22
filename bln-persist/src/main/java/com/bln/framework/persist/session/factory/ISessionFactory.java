package com.bln.framework.persist.session.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.bln.framework.persist.session.ISession;

public interface ISessionFactory {

	/**
	 * ����SessionKey��ȡSession����
	 * @param sessionKey
	 * @return
	 */
	public abstract ISession openSession(Object sessionKey);

	/**
	 *  ����Connection����Session
	 * @param conn ����ԭ��
	 * @return �����ɵ�Session
	 */
	public abstract ISession openSession(Connection connection);

	/**
	 * ��õ�ǰ�߳���sessionKey����Ӧ��Session
	 * @param sessionKey session�ؼ���
	 * @return ��ǰ�̵߳�Session
	 * @throws SQLException 
	 */
	public abstract ISession getCurrentSession(Object sessionKey) throws SQLException;

	/**
	 * �رյ�ǰ�߳��µ�����Session
	 * @throws SQLException
	 */
	public void closeCurrentAllSession() throws SQLException;

	/**
	 * ��õ�ǰ�߳��µ�����Session
	 * @return ��ǰ�߳��µ�����Session
	 */
	public List<ISession> getCurrentAllSession();

	/**
	 * ��������Ϊ�յ�Session
	 * @return ISession
	 */
	public ISession openSession();

	/**
	 * ��ȡ��ǰ�߳��µ�Session������Ϊ�յ�Session
	 * @return ��ǰ�߳��µ�Session
	 * @throws SQLException
	 */
	public ISession getCurrentSession() throws SQLException;

}