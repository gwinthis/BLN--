package com.bln.framework.session;


public interface ISessionMan {

	/**
	 * 新建Session
	 * @return SessionInfo
	 */
	public abstract ISessionInfo newSession();

	/**
	 * 根据sessionId获取SessionInfo
	 * @param sessionId
	 * @return sessionId所对应的SessionInfo
	 */
	public abstract ISessionInfo getSessionInfo(String sessionId);

	/**
	 * 更新Session，保持存活
	 * @param sessionId
	 * @return
	 */
	public abstract void keepLive(String sessionId);

	/**
	 * 删除指定sessionId的Session
	 * @param sessionId
	 */
	public abstract void removeSession(String sessionId);

	/**
	 * 删除所有超时的Session
	 */
	public abstract void removeAllTimeoutSession();

	/**
	 * 是否超时
	 * @param sessionId
	 * @return 是否超时
	 * <li>true : 已超时</li>
	 * <li>false : 未超时</li>
	 */
	public abstract boolean isTimeOut(String sessionId);

	/**
	 * 是否超时
	 * @param sessionInfo session信息
	 * @return 是否超时
	 * <li>true : 已超时</li>
	 * <li>false : 未超时</li>
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
	 * 保持Session存活
	 * @param sessionInfo 需要存活的sessionInfo
	 */
	public void keepLive(ISessionInfo sessionInfo);

	/**
	 * 更新Sessino信息
	 * @param sessionInfo
	 * @return
	 */
	public ISessionInfo updateSessionInfo(ISessionInfo sessionInfo);

}