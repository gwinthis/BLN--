/**
 * @author gengw
 * Created on May 24, 2012
 */
package com.bln.framework.biz.service.facade.message;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * �쳣��Ϣӳ����
 */
public class ExceptionMessage implements IExceptionMessage {
	
	/**
	 * ��Ϣ��ӳ��
	 */
	Map<String, String> errorCodeMap = null;

	/**
	 * ��Ϣ����ӳ��
	 */
	Map<String, String> errorDescMap = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.facade.message.IExceptionMessage#getErrorCode(java.lang.String)
	 */
	public String getErrorCode(String clazzName){
		
		String errorCode = null;
		if(errorCodeMap != null && !errorCodeMap.isEmpty()){
			errorCode = errorCodeMap.get(clazzName);
		}

		return errorCode;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.facade.message.IExceptionMessage#getErrorDesc(java.lang.String)
	 */
	public String getErrorDesc(Throwable e){
		
		//1.��ȡ�쳣��Ϣ
		String message = ExceptionUtils.getRootCauseMessage(e);
		
		//2.�����쳣��Ϣ���Ҵ�������
		String errorDesc = null;
		if(errorDescMap != null){
			errorDesc = errorDescMap.get(message);
		}
		
		//���δ�ҵ���Ϣӳ�䣬ʹ���쳣��Ϣ
		if(StringUtils.isEmpty(errorDesc)){
			errorDesc = message;
		}
		
		//3.���ش�������
		return errorDesc;
	}

	/**
	 * @return the errorCodeMap
	 */
	public Map<String, String> getErrorCodeMap() {
		return errorCodeMap;
	}

	/**
	 * @param errorCodeMap the errorCodeMap to set
	 */
	public void setErrorCodeMap(Map<String, String> errorCodeMap) {
		this.errorCodeMap = errorCodeMap;
	}

	/**
	 * @return the errorDescMap
	 */
	public Map<String, String> getErrorDescMap() {
		return errorDescMap;
	}

	/**
	 * @param errorDescMap the errorDescMap to set
	 */
	public void setErrorDescMap(Map<String, String> errorDescMap) {
		this.errorDescMap = errorDescMap;
	}
	

}
