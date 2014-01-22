package com.bln.framework.net.mq.dao;

import java.io.IOException;

import com.bln.framework.net.mq.exception.AccessQueueException;
import com.bln.framework.net.mq.exception.ConnectionException;

/**
 * 队列访问器接口
 */
public interface IQueueDao {

	/**
	 * 获取消息深度
	 * @return 消息数量
	 * @throws AccessQueueException 
	 * @throws ConnectionException 
	 * @throws MQException 
	 */
	public abstract int msgDepth() throws AccessQueueException, ConnectionException;

	/**
	 * 显示首条消息
	 * @return
	 * @throws IOException 
	 * @throws AccessQueueException 
	 * @throws ConnectionException 
	 */
	public abstract byte[] displayMsg() throws IOException,
			AccessQueueException, ConnectionException;

	/**
	 * 读取首条消息，并删除首条消息
	 * @return
	 * @throws IOException 
	 * @throws AccessQueueException 
	 * @throws ConnectionException 
	 */
	public abstract byte[] readMsg() throws AccessQueueException, IOException, ConnectionException;

	/**
	 * 清除首条消息
	 * @return
	 * @throws IOException 
	 * @throws AccessQueueException 
	 * @throws ConnectionException 
	 */
	public abstract byte[] removeMsg() throws AccessQueueException, IOException, ConnectionException;

	/**
	 * 发送消息
	 * @param msg 消息内容
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