package com.bln.framework.biz.service;

import com.bln.framework.mo.IMessageObject;

/**
 * ����������
 */
public interface IServiceIntercepter {

	/**
	 * ���ط���
	 * @param reqMo ������Ϣ����
	 * @return ��Ӧ���� null ��������, not �ܾ�����
	 */
	public IMessageObject intercept(IMessageObject reqMo) throws Throwable;


}