/**
 * @author gengw
 * Created on Jan 20, 2013
 */
package com.bln.framework.net.mq.ibm.connpool;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQSimpleConnectionManager;

/**
 * MQ连接池
 */
public class MQConnPool {
	
	/**
	 * 超时时间
	 */
	protected int timeout = 3600000;
	
	/**
	 * 最大连接数
	 */
	protected int maxConnections = 75;
	
	/**
	 * 最大闲置连接数
	 */
	protected int maxUnUsedConnections = 50;
	
	/**
	 * 连接池
	 */
	MQSimpleConnectionManager myConnMan = null;
	
	/**
	 * 构造函数
	 */
	public MQConnPool(){
		myConnMan = new MQSimpleConnectionManager();
		myConnMan.setActive(MQSimpleConnectionManager.MODE_ACTIVE);
	}
	
	/**
	 * 初始化连接池
	 */
	protected void initPool(){
		
        myConnMan.setTimeout(timeout);
        myConnMan.setMaxConnections(maxConnections);
        myConnMan.setMaxUnusedConnections(maxUnUsedConnections);

        MQEnvironment.setDefaultConnectionManager(myConnMan);
        System.out.println("--------------initPool---------------");
	}
		
	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}


	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
		initPool();
	}


	/**
	 * @return the maxConnections
	 */
	public int getMaxConnections() {
		return maxConnections;
	}


	/**
	 * @param maxConnections the maxConnections to set
	 */
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
		initPool();
	}


	/**
	 * @return the maxUnUsedConnections
	 */
	public int getMaxUnUsedConnections() {
		return maxUnUsedConnections;
	}


	/**
	 * @param maxUnUsedConnections the maxUnUsedConnections to set
	 */
	public void setMaxUnUsedConnections(int maxUnUsedConnections) {
		this.maxUnUsedConnections = maxUnUsedConnections;
		initPool();
	}
}
