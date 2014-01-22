/**
 * @author gengw
 * Created on Apr 28, 2012
 */
package com.bln.framework.persist.session.builder.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.jndi.IContextPrototype;
import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.builder.ISessionBuilder;
import com.bln.framework.persist.session.builder.SessionBuilderBase;
import com.bln.framework.persist.session.builder.exception.BuildSessionException;
import com.bln.framework.util.reflect.SeeWhoCall;

/**
 * 通过DataSource生成Session
 */
public class SessionBuilder4DataSource extends SessionBuilderBase implements ISessionBuilder{
	
	private static final Log log = LogFactory.getLog(SessionBuilder4DataSource.class);
	
	/**
	 * 上下文环境原型
	 */
	protected IContextPrototype contextPrototype = null;
	
	/**
	 * 数据源名称
	 */
	protected String dataSourceName = null;
	
	/**
	 * 数据源
	 */
	protected DataSource ds = null;
		
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.builder.datasource.ISessionBuilder#build()
	 */
	public ISession build(){
		
		try {
			
			//1.获取Connection
			
			//1.1检验DataSource
			//checkDataSource();
		
			//1.2获取Connection
			Connection conn = this.buildConnection();
			
			//2.生成Session
			ISession session = sessionTemplate.clone(conn);
			
			//3.返回Session
			return session;
			
		} catch (Throwable e) {
			
			BuildSessionException bse = new BuildSessionException("build session failed, from datasorce " + this.dataSourceName);
			bse.initCause(e);
			
			throw bse;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.builder.IConnectionBuilder#getConnection()
	 */
	public Connection buildConnection() throws SQLException {
		
		try {
			checkDataSource();
		} catch (NamingException e) {
			BuildSessionException bse = new BuildSessionException();
			bse.initCause(e);
			
			throw bse;		
		}
		
		
		Connection conn = ds.getConnection();
		
		if(log.isDebugEnabled()){
			SeeWhoCall.Caller[] callers = SeeWhoCall.getAllCallers();
			SeeWhoCall.Caller lastCaller = callers[0];
			if("SessionBuilder4DataSource.java".equals(lastCaller.getFileName())){
				lastCaller = callers[1]; 
			}
			log.debug(lastCaller + " get connection [" + conn + "] from datasource[" + ds +"]");
		}
		
		return conn;
	}
	
	/**
	 * 校验数据源
	 * @throws NamingException 
	 */
	protected void checkDataSource() throws NamingException{
		
		if(ds == null){
			Context ctx = contextPrototype.getInstance();
			ds = (DataSource)ctx.lookup(dataSourceName);
		}
	}

	/**
	 * @return the contextPrototype
	 */
	public IContextPrototype getContextPrototype() {
		return contextPrototype;
	}

	/**
	 * @param contextPrototype the contextPrototype to set
	 */
	public void setContextPrototype(IContextPrototype contextPrototype) {
		this.contextPrototype = contextPrototype;
	}

	/**
	 * @return the dataSourceName
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}

	/**
	 * @param dataSourceName the dataSourceName to set
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	
//	public String toString(){
//		return "SessionBuilder4DataSource" + this.hashCode();
//	}
}
