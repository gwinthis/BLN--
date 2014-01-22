/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.biz.service.instance;

import java.util.Map;


import com.bln.framework.base.BaseObj;
import com.bln.framework.biz.service.IService;
import com.bln.framework.biz.service.instance.config.IServiceConfig;
import com.bln.framework.mo.template.IMoTemplate;

/**
 * �������
 */
public abstract class ServiceBase extends BaseObj implements IService{

	/**
	 * ��������
	 */
	protected IServiceConfig serviceConfig = null;

	/**
	 * ��Ϣ����ģ��
	 */
	protected IMoTemplate moTemplate = null;
		
	/**
	 * ������ӳ��
	 */
	protected Map<String, String> errorCodeMap = null;
	
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
	 * @return the serviceConfig
	 */
	public IServiceConfig getServiceConfig() {
		return serviceConfig;
	}

	/**
	 * @param serviceConfig the serviceConfig to set
	 */
	public void setServiceConfig(IServiceConfig serviceConfig) {
		this.serviceConfig = serviceConfig;
	}

	/**
	 * �ع�����
	 * @param reason �ع�ԭ��
	 * @throws Throwable �쳣
	 */
	protected void rollback(String reason) throws Throwable{
		
		Exception e = new Exception(reason);
		throw e;
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

	

}