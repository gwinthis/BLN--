package com.bln.framework.net.mq.poller;

import java.io.IOException;

import com.bln.framework.net.mq.IMsgConsumer;
import com.bln.framework.net.mq.dao.IQueueDao;
import com.bln.framework.net.mq.exception.AccessQueueException;
import com.bln.framework.net.mq.exception.ConnectionException;

public interface IMQPoller {

	/**
	 * 获取消息
	 * @throws AccessQueueException
	 * @throws IOException
	 * @throws ConnectionException 
	 */
	public void pollMsg();

	/**
	 * @return the queueDao
	 */
	public abstract IQueueDao getQueueDao();

	/**
	 * @param queueDao the queueDao to set
	 */
	public abstract void setQueueDao(IQueueDao queueDao);

	/**
	 * @return the msgConsumer
	 */
	public abstract IMsgConsumer getMsgConsumer();

	/**
	 * @param msgConsumer the msgConsumer to set
	 */
	public abstract void setMsgConsumer(IMsgConsumer msgConsumer);

	/**
	 * @return the waitInterval
	 */
	public abstract int getWaitInterval();

	/**
	 * @param waitInterval the waitInterval to set
	 */
	public abstract void setWaitInterval(int waitInterval);

}