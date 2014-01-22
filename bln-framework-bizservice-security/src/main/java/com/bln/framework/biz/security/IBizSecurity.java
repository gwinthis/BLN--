package com.bln.framework.biz.security;

import com.bln.framework.session.ISessionMan;

public interface IBizSecurity {

	/**
	 * 登录应用
	 * @param sessionId
	 * @param userId
	 * @throws LoginException
	 */
	public abstract void logIn(String sessionId, String userId);

	/**
	 * 校验是否已经登录
	 * @param sessionId sessionId
	 * @param userId 用户标识
	 * @return
	 */
	public abstract boolean isLogIn(String sessionId, String userId);

	/**
	 * 登出应用
	 * @param sessionId
	 * @param userId
	 * @throws LoginException
	 */
	public abstract void logOut(String sessionId, String userId);

	/**
	 * @return the sessionMan
	 */
	public ISessionMan getSessionMan();

}