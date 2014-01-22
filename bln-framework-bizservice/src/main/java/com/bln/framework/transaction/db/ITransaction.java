/**
 * @author gengw
 * Created on Mar 30, 2012
 */
package com.bln.framework.transaction.db;

import com.bln.framework.biz.service.IService;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.transaction.exception.TransException;

/**
 * ������
 */
public interface ITransaction {
	
	/**
	 * �����µ����ﴦ��
	 * @param serviceObj �������
	 * @param reqMo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
	 * @throws Throwable ����ʱ���׳����쳣
	 */
	public IMessageObject requireNewTrans(IService serviceObj, IMessageObject reqMo) throws TransException;

	/**
	 * �����ǰ��������ӵ���ǰ�����У����������µ�����
	 * @param serviceObj �������
	 * @param reqMo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
	 * @throws Throwable ����ʱ���׳����쳣
	 */
	public IMessageObject requireTrans(IService serviceObj, IMessageObject reqMo) throws TransException;

}
