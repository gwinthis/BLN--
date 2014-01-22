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
 * ��������������
 */
public abstract class ServiceSecurityIntercepterBase extends ServiceIntercepterBase implements IServiceIntercepter{

	/**
	 * session�������
	 */
	public ISessionMan sessionMan = null;
	
	/**
	 * ����ҪУ��ķ���
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
	 * �Ƿ���ҪУ��
	 * @param serviceId ����ID
	 * @return �Ƿ���ҪУ��
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
