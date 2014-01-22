/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.biz.service.intercepter;

import java.util.Arrays;

import com.bln.framework.biz.service.IServiceIntercepter;
import com.bln.framework.biz.service.intercepter.ServiceIntercepterBase;
import com.bln.framework.session.ISessionMan;

/**
 * 服务拦截器基类
 */
public abstract class ServiceSecurityIntercepterBase extends ServiceIntercepterBase implements IServiceIntercepter{

	/**
	 * session管理对象
	 */
	public ISessionMan sessionMan = null;
	
	/**
	 * 不需要校验的服务
	 */
	public String[] unCheckServiceIds = null;

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

	/**
	 * 是否需要校验
	 * @param serviceId 服务ID
	 * @return 是否需要校验
	 */
	protected boolean isNeedCheck(String serviceId) {
		boolean needCheck = true;
		if(unCheckServiceIds != null && unCheckServiceIds.length > 0){
			needCheck = Arrays.binarySearch(unCheckServiceIds, serviceId) < 0;
		}
		
		return needCheck;
		
	}

	/**
	 * @return the unCheckServiceIds
	 */
	public String[] getUnCheckServiceIds() {
		return unCheckServiceIds;
	}

	/**
	 * @param unCheckServiceIds the unCheckServiceIds to set
	 */
	public void setUnCheckServiceIds(String[] unCheckServiceIds) {
		this.unCheckServiceIds = unCheckServiceIds;
		Arrays.sort(unCheckServiceIds);
	}
}
