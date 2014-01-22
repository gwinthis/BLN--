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
 * ����SessionУ����
 * ��ģ��ֻ����Session��Ϣ���Ƿ��¼��У���ڵ�¼�������д���
 */
public class LiveSessionIntercepter extends ServiceSecurityIntercepterBase implements IServiceIntercepter{

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IServiceIntercepter#intercept(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject intercept(IMessageObject reqMo){
		
		IMessageObject respMo = null;
		
		//1.��ȡSessionId
		String sessionId = reqMo.getSessionId();
		
		//2.���Ϊ�����ʼ��Session������У��Session
		if(StringUtils.isEmpty(sessionId)){
			
			sessionId = sessionMan.newSession().getSessionid();
			reqMo.setSessionId(sessionId);
			
		}else{
			
			//����SessionId��ȡSession����
			ISessionInfo sessionInfo = this.sessionMan.getSessionInfo(sessionId);
			
			//У��session�Ƿ���Ч
			boolean isValid = sessionInfo != null && !this.sessionMan.isTimeOut(sessionInfo);
			
			//�����Ч�������Session, �����Ч���ش���
			if(isValid){
				this.sessionMan.keepLive(sessionInfo);
			}else{
				respMo = this.moTemplate.serviceFailedEnd(reqMo, this.errorCode, this.errorDesc);
			}	
			
		}
		
		return respMo;
	}
}
