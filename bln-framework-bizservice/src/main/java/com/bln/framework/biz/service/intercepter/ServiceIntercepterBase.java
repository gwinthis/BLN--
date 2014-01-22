/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.biz.service.intercepter;

import com.bln.framework.mo.template.IMoTemplate;

/**
 * ��������������
 */
public abstract class ServiceIntercepterBase{

	/**
	 * ��Ϣ����ģ��
	 */
	protected IMoTemplate moTemplate = null;
	
	/**
	 * ���سɹ������ظ��ͻ��˵Ĵ�����
	 */
	protected String errorCode = "";

	/**
	 * ���سɹ������ظ��ͻ��˵Ĵ�������
	 */
	protected String errorDesc = "";

	
	/**
	 * @return the moTemplate
	 */
	public IMoTemplate getMoTemplate() {
		return moTemplate;
	}

	/**
	 * @param moTemplate the moTemplate to set
	 */
	public void setMoTemplate(IMoTemplate moTemplate) {
		this.moTemplate = moTemplate;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
