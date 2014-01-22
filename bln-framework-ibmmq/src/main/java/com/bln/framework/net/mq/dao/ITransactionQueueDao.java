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
	 * 连接队列管理器
	 * @throws ConnectionException
	 */
	public void connect() throws ConnectionException;

	/**
	 * 关闭连接
	 * @throws ConnectionException
	 */
	public void disconnect() throws ConnectionException;

	/**
	 * 启动事务
	 * @throws MQTransException
	 */
	public void beginTransaction() throws MQTransException;

	/**
	 * 提交事务
	 * @throws MQTransException
	 */
	public void commit() throws MQTransException;

	/**
	 * 回滚事务
	 * @throws MQTransException
	 */
	public void rollback() throws MQTransException;

}
