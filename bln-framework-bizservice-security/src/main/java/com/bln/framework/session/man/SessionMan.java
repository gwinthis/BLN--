/**
 * @author gengw
 * Created on Apr 5, 2012
 */
package com.bln.framework.session.man;

import java.util.Date;
import java.util.UUID;

import com.bln.framework.base.BaseObj;
import com.bln.framework.session.ISessionInfo;
import com.bln.framework.session.ISessionMan;
import com.bln.framework.session.info.SessionInfo;
import com.bln.framework.session.store.ISessionStore;

/**
 * Session管理对象
 */
public class SessionMan extends BaseObj implements ISessionMan{
	
	/**
	 * session信息存储器
	 */
	protected ISessionStore sessionPool = null;
	
	/**
	 * <p>session超时时间</p>
	 * <p>单位为分钟</p>
	 */
	protected int timeoutDuration = 0;
	
//	private static int count = 0;
//	
//	public SessionMan(){
//		count++;
//		System.out.println("@@@@@@@@@SessionMan@@@@@@@@" + count);
//	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.ISessionMan#newSession()
	 */
	public ISessionInfo newSession(){
		
		//获取SessionId
		String sessionId = generateSessionId();

		//新建新的Session
		SessionInfo sessionInfo = new SessionInfo(sessionId, new Date());
				
		//把Session实例添加到Session池中
		sessionPool.put(sessionId, sessionInfo);
		
		//返回SessionId
		return sessionInfo;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.ISessionMan#getSessionInfo(java.lang.String)
	 */
	public ISessionInfo getSessionInfo(String sessionId){
		
		ISessionInfo sessionInfo = sessionPool.get(sessionId);
		return sessionInfo;
	}
	
	/**
	 * 更新Sessino信息
	 * @param sessionInfo
	 * @return
	 */
	public ISessionInfo updateSessionInfo(ISessionInfo sessionInfo){
		
		sessionPool.updateSession(sessionInfo);
		return sessionInfo;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.ISessionMan#keepLive(java.lang.String)
	 */
	public void keepLive(String sessionId){
		ISessionInfo sessionInfo = sessionPool.get(sessionId);
		keepLive(sessionInfo);
	}
	
	/**
	 * 保持Session存活
	 * @param sessionInfo 需要存活的sessionInfo
	 */
	public void keepLive(ISessionInfo sessionInfo){
		Date requestDate = new Date();
		sessionPool.updateLastRequestDate(sessionInfo, requestDate);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.session.ISessionMan#removeSession(java.lang.String)
	 */
	public void removeSession(String sessionId){
		sessionPool.remove(sessionId);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.ISessionMan#removeAllTimeoutSession()
	 */
	public void removeAllTimeoutSession(){
		
		//未定义超时，直接返回
		if(timeoutDuration == 0){
			return;
		}
		
		//删除所有超时的Session
		for ( ISessionInfo sessionInfo : sessionPool.listVals()){
			if(isTimeOut(sessionInfo)){
				sessionPool.remove(sessionInfo.getSessionid());
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.ISessionMan#isTimeOut(java.lang.String)
	 */
	public boolean isTimeOut(String sessionId){
		
		boolean isTimeOut = false;
		if(timeoutDuration == 0){
			isTimeOut = false;
		}else{
			ISessionInfo sessionInfo = sessionPool.get(sessionId);
			if(sessionInfo == null){
				isTimeOut = false;
			}else{
				isTimeOut = isTimeOut(sessionInfo);
			}
		}
		
		return isTimeOut;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.session.ISessionMan#isTimeOut(com.bln.framework.session.SessionInfo)
	 */
	public boolean isTimeOut(ISessionInfo sessionInfo){
		
		boolean isTimeOut = false;
		if(timeoutDuration == 0){
			isTimeOut = false;
		}else{
			if(sessionInfo == null){
				isTimeOut = true;
			}else{
				isTimeOut = new Date().getTime() - sessionInfo.getLastreqdate().getTime() >= 1000 * 60 * timeoutDuration;
			}
		}
		
		return isTimeOut;
	}
	
	/**
	 * 生成新的SessionId
	 * @return
	 */
	protected String generateSessionId(){
		return UUID.randomUUID().toString();
	}

	/**
	 * @return the timeoutDuration
	 */
	public int getTimeoutDuration() {
		return timeoutDuration;
	}

	/**
	 * @param timeoutDuration the timeoutDuration to set
	 */
	public void setTimeoutDuration(int timeoutDuration) {
		this.timeoutDuration = timeoutDuration;
	}

	/**
	 * @return the sessionPool
	 */
	public ISessionStore getSessionPool() {
		return sessionPool;
	}

	/**
	 * @param sessionPool the sessionPool to set
	 */
	public void setSessionPool(ISessionStore sessionPool) {
		this.sessionPool = sessionPool;
	}
}
