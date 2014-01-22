/**
 * @author gengw
 * Created on Apr 28, 2012
 */
package com.bln.framework.persist.session.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.builder.ISessionBuilder;
import com.bln.framework.persist.session.factory.exception.OpenSessionException;
import com.bln.framework.util.reflect.SeeWhoCall;

/**
 * Session工厂
 */
public class SessionFactory implements ISessionFactory {
	
	private static final Log log = LogFactory.getLog(SessionFactory.class);
	
	/**
	 * Session模板
	 */
	protected ISession sessionTemplate = null;
	
	/**
	 * Session关键字所对应的Session构造器Map
	 */
	protected Map<Object, ISessionBuilder> keySessionBuilderMap = null;
	
	/**
	 * 当前线程下sessionKey所对应的当前Session值
	 */
	protected ThreadLocal<Map<Object, ISession>> keyCurrentSession = new ThreadLocal<Map<Object, ISession>>();
	
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.factory.ISessionFactory#openSession()
	 */
	public ISession openSession(){
		return this.openSession((Map<String, String>)null);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.factory.ISessionFactory#openSession(java.util.Map)
	 */
	public ISession openSession(Object sessionKey){
		
		//1.获取SessionBuilder
		ISessionBuilder sessionBuilder = keySessionBuilderMap.get(sessionKey);
		if(sessionBuilder == null){
			OpenSessionException ose = new OpenSessionException("not found sessionKey in the keySessionMap!");
			ose.addContextValue("sessionKey", sessionKey);
			ose.addContextValue("keySessionMap", keySessionBuilderMap);
			throw ose;
		}
		
		//2.生成Session
		ISession session = sessionBuilder.build();
		if(log.isDebugEnabled()){
			SeeWhoCall.Caller[] callers = SeeWhoCall.getAllCallers();
			SeeWhoCall.Caller lastCaller = callers[0];
			if("SessionFactory.java".equals(lastCaller.getFileName())){
				lastCaller = callers[1]; 
			}
			log.debug(lastCaller + " open session with key [" + sessionKey + "]");
		}
		
		//3.返回Session
		return session;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.factory.ISessionFactory#openSession(java.sql.Connection)
	 */
	public ISession openSession(Connection connection){
		
		ISession session = sessionTemplate.clone(connection);
		log.debug(SeeWhoCall.getLastCaller() + " open session with connection [" + connection + "]");
		
		return session;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.factory.ISessionFactory#getCurrentSession()
	 */
	public ISession getCurrentSession()throws SQLException{
		return this.getCurrentSession(null);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.factory.ISessionFactory#getCurrentSession(java.util.Map)
	 */
	public ISession getCurrentSession(Object sessionKey) throws SQLException{
		
		//1.获得当前线程下SessionKey和Session的Map
		Map<Object, ISession> keySessionMap = this.keyCurrentSession.get();
		if(keySessionMap == null){
			keySessionMap = new HashMap<Object, ISession>();
			keyCurrentSession.set(keySessionMap);
		}
		
		//2.获取当前Session
		ISession session = keySessionMap.get(sessionKey);
		if(session == null || session.isClosed()){
			session = this.openSession(sessionKey);
			keySessionMap.put(sessionKey, session);
		}
		
		//3.返回当前Session
		return session;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.factory.ISessionFactory#closeCurrentAllSession()
	 */
	public void closeCurrentAllSession() throws SQLException{
		
		Map<Object, ISession> keySessionMap = this.keyCurrentSession.get();
		if(keySessionMap != null && !keySessionMap.isEmpty()){
			
			for ( Map.Entry<Object, ISession> sessionEntry : keySessionMap.entrySet()){
				ISession session = sessionEntry.getValue();
				if(session != null){
					session.close();
					session = null;
					sessionEntry.setValue(null);
				}
			}
			this.keyCurrentSession.set(null);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.factory.ISessionFactory#getCurrentAllSession()
	 */
	public List<ISession> getCurrentAllSession(){
		
		List<ISession> sessions = null;
		
		Map<Object, ISession> keySessionMap = this.keyCurrentSession.get();
		if(keySessionMap != null && !keySessionMap.isEmpty()){
			
			sessions = new ArrayList<ISession>();
			for ( Map.Entry<Object, ISession> sessionEntry : keySessionMap.entrySet()){
				ISession session = sessionEntry.getValue();
				sessions.add(session);
			}
		}
		
		return sessions;
		
	}
	 
	/**
	 * @return the keySessionBuilderMap
	 */
	public Map<Object, ISessionBuilder> getKeySessionBuilderMap() {
		return keySessionBuilderMap;
	}

	/**
	 * @param keySessionBuilderMap the keySessionBuilderMap to set
	 */
	public void setKeySessionBuilderMap(Map<Object, ISessionBuilder> keySessionBuilderMap) {
		this.keySessionBuilderMap = keySessionBuilderMap;
	}
	
	/**
	 * @param sessionTemplate the sessionTemplate to set
	 */
	public void setSessionTemplate(ISession sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}


}
