/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.biz.service;

import com.bln.framework.mo.IMessageObject;

/**
 * ��������ӿ�
 */
public interface IServiceFacade {
	
	/**
	 * @param reqMo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
	 * @throws Throwable ��������׳����쳣
	 */
	public IMessageObject service(IMessageObject reqMo);

}
