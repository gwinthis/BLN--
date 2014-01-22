/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.biz.service.intercepter;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.biz.service.IServiceIntercepter;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.session.ISessionInfo;

/**
 * 存活的Session校验器
 * 该模块只处理Session信息，是否登录的校验在登录过滤器中处理。
 */
public class LiveSessionIntercepter extends ServiceSecurityIntercepterBase implements IServiceIntercepter{

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IServiceIntercepter#intercept(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject intercept(IMessageObject reqMo){
		
		IMessageObject respMo = null;
		
		//1.获取SessionId
		String sessionId = reqMo.getSessionId();
		
		//2.如果为空则初始化Session，否则校验Session
		if(StringUtils.isEmpty(sessionId)){
			
			sessionId = sessionMan.newSession().getSessionid();
			reqMo.setSessionId(sessionId);
			
		}else{
			
			//根据SessionId获取Session对象
			ISessionInfo sessionInfo = this.sessionMan.getSessionInfo(sessionId);
			
			//校验session是否有效
			boolean isValid = sessionInfo != null && !this.sessionMan.isTimeOut(sessionInfo);
			
			//如果有效继续存活Session, 如果无效返回错误
			if(isValid){
				this.sessionMan.keepLive(sessionInfo);
			}else{
				respMo = this.moTemplate.serviceFailedEnd(reqMo, this.errorCode, this.errorDesc);
			}	
			
		}
		
		return respMo;
	}
}
