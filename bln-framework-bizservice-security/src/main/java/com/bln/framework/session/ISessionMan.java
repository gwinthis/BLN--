package com.bln.framework.session;


public interface ISessionMan {

	/**
	 * �½�Session
	 * @return SessionInfo
	 */
	public abstract ISessionInfo newSession();

	/**
	 * ����sessionId��ȡSessionInfo
	 * @param sessionId
	 * @return sessionId����Ӧ��SessionInfo
	 */
	public abstract ISessionInfo getSessionInfo(String sessionId);

	/**
	 * ����Session�����ִ��
	 * @param sessionId
	 * @return
	 */
	public abstract void keepLive(String sessionId);

	/**
	 * ɾ��ָ��sessionId��Session
	 * @param sessionId
	 */
	public abstract void removeSession(String sessionId);

	/**
	 * ɾ�����г�ʱ��Session
	 */
	public abstract void removeAllTimeoutSession();

	/**
	 * �Ƿ�ʱ
	 * @param sessionId
	 * @return �Ƿ�ʱ
	 * <li>true : �ѳ�ʱ</li>
	 * <li>false : δ��ʱ</li>
	 */
	public abstract boolean isTimeOut(String sessionId);

	/**
	 * �Ƿ�ʱ
	 * @param sessionInfo session��Ϣ
	 * @return �Ƿ�ʱ
	 * <li>true : �ѳ�ʱ</li>
	 * <li>false : δ��ʱ</li>
	 */
	public abstract boolean isTimeOut(ISessionInfo sessionInfo);

	/**
	 * @return the timeoutDuration
	 */
	public int getTimeoutDuration();

	/**
	 * @param timeoutDuration the timeoutDuration to set
	 */
	public void setTimeoutDuration(int timeoutDuration);

	/**
	 * ����Session���
	 * @param sessionInfo ��Ҫ����sessionInfo
	 */
	public void keepLive(ISessionInfo sessionInfo);

	/**
	 * ����Sessino��Ϣ
	 * @param sessionInfo
	 * @return
	 */
	public ISessionInfo updateSessionInfo(ISessionInfo sessionInfo);

}