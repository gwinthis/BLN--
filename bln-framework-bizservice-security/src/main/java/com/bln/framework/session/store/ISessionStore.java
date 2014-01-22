package com.bln.framework.session.store;

import java.util.Date;
import java.util.List;

import com.bln.framework.session.ISessionInfo;
import com.bln.framework.session.info.SessionInfo;

public interface ISessionStore {

	/**
	 * 添加Session信息
	 * @param sessionId
	 * @param sessionInfo
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public abstract ISessionInfo put(String sessionId, SessionInfo sessionInfo);

	/**
	 * 根据sessionId获得Session信息
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public abstract ISessionInfo get(String sessionId);

	/**
	 * 根据sessionId删除Session信息
	 * @param sessionId
	 * @return SessionInfo
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public abstract void remove(String sessionId);

	/**
	 * 更新Session
	 * @param sessionId
	 */
	public void updateSession(ISessionInfo sessionInfo);

	/**
	 * 更新Session的最后访问时间
	 * @param sessionInfo session信息
	 * @param requestDate 更新时间
	 */
	public void updateLastRequestDate(ISessionInfo sessionInfo, Date requestDate);

	/**
	 * 获得值列表
	 * @return
	 */
	public List<SessionInfo> listVals();

}