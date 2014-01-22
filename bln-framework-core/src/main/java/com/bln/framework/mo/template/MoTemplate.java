/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.mo.template;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.bln.framework.config.IAppConfig;
import com.bln.framework.mo.IMessageObject;

/**
 * ��Ϣ����ģ��
 */
public class MoTemplate implements IMoTemplate {
	
	/**
	 * ��MO����ʱ�Ƿ���Ҫɾ������ڵ�
	 */
	protected boolean removeRequestWhenEnd = true;

	/**
	 * Ӧ�ö���
	 */
	protected IAppConfig appConfig = null;
	
	/**
	 * ����ɹ���ֵ
	 */
	protected String successValue = "00";
	
	/**
	 * ����ʧ�ܵ�ֵ
	 */
	protected String failedValue = "30";
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.template.IMoTemplate#requestEnd(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject requestEnd(IMessageObject mo){
		
		//1.���������Ϣ
		if(this.isRemoveRequestWhenEnd()){
			mo.clearRequest();
		}

		//2.������Ӧʱ��
		String respDate = DateFormatUtils.format(new Date(), appConfig.getDatetimeFormat());
		mo.setResponseDate(respDate);
		
		//3.������Ϣ����
		return mo;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.template.IMoTemplate#serviceSuccessfulEnd(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject serviceSuccessfulEnd(IMessageObject mo){
		
		//������Ϊ�ɹ�
		mo.setReturnCode(successValue);
		
		return mo;
	}
	
	/**
	 * ����ʧ�ܽ��� 
	 * @param mo Ҫ�������Ϣ����
	 * @param errorCode ������
	 * @return ���������Ϣ����
	 */
	public IMessageObject serviceFailedEnd(IMessageObject mo, String errorCode){
		
		return serviceFailedEnd(mo, errorCode, null);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.template.IMoTemplate#serviceFailedEnd(com.bln.framework.mo.IMessageObject, java.lang.String, java.lang.String)
	 */
	public IMessageObject serviceFailedEnd(IMessageObject mo, String errorCode, String respDesc){
		
		//������Ϊʧ��
		mo.setReturnCode(failedValue);
		
		//���ô�����
		mo.setErrorCode(errorCode);
		
		//���ô���������Ϣ
		if(!StringUtils.isEmpty(respDesc)){
			mo.setErrorDesc(respDesc);
		}
		
		//���ش��������Ϣ����
		return mo;
	}

	/**
	 * @return
	 */
	public boolean isRemoveRequestWhenEnd() {
		return removeRequestWhenEnd;
	}

	/**
	 * @param removeRequestWhenEnd the removeRequestWhenEnd to set
	 */
	public void setRemoveRequestWhenEnd(boolean removeRequestWhenEnd) {
		this.removeRequestWhenEnd = removeRequestWhenEnd;
	}
	
	/**
	 * @return the successValue
	 */
	public String getSuccessValue() {
		return successValue;
	}

	/**
	 * @param successValue the successValue to set
	 */
	public void setSuccessValue(String successValue) {
		this.successValue = successValue;
	}

	/**
	 * @return the failedValue
	 */
	public String getFailedValue() {
		return failedValue;
	}

	/**
	 * @param failedValue the failedValue to set
	 */
	public void setFailedValue(String failedValue) {
		this.failedValue = failedValue;
	}

	/**
	 * @return the appConfig
	 */
	public IAppConfig getAppConfig() {
		return appConfig;
	}

	/**
	 * @param appConfig the appConfig to set
	 */
	public void setAppConfig(IAppConfig appConfig) {
		this.appConfig = appConfig;
	}


}
