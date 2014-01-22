/**
 * @author gengw
 * Created on Jan 23, 2013
 */
package com.bln.framework.session.store.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bln.framework.session.ISessionInfo;
import com.bln.framework.session.info.SessionInfo;
import com.bln.framework.session.store.ISessionStore;

/**
 * Map存储
 */
public class MapStore implements ISessionStore {
	
	/**
	 * session池
	 */
	public Map<String, SessionInfo> sessionPool = Collections.synchronizedMap(new HashMap<String, SessionInfo>());

	/* (non-Javadoc)
	 * @see com.bln.framework.session.store.ISessionStore#put(java.lang.String, com.bln.framework.session.info.SessionInfo)
	 */
	public ISessionInfo put(String sessionId, SessionInfo sessionInfo) {
		return sessionPool.put(sessionId, sessionInfo);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.store.ISessionStore#get(java.lang.String)
	 */
	public ISessionInfo get(String sessionId) {
		return sessionPool.get(sessionId);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.store.ISessionStore#remove(java.lang.String)
	 */
	public void remove(String sessionId) {
		sessionPool.remove(sessionId);
	}

	/**
	 * 获得值列表
	 * @return
	 */
	public List<SessionInfo> listVals(){
		
		List<SessionInfo> infos = new ArrayList<SessionInfo>(sessionPool.size());
		for ( Map.Entry<String, SessionInfo> entry : sessionPool.entrySet()){
			infos.add(entry.getValue());
		}
		
		return infos;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.store.ISessionStore#updateSession(com.bln.framework.session.info.SessionInfo)
	 */
	public void updateSession(ISessionInfo sessionInfo) {
		
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.store.ISessionStore#updateLastRequestDate(java.util.Date)
	 */
	public void updateLastRequestDate(ISessionInfo sessionInfo, Date requestDate) {
		sessionInfo.setLastreqdate(requestDate);
	}
}
