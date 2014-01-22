/**
 * @author gengw
 * Created on 2012-03-28
 */
package com.bln.framework.biz.service;

import com.bln.framework.biz.service.instance.config.IServiceConfig;
import com.bln.framework.mo.IMessageObject;

/**
 * <p>����������ӿ�</p>
 * ����������Ϣ����
 */
public interface IService {


	/**
	 * ִ�з���
	 * @param reqMo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
	 * @throws Throwable ����ִ�����׳����쳣
	 */
	public IMessageObject execute(IMessageObject reqMo) throws Throwable;


	/**
	 * @return the serviceConfig
	 */
	public IServiceConfig getServiceConfig();

}
