package com.bln.framework.biz.security;

import com.bln.framework.session.ISessionMan;

public interface IBizSecurity {

	/**
	 * ��¼Ӧ��
	 * @param sessionId
	 * @param userId
	 * @throws LoginException
	 */
	public abstract void logIn(String sessionId, String userId);

	/**
	 * У���Ƿ��Ѿ���¼
	 * @param sessionId sessionId
	 * @param userId �û���ʶ
	 * @return
	 */
	public abstract boolean isLogIn(String sessionId, String userId);

	/**
	 * �ǳ�Ӧ��
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