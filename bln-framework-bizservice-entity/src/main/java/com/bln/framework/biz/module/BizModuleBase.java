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
 * 业务模型基类
 */
public class BizModuleBase {
	
	/**
	 * 数据库Session工厂
	 */
	protected ISessionFactory sessionFactory = null;
	
	/**
	 * 获取数据库Session的策略
	 */
	protected ISessionStrategy sessionStrategy = null;
		
	/**
	 * 状态实体类库
	 */
	protected IEntityLib entityLib = null;

	/**
	 * 表结构库
	 */
	protected ITableStruLib tableStruLib = null;
	
	/**
	 * SQL实体工具
	 */
	protected ISqlEntityUtils sqlEntityUtils = null;
	
	/**
	 * db session定位关键字
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
	 * 获取条件为指定KEY的数据库Session
	 * @return ISession
	 */
	protected ISession getSession()throws SQLException{
		return this.getSession(this.defaultSessionKey);
	}
	
	/**
	 * 获取指定条件的数据库Session
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
