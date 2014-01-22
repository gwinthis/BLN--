package com.bln.framework.net.mq.dao;

import java.io.IOException;

import com.bln.framework.net.mq.exception.AccessQueueException;
import com.bln.framework.net.mq.exception.ConnectionException;

/**
 * ���з������ӿ�
 */
public interface IQueueDao {

	/**
	 * ��ȡ��Ϣ���
	 * @return ��Ϣ����
	 * @throws AccessQueueException 
	 * @throws ConnectionException 
	 * @throws MQException 
	 */
	public abstract int msgDepth() throws AccessQueueException, ConnectionException;

	/**
	 * ��ʾ������Ϣ
	 * @return
	 * @throws IOException 
	 * @throws AccessQueueException 
	 * @throws ConnectionException 
	 */
	public abstract byte[] displayMsg() throws IOException,
			AccessQueueException, ConnectionException;

	/**
	 * ��ȡ������Ϣ����ɾ��������Ϣ
	 * @return
	 * @throws IOException 
	 * @throws AccessQueueException 
	 * @throws ConnectionException 
	 */
	public abstract byte[] readMsg() throws AccessQueueException, IOException, ConnectionException;

	/**
	 * ���������Ϣ
	 * @return
	 * @throws IOException 
	 * @throws AccessQueueException 
	 * @throws ConnectionException 
	 */
	public abstract byte[] removeMsg() throws AccessQueueException, IOException, ConnectionException;

	/**
	 * ������Ϣ
	 * @param msg ��Ϣ����
	 * @throws IOException 
	 * @throws AccessQueueException 
	 * @throws ConnectionException 
	 */
	public abstract void sendMsg(byte[] msg) throws IOException,
			AccessQueueException, ConnectionException;

	/**
	 * @return the queueName
	 */
	public abstract String getQueueName();

	/**
	 * @param queueName the queueName to set
	 */
	public abstract void setQueueName(String queueName);

	/**
	 * @return the blockRead
	 */
	public abstract boolean isBlockRead();

	/**
	 * @param blockRead the blockRead to set
	 */
	public abstract void setBlockRead(boolean blockRead);

	/**
	 * @return the waitInterval
	 */
	public int getWaitInterval();

	/**
	 * @param waitInterval the waitInterval to set
	 */
	public void setWaitInterval(int waitInterval);

}