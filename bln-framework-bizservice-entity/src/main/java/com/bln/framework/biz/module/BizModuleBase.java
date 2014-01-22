/**
 * @author gengw
 * Created on Jun 15, 2012
 */
package com.bln.framework.biz.module;

import java.sql.SQLException;

import com.bln.framework.biz.module.entity.lib.IEntityLib;
import com.bln.framework.biz.module.entity.strategy.session.ISessionStrategy;

import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.factory.ISessionFactory;
import com.bln.framework.persist.sql.entity.util.ISqlEntityUtils;
import com.bln.framework.persist.tablestru.lib.ITableStruLib;

/**
 * ҵ��ģ�ͻ���
 */
public class BizModuleBase {
	
	/**
	 * ���ݿ�Session����
	 */
	protected ISessionFactory sessionFactory = null;
	
	/**
	 * ��ȡ���ݿ�Session�Ĳ���
	 */
	protected ISessionStrategy sessionStrategy = null;
		
	/**
	 * ״̬ʵ�����
	 */
	protected IEntityLib entityLib = null;

	/**
	 * ��ṹ��
	 */
	protected ITableStruLib tableStruLib = null;
	
	/**
	 * SQLʵ�幤��
	 */
	protected ISqlEntityUtils sqlEntityUtils = null;
	
	/**
	 * db session��λ�ؼ���
	 */
	protected String defaultSessionKey = null;


	/**
	 * @return the sessionFactory
	 */
	public ISessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(ISessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @return the sessionStrategy
	 */
	public ISessionStrategy getSessionStrategy() {
		return sessionStrategy;
	}

	/**
	 * @param sessionStrategy the sessionStrategy to set
	 */
	public void setSessionStrategy(ISessionStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	/**
	 * @return the entityLib
	 */
	public IEntityLib getEntityLib() {
		return entityLib;
	}

	/**
	 * @param entityLib the entityLib to set
	 */
	public void setEntityLib(IEntityLib entityLib) {
		this.entityLib = entityLib;
	}

	/**
	 * @return the tableStruLib
	 */
	public ITableStruLib getTableStruLib() {
		return tableStruLib;
	}

	/**
	 * @param tableStruLib the tableStruLib to set
	 */
	public void setTableStruLib(ITableStruLib tableStruLib) {
		this.tableStruLib = tableStruLib;
	}

	/**
	 * @return the sqlEntityUtils
	 */
	public ISqlEntityUtils getSqlEntityUtils() {
		return sqlEntityUtils;
	}

	/**
	 * @param sqlEntityUtils the sqlEntityUtils to set
	 */
	public void setSqlEntityUtils(ISqlEntityUtils sqlEntityUtils) {
		this.sqlEntityUtils = sqlEntityUtils;
	}
	
	/**
	 * ��ȡ����Ϊָ��KEY�����ݿ�Session
	 * @return ISession
	 */
	protected ISession getSession()throws SQLException{
		return this.getSession(this.defaultSessionKey);
	}
	
	/**
	 * ��ȡָ�����������ݿ�Session
	 * @return ISession
	 */
	protected ISession getSession(String key)throws SQLException{
		return this.sessionStrategy.getSession(sessionFactory, key);
	}

	/**
	 * @return the defaultSessionKey
	 */
	public String getDefaultSessionKey() {
		return defaultSessionKey;
	}

	/**
	 * @param defaultSessionKey the defaultSessionKey to set
	 */
	public void setDefaultSessionKey(String defaultSessionKey) {
		this.defaultSessionKey = defaultSessionKey;
	}
}
