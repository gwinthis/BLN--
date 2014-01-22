/**
 * @author gengw
 * Created on Jan 23, 2013
 */
package com.bln.framework.session.store.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.factory.ioc.IBLNFactory;
import com.bln.framework.factory.ioc.listener.IObjectListener;
import com.bln.framework.persist.jdbc.wrapper.ConnectionLogWrapper;
import com.bln.framework.persist.session.builder.IConnectionBuilder;
import com.bln.framework.session.ISessionInfo;
import com.bln.framework.session.exception.NewSessionException;
import com.bln.framework.session.info.SessionInfo;
import com.bln.framework.session.store.ISessionStore;

/**
 * 数据库存储
 */
public class DBStore implements ISessionStore, IObjectListener {
	
	private static final Log log = LogFactory.getLog(DBStore.class);
	
	/**
	 * 连接构造器
	 */
	protected IConnectionBuilder connectionBuilder = null;
	
	/**
	 * 表名
	 */
	protected String tableName = "T_FW_SESSIONINFO";
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.listener.IObjectListener#actived(com.bln.framework.factory.ioc.IBLNFactory)
	 */
	public void actived(IBLNFactory factory) {
		
	}

	/**
	 * 获得DBConnection
	 * @param conn
	 * @return
	 * @throws SQLException 
	 */
	protected Connection getDBConnection() throws SQLException{

		Connection conn = new ConnectionLogWrapper(connectionBuilder.buildConnection());
		return conn;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.ioc.listener.IObjectListener#afterLoad(com.bln.framework.factory.ioc.IBLNFactory)
	 */
	public void afterLoad(IBLNFactory factory) {

		Connection conn = null;
		try {
			
			conn = getDBConnection();
			SessionDao sessionDao = new SessionDao(conn, this.tableName);
			
			sessionDao.buildTable();
			
		} catch (Throwable e) {
			
			log.info("table is exsists or buildTable error, exception: " + e.toString());

		}finally{
			try {
				if(conn != null){
					conn.close();
				}				
			} catch (SQLException e) {
				NewSessionException nse = new NewSessionException();
				nse.initCause(e);
				
				throw nse;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.store.ISessionStore#put(java.lang.String, com.bln.framework.session.info.SessionInfo)
	 */
	public ISessionInfo put(String sessionId, SessionInfo sessionInfo){
		
		Connection conn = null;
		try {
			
			conn = getDBConnection();
			SessionDao sessionDao = new SessionDao(conn, this.tableName);
						
			sessionInfo = sessionDao.insert(sessionInfo);
			
		} catch (SQLException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
			
		} catch (IOException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
		}finally{
			try {
				if(conn != null){
					conn.close();
				}				
			} catch (SQLException e) {
				NewSessionException nse = new NewSessionException();
				nse.initCause(e);
				
				throw nse;
			}
		}
		
		return sessionInfo;
	}
		
	/* (non-Javadoc)
	 * @see com.bln.framework.session.store.ISessionStore#get(java.lang.String)
	 */
	public ISessionInfo get(String sessionId) {
		
		ISessionInfo sessionInfo = null;
		Connection conn = null;
		try {
						
			conn = getDBConnection();
			SessionDao sessionDao = new SessionDao(conn, this.tableName);
			
			sessionInfo = sessionDao.queryById(sessionId);
			
		} catch (SQLException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
			
		} catch (IOException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
		}finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				NewSessionException nse = new NewSessionException();
				nse.initCause(e);
				
				throw nse;
			}
		}
		
		return sessionInfo;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.store.ISessionStore#updateSession(com.bln.framework.session.info.SessionInfo)
	 */
	public void updateSession(ISessionInfo sessionInfo) {

		Connection conn = null;
		try {
						
			conn = getDBConnection();
			SessionDao sessionDao = new SessionDao(conn, this.tableName);
			
			sessionDao.update(sessionInfo);
			
		} catch (SQLException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
			
		} catch (IOException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
		}finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				NewSessionException nse = new NewSessionException();
				nse.initCause(e);
				
				throw nse;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.store.ISessionStore#updateLastRequestDate(com.bln.framework.session.ISessionInfo, java.util.Date)
	 */
	public void updateLastRequestDate(ISessionInfo sessionInfo, Date requestDate) {

		Connection conn = null;
		try {
			
			sessionInfo.setLastreqdate(requestDate);
			
			conn = getDBConnection();
			SessionDao sessionDao = new SessionDao(conn, this.tableName);
			sessionDao.updateLastRequestDate(sessionInfo);
			
		} catch (SQLException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
			
		} catch (IOException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
		}finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				NewSessionException nse = new NewSessionException();
				nse.initCause(e);
				
				throw nse;
			}
		}
	}
	

	/* (non-Javadoc)
	 * @see com.bln.framework.session.store.ISessionStore#remove(java.lang.String)
	 */
	public void remove(String sessionId) {
		Connection conn = null;
		try {
						
			conn = getDBConnection();
			SessionDao sessionDao = new SessionDao(conn, this.tableName);
			
			sessionDao.delete(sessionId);
			
		} catch (SQLException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
			
		} catch (IOException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
		}finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				NewSessionException nse = new NewSessionException();
				nse.initCause(e);
				
				throw nse;
			}
		}
	}

	/**
	 * 获得所有实体
	 * @return
	 * @see java.util.Map#entrySet()
	 */
	public List<SessionInfo> listVals() {
		
		List<SessionInfo> infos = null;
		Connection conn = null;
		try {
						
			conn = getDBConnection();
			
			SessionDao sessionDao = new SessionDao(conn, this.tableName);
			infos = sessionDao.query(null);

		} catch (SQLException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
			
		} catch (IOException e) {
			NewSessionException nse = new NewSessionException();
			nse.initCause(e);
			
			throw nse;
		}finally{
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				NewSessionException nse = new NewSessionException();
				nse.initCause(e);
				
				throw nse;
			}
		}
		return infos;
	}

	/**
	 * @return the connectionBuilder
	 */
	public IConnectionBuilder getConnectionBuilder() {
		return connectionBuilder;
	}

	/**
	 * @param connectionBuilder the connectionBuilder to set
	 */
	public void setConnectionBuilder(IConnectionBuilder connectionBuilder) {
		this.connectionBuilder = connectionBuilder;
	}


}
