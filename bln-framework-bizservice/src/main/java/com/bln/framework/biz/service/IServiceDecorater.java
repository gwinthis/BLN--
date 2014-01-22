/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.biz.service;

import com.bln.framework.mo.IMessageObject;

/**
 * ����װ�νӿ�
 */
public interface IServiceDecorater {

	/**
	 * ��÷������
	 * @return the service
	 */
	public IService getService();

	/**
	 * ���÷������
	 * @param service the service to set
	 */
	public void setService(IService service);

	/**
	 * ������
	 * @param reqMo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
	 * @throws Throwable ����ʱ���׳����쳣
	 */
	public IMessageObject service(IMessageObject reqMo) throws Throwable;
}
