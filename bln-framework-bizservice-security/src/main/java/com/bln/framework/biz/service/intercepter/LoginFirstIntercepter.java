/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.biz.service.intercepter;

import com.bln.framework.biz.security.IBizSecurity;
import com.bln.framework.biz.service.IServiceIntercepter;
import com.bln.framework.mo.IMessageObject;

/**
 * <p>����ִ��֮ǰ�����ȵ�¼</p>
 * ��Session��У���Ƿ��ѵ�¼
 */
public class LoginFirstIntercepter extends ServiceSecurityIntercepterBase implements IServiceIntercepter {
	
	/**
	 * ҵ��ȫ��֤���
	 */
	IBizSecurity bizSecurity = null;
	
	/**
	 * extheader�л�ȡ�û���ʶ
	 */
	String useridkeyOfExtHeader = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IServiceIntercepter#intercept(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject intercept(IMessageObject reqMo) throws Throwable {
		
		IMessageObject respMo = null;
		
		//1.�ж��Ƿ���ҪУ��
		boolean needCheck = isNeedCheck(reqMo.getServiceId());
		
		//2.�����ҪУ�飬��У���Ƿ��¼Ӧ��
		if(needCheck){
			
			//2.1��ȡSessionId
			String sessionId = reqMo.getSessionId();
			
			//2.2��ȡ�û���ʶ
			String userId = reqMo.getValueOfExtHeader(useridkeyOfExtHeader);
			
			//2.3У���Ƿ��¼�����δ��¼ϵͳ��������Ӧ��Ϣ
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
