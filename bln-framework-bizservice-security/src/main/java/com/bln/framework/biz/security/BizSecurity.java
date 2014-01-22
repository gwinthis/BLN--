/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.biz.security;

import com.bln.framework.app.context.AppContext;
import com.bln.framework.session.ISessionInfo;
import com.bln.framework.session.ISessionMan;

/**
 * 应用安全对象
 */
public class BizSecurity implements IBizSecurity {
	
	/**
	 * 登录关键字
	 */
	protected static final String LOGIN_KEY = "USEID";
	
	/**
	 * 客户端IP
	 */
	protected static final String CLIENT_IP = "CLIENT_IP";
	
//	private static int count = 0;
//	
//	public BizSecurity(){
//		count++;
//		System.out.println("@@@@@@@@@BizSecurity@@@@@@@@" + count);
//	}
	/**
	 * session管理对象
	 */
	protected ISessionMan sessionMan = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.app.security.IAppSecurity#logIn(java.lang.String, java.lang.String)
	 */
	public void logIn(String sessionId, String userId){
		
		//获取Session信息
		ISessionInfo sessionInfo = sessionMan.getSessionInfo(sessionId);
		
		//设置登录标志位
		sessionInfo.setSessionData(LOGIN_KEY, userId);
		
		//设置客户端IP
		String clientIP = getClientIP();
		sessionInfo.setSessionData(CLIENT_IP, clientIP);
		
		//更新
		sessionMan.updateSessionInfo(sessionInfo);
	}
	
	
	/* (non-Javadoc)
	 * @see com.bln.framework.app.security.IAppSecurity#isLogIn(java.lang.String, java.lang.String)
	 */
	public boolean isLogIn(String sessionId, String userId){
		
		//获取Session信息
		ISessionInfo sessionInfo = sessionMan.getSessionInfo(sessionId);
		if(sessionInfo == null){
			return false;
		}
		
		boolean isLogIn = true;
		
		//判断登录标志位是否一致
		String userIdInSession = (String)sessionInfo.getSessionData(LOGIN_KEY);
		if(userId == null || !userId.equals(userIdInSession)){
			isLogIn = false;
		}
		
		//判断登录IP和访问IP是同一个
		if(isLogIn){
			
			String clientIP = getClientIP();
			if(clientIP != null && !clientIP.equals(sessionInfo.getSessionData(CLIENT_IP))){
				isLogIn = false;
			}
		}
		
		//返回登录状态
		return isLogIn;

	}
	
	/**
	 * 获取当前访问者的IP
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
