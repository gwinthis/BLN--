/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.biz.security;

import com.bln.framework.app.context.AppContext;
import com.bln.framework.session.ISessionInfo;
import com.bln.framework.session.ISessionMan;

/**
 * Ӧ�ð�ȫ����
 */
public class BizSecurity implements IBizSecurity {
	
	/**
	 * ��¼�ؼ���
	 */
	protected static final String LOGIN_KEY = "USEID";
	
	/**
	 * �ͻ���IP
	 */
	protected static final String CLIENT_IP = "CLIENT_IP";
	
//	private static int count = 0;
//	
//	public BizSecurity(){
//		count++;
//		System.out.println("@@@@@@@@@BizSecurity@@@@@@@@" + count);
//	}
	/**
	 * session�������
	 */
	protected ISessionMan sessionMan = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.app.security.IAppSecurity#logIn(java.lang.String, java.lang.String)
	 */
	public void logIn(String sessionId, String userId){
		
		//��ȡSession��Ϣ
		ISessionInfo sessionInfo = sessionMan.getSessionInfo(sessionId);
		
		//���õ�¼��־λ
		sessionInfo.setSessionData(LOGIN_KEY, userId);
		
		//���ÿͻ���IP
		String clientIP = getClientIP();
		sessionInfo.setSessionData(CLIENT_IP, clientIP);
		
		//����
		sessionMan.updateSessionInfo(sessionInfo);
	}
	
	
	/* (non-Javadoc)
	 * @see com.bln.framework.app.security.IAppSecurity#isLogIn(java.lang.String, java.lang.String)
	 */
	public boolean isLogIn(String sessionId, String userId){
		
		//��ȡSession��Ϣ
		ISessionInfo sessionInfo = sessionMan.getSessionInfo(sessionId);
		if(sessionInfo == null){
			return false;
		}
		
		boolean isLogIn = true;
		
		//�жϵ�¼��־λ�Ƿ�һ��
		String userIdInSession = (String)sessionInfo.getSessionData(LOGIN_KEY);
		if(userId == null || !userId.equals(userIdInSession)){
			isLogIn = false;
		}
		
		//�жϵ�¼IP�ͷ���IP��ͬһ��
		if(isLogIn){
			
			String clientIP = getClientIP();
			if(clientIP != null && !clientIP.equals(sessionInfo.getSessionData(CLIENT_IP))){
				isLogIn = false;
			}
		}
		
		//���ص�¼״̬
		return isLogIn;

	}
	
	/**
	 * ��ȡ��ǰ�����ߵ�IP
	 * @return
	 */
	protected String getClientIP(){
		return (String)AppContext.singleton().getValue(CLIENT_IP);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.app.security.IAppSecurity#logOut(java.lang.String, java.lang.String)
	 */
	public void logOut(String sessionId, String userId){
		sessionMan.removeSession(sessionId);
	}


	/**
	 * @return the sessionMan
	 */
	public ISessionMan getSessionMan() {
		return sessionMan;
	}


	/**
	 * @param sessionMan the sessionMan to set
	 */
	public void setSessionMan(ISessionMan sessionMan) {
		this.sessionMan = sessionMan;
	}

}
