/**
 * @author gengw
 * Created on Feb 6, 2013
 */
package com.bln.framework.net.mq.dao;

import com.bln.framework.net.mq.exception.ConnectionException;
import com.bln.framework.net.mq.exception.MQTransException;

/**
 * 
 */
public interface ITransactionQueueDao extends IQueueDao{

	/**
	 * ���Ӷ��й�����
	 * @throws ConnectionException
	 */
	public void connect() throws ConnectionException;

	/**
	 * �ر�����
	 * @throws ConnectionException
	 */
	public void disconnect() throws ConnectionException;

	/**
	 * ��������
	 * @throws MQTransException
	 */
	public void beginTransaction() throws MQTransException;

	/**
	 * �ύ����
	 * @throws MQTransException
	 */
	public void commit() throws MQTransException;

	/**
	 * �ع�����
	 * @throws MQTransException
	 */
	public void rollback() throws MQTransException;

}
