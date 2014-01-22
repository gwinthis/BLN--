/**
 * @author gengw
 * Created at 2014-01-08
 */
package com.bln.framework.persist.template;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.persist.jdbc.template.storeprocedure.IStoreProcedureParameters;
import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureResult;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.session.executor.material.condition.ConditionClause;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.session.factory.ISessionFactory;
import com.bln.framework.persist.sql.entity.common.CommonSqlEntity;
import com.bln.framework.persist.sql.entity.common.ICommonSqlEntity;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.persist.sql.entity.select.SelectSqlEntity;

/**
 * 持久层模板对象
 */
public class PersistTemplate implements IPersistTemplate {

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#queryFirstRowFromTable(java.lang.String, java.lang.String, java.lang.String, java.util.List, java.lang.String, com.bln.framework.persist.session.executor.material.pagination.IPagination)
	 */
	public IRow queryFirstRowFromTable(String tableName, String where, List<Object> params, String orderby, IPagination pagination) throws SQLException, IOException{
		ISession session = this.getSession(sessionKey);
		
		IConditionClause conClause = new ConditionClause(where, params);
		return session.getTableQueryExecutor(tableName).queryFirstRow(conClause, pagination, orderby);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#queryFromTable(java.lang.String, java.lang.String, java.lang.String, java.util.List, java.lang.String, com.bln.framework.persist.session.executor.material.pagination.IPagination)
	 */
	public List<IRow> queryFromTable(String tableName, String where, List<Object> params, String orderby, IPagination pagination) throws SQLException, IOException{
		ISession session = this.getSession(sessionKey);
		
		IConditionClause conClause = new ConditionClause(where, params);
		return session.getTableQueryExecutor(tableName).query(conClause, pagination, orderby);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#isExsistsFromTable(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public boolean isExsistsFromTable(String tableName, String where, List<Object> params) throws SQLException, IOException{
		ISession session = this.getSession(sessionKey);
		
		IConditionClause conClause = new ConditionClause(where, params);
		return session.getTableQueryExecutor(tableName).isExists(conClause);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#deleteFromTable(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void deleteFromTable(String tableName, String where) throws SQLException{
		ISession session = this.getSession(sessionKey);
		
		IConditionClause conClause = new ConditionClause(where);
		session.getTableUpdateExecutor(tableName).deleteByCondition(conClause);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#insertIntoTable(java.lang.String, java.lang.String, com.bln.framework.persist.row.IRow)
	 */
	public IRow insertIntoTable(String tableName, IRow row) throws SQLException{
		
		ISession session = this.getSession(sessionKey);
		return session.getTableUpdateExecutor(tableName).insert(row);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#updateFromTable(java.lang.String, java.lang.String, com.bln.framework.persist.row.IRow)
	 */
	public IRow updateFromTable(String tableName, IRow row) throws SQLException, PersistStaleEntityException{
		
		ISession session = this.getSession(sessionKey);
		return session.getTableUpdateExecutor(tableName).update(row);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#updateFromTableByCondition(java.lang.String, java.lang.String, com.bln.framework.persist.row.IRow, com.bln.framework.persist.session.executor.material.condition.IConditionClause)
	 */
	public int updateFromTableByCondition(String tableName, IRow row, IConditionClause conClause) throws SQLException, PersistStaleEntityException{
		
		ISession session = this.getSession(sessionKey);
		return session.getTableUpdateExecutor(tableName).updateByCondition(row, conClause);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#deleteFromTableByCondition(java.lang.String, java.lang.String, com.bln.framework.persist.session.executor.material.condition.IConditionClause)
	 */
	public int deleteFromTableByCondition(String tableName, IConditionClause conClause) throws SQLException, PersistStaleEntityException{
		
		ISession session = this.getSession(sessionKey);
		return session.getTableUpdateExecutor(tableName).deleteByCondition(conClause);
	}
	

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#queryFirstRow(java.lang.String, java.lang.String, java.util.List, com.bln.framework.persist.session.executor.material.pagination.IPagination)
	 */
	public IRow queryFirstRow(String sql, List<Object> params, IPagination pagination) throws SQLException, IOException{
		
		ISession session = this.getSession(sessionKey);
		
		ISelectSqlEntity selectSqlEntity = new SelectSqlEntity(sql, params, null);
		return session.getViewExecutor().queryFirstRow(selectSqlEntity, pagination);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#query(java.lang.String, java.lang.String, java.util.List, com.bln.framework.persist.session.executor.material.pagination.IPagination)
	 */
	public List<IRow> query(String sql, List<Object> params, IPagination pagination) throws SQLException, IOException{
		
		ISession session = this.getSession(sessionKey);
		
		ISelectSqlEntity selectSqlEntity = new SelectSqlEntity(sql, params, null);
		return session.getViewExecutor().query(selectSqlEntity, pagination);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#update(java.lang.String, java.lang.String, java.util.List)
	 */
	public int update(String sql, List<Object> params) throws SQLException, IOException{
		
		ISession session = this.getSession(sessionKey);
		
		ICommonSqlEntity sqlEntity = new CommonSqlEntity(sql, params, null);
		return session.getUpdateExecutor().execute(sqlEntity);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#executeStoreProcedure(java.lang.String, java.lang.String, com.bln.framework.persist.jdbc.template.storeprocedure.IStoreProcedureParameters)
	 */
	public StoreProcedureResult<IRow> executeStoreProcedure(String procedureName, IStoreProcedureParameters params) throws SQLException, IOException{
		ISession session = this.getSession(sessionKey);
		return session.getSpExecutor().execute(procedureName, params);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#beginTransaction(java.lang.String)
	 */
	public void beginTransaction() throws SQLException{
		this.getSession(sessionKey).beginTransaction();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#commit(java.lang.String)
	 */
	public void commit() throws SQLException{
		this.getSession(sessionKey).commit();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.template.IPersistTemplate#rollback(java.lang.String)
	 */
	public void rollback() throws SQLException{
		this.getSession(sessionKey).rollback();
	}
	
	public void closeCurrentAllSession() throws SQLException{
		ISessionFactory sessionFactory = (ISessionFactory)persistFactory.getInstance(sessionFactoryPath);
		sessionFactory.closeCurrentAllSession();
	}
	
	/**
	 * 获取当前session
	 * @param factoryName
	 * @param sessionKey
	 * @return
	 * @throws SQLException
	 */
	protected ISession getSession(String sessionKey) throws SQLException{
		
		ISessionFactory sessionFactory = (ISessionFactory)persistFactory.getInstance(sessionFactoryPath);
		ISession session = sessionFactory.getCurrentSession(sessionKey);
		
		return session;
	}
		
	/**
	 * 持久层工厂
	 */
	protected IBLNFactory persistFactory = null;

	/**
	 * path of session factory in the persist factory 
	 */
	protected String sessionFactoryPath = null;
	
	/**
	 * session key of session factory 
	 */
	protected String sessionKey = null;
	
	public IBLNFactory getPersistFactory() {
		return persistFactory;
	}

	public void setPersistFactory(IBLNFactory persistFactory) {
		this.persistFactory = persistFactory;
	}

	public String getSessionFactoryPath() {
		return sessionFactoryPath;
	}

	public void setSessionFactoryPath(String sessionFactoryPath) {
		this.sessionFactoryPath = sessionFactoryPath;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	public PersistTemplate() {
		super();
	}
	
	public PersistTemplate(IBLNFactory persistFactory,
			String sessionFactoryPath, String sessionKey) {
		super();
		this.persistFactory = persistFactory;
		this.sessionFactoryPath = sessionFactoryPath;
		this.sessionKey = sessionKey;
	}




}
