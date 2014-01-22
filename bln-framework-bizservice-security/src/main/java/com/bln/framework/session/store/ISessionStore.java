package com.bln.framework.session.store;

import java.util.Date;
import java.util.List;

import com.bln.framework.session.ISessionInfo;
import com.bln.framework.session.info.SessionInfo;

public interface ISessionStore {

	/**
	 * ���Session��Ϣ
	 * @param sessionId
	 * @param sessionInfo
	 * @return
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public abstract ISessionInfo put(String sessionId, SessionInfo sessionInfo);

	/**
	 * ����sessionId���Session��Ϣ
	 * @param key
	 * @return
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public abstract ISessionInfo get(String sessionId);

	/**
	 * ����sessionIdɾ��Session��Ϣ
	 * @param sessionId
	 * @return SessionInfo
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public abstract void remove(String sessionId);

	/**
	 * ����Session
	 * @param sessionId
	 */
	public void updateSession(ISessionInfo sessionInfo);

	/**
	 * ����Session��������ʱ��
	 * @param sessionInfo session��Ϣ
	 * @param requestDate ����ʱ��
	 */
	public void updateLastRequestDate(ISessionInfo sessionInfo, Date requestDate);

	/**
	 * ���ֵ�б�
	 * @return
	 */
	public List<SessionInfo> listVals();

}