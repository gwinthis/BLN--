/**
 * @author gengw
 * Created on Apr 28, 2012
 */
package com.bln.framework.persist.session.builder.drviermanager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.builder.ISessionBuilder;
import com.bln.framework.persist.session.builder.SessionBuilderBase;
import com.bln.framework.persist.session.builder.exception.BuildSessionException;
import com.bln.framework.util.reflect.SeeWhoCall;

/**
 * ͨ��DriverManager����Session
 */
public class SessionBuilder4DriverManager extends SessionBuilderBase implements ISessionBuilder{
	
	private static final Log log = LogFactory.getLog(SessionBuilder4DriverManager.class);
	
	/**
	 * ������
	 */
	protected String driverClass = null;
	
	/**
	 * ���ݿ����ӵ�ַ
	 */
	protected String connectionUrl = null;
		
	/**
	 * ���ݿ���������
	 */
	protected Properties connectionProperty = null;
	
	/**
	 * ����Session
	 */
	public ISession build(){
		
		try {
			//1.��ȡConnection
			
			//1.1ע������
			Driver driver = (Driver) Class.forName(driverClass).newInstance();
			DriverManager.registerDriver(driver);
			
			//1.2��ȡ����
			Connection conn = this.buildConnection();
			
			//2.����Session
			ISession session = sessionTemplate.clone(conn);
			
			//3.����Session
			return session;
			
		} catch (Throwable e) {
			BuildSessionException bse = new BuildSessionException();
			bse.initCause(e);
			
			throw bse;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.builder.IConnectionBuilder#getConnection()
	 */
	public Connection buildConnection() throws SQLException{
		Connection conn = DriverManager.getConnection(connectionUrl, connectionProperty);
		if(log.isDebugEnabled()){
			SeeWhoCall.Caller[] callers = SeeWhoCall.getAllCallers();
			SeeWhoCall.Caller lastCaller = callers[0];
			if("SessionBuilder4DataSource.java".equals(lastCaller.getFileName())){
				lastCaller = callers[1]; 
			}
			log.debug(lastCaller + " get connection [" + conn + "] from url[" + connectionUrl +"]");
		}
		
		return conn;
	}
	
	/**
	 * @return the driverClass
	 */
	public String getDriverClass() {
		return driverClass;
	}

	/**
	 * @param driverClass the driverClass to set
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	/**
	 * @return the connectionUrl
	 */
	public String getConnectionUrl() {
		return connectionUrl;
	}

	/**
	 * @param connectionUrl the connectionUrl to set
	 */
	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	/**
	 * @return the connectionProperty
	 */
	public Properties getConnectionProperty() {
		return connectionProperty;
	}

	/**
	 * @param connectionProperty the connectionProperty to set
	 */
	public void setConnectionProperty(Properties connectionProperty) {
		this.connectionProperty = connectionProperty;
	}
}
