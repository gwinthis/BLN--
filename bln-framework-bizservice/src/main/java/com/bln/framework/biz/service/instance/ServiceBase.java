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
 * 服务基类
 */
public abstract class ServiceBase extends BaseObj implements IService{

	/**
	 * 服务配置
	 */
	protected IServiceConfig serviceConfig = null;

	/**
	 * 消息对象模板
	 */
	protected IMoTemplate moTemplate = null;
		
	/**
	 * 错误码映射
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
	 * 回滚事务
	 * @param reason 回滚原因
	 * @throws Throwable 异常
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