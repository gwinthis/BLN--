/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.biz.service.intercepter;

import com.bln.framework.biz.security.IBizSecurity;
import com.bln.framework.biz.service.IServiceIntercepter;
import com.bln.framework.mo.IMessageObject;

/**
 * <p>服务执行之前必须先登录</p>
 * 从Session中校验是否已登录
 */
public class LoginFirstIntercepter extends ServiceSecurityIntercepterBase implements IServiceIntercepter {
	
	/**
	 * 业务安全验证组件
	 */
	IBizSecurity bizSecurity = null;
	
	/**
	 * extheader中获取用户标识
	 */
	String useridkeyOfExtHeader = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IServiceIntercepter#intercept(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject intercept(IMessageObject reqMo) throws Throwable {
		
		IMessageObject respMo = null;
		
		//1.判断是否需要校验
		boolean needCheck = isNeedCheck(reqMo.getServiceId());
		
		//2.如果需要校验，则校验是否登录应用
		if(needCheck){
			
			//2.1获取SessionId
			String sessionId = reqMo.getSessionId();
			
			//2.2获取用户标识
			String userId = reqMo.getValueOfExtHeader(useridkeyOfExtHeader);
			
			//2.3校验是否登录，如果未登录系统，生成响应信息
			if(!bizSecurity.isLogIn(sessionId, userId)){
				respMo = this.moTemplate.serviceFailedEnd(reqMo, this.errorCode, this.errorDesc);
			}
		}
		
		return respMo;
	}
	
	/**
	 * @return the bizSecurity
	 */
	public IBizSecurity getBizSecurity() {
		return bizSecurity;
	}

	/**
	 * @param bizSecurity the bizSecurity to set
	 */
	public void setBizSecurity(IBizSecurity bizSecurity) {
		this.bizSecurity = bizSecurity;
	}

	/**
	 * @return the useridkeyOfExtHeader
	 */
	public String getUseridkeyOfExtHeader() {
		return useridkeyOfExtHeader;
	}

	/**
	 * @param useridkeyOfExtHeader the useridkeyOfExtHeader to set
	 */
	public void setUseridkeyOfExtHeader(String useridkeyOfExtHeader) {
		this.useridkeyOfExtHeader = useridkeyOfExtHeader;
	}

}
