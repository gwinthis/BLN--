/**
 * @author gengw
 * Created on Jan 18, 2013
 */
package com.bln.framework.net.mq.ibm;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.net.mq.dao.IQueueDao;
import com.bln.framework.net.mq.exception.AccessQueueException;
import com.bln.framework.net.mq.exception.ConnectionException;
import com.bln.framework.net.mq.ibm.connpool.MQConnPool;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

/**
 * 队列访问对象
 */
public class QueueDao implements IQueueDao {

	/**
	 * 主机名称(IP)
	 */
	protected String hostName = null;

	/**
	 * MQ端口
	 */
	protected int port = 1414;
	
	/**
	 * 队列管理器名称
	 */
	protected String queueManagerName = null;
	
	/**
	 * 通道名称
	 */
	protected String channelName = null;
	
	/**
	 * 字符集编码
	 */
	protected int ccsid = 1381;
	
	/**
	 * 用户名
	 */
	protected String userName = null;
	
	/**
	 * 密码
	 */
	protected String password = null;
	
	/**
	 * 队列名称
	 */
	protected String queueName = null;
	
	/**
	 * 阻塞读
	 */
	protected boolean blockRead = false;

	/**
	 * 等待时间
	 * 默认一分钟
	 */
	protected int waitInterval = 60 * 1000;

	/**
	 * 依赖于连接池
	 */
	protected MQConnPool mqConnPool = null;
	
	/**
	 * 连接队列管理器
	 * @throws ConnectionException
	 */
	protected MQQueueManager connect() throws ConnectionException{
		
		MQEnvironment.hostname = this.hostName;
		MQEnvironment.port = this.port;
		MQEnvironment.channel = this.channelName;
		MQEnvironment.CCSID = this.ccsid;
		
		if(!StringUtils.isEmpty(this.userName)){
			MQEnvironment.userID = this.userName;
		}
		
		if(!StringUtils.isEmpty(this.password)){
			MQEnvironment.password = this.password;
		}
		
		try {
			MQQueueManager qMgr = new MQQueueManager(this.queueManagerName);
			return qMgr;
			
		} catch (MQException e) {
			ConnectionException ce = new ConnectionException("Connect QueueManager " + queueManagerName + " error!");
			ce.initCause(e);
			
			throw ce;
		}
	}
	
	/**
	 * 关闭连接
	 * @throws ConnectionException
	 */
	protected void close(MQQueueManager qMgr) throws ConnectionException{
		
		try {
			qMgr.disconnect();
			//qMgr.close();
		} catch (MQException e) {
			ConnectionException ce = new ConnectionException("close QueueManager " + queueManagerName + " error!");
			ce.initCause(e);
			
			throw ce;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#msgDepth()
	 */
	public int msgDepth() throws AccessQueueException, ConnectionException{
		
		MQQueueManager qMgr = null;
		MQQueue queue = null;
		
		try {
			
			qMgr = this.connect();
			
			int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_INQUIRE;
			queue = qMgr.accessQueue(queueName, openOptions);
			
			int msgDepth = queue.getCurrentDepth();
			
			return msgDepth;
			
		} catch (MQException e) {
			
			AccessQueueException aqe = new AccessQueueException("access queue error!");
			aqe.initCause(e);
			
			throw aqe;

		}finally{
			try {
				if(queue != null){
					queue.close();
				}
				
				if(qMgr != null){
					this.close(qMgr);
				}
			} catch (MQException e) {
				AccessQueueException aqe = new AccessQueueException("close queue error!");
				aqe.initCause(e);
				
				throw aqe;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#displayMsg()
	 */
	public byte[] displayMsg() throws IOException, AccessQueueException, ConnectionException{
		
		//浏览方式打开
		int openOptions = MQConstants.MQOO_BROWSE;
		
		//浏览方式获取
		MQGetMessageOptions getMessageOptions = new MQGetMessageOptions();
		getMessageOptions.options |= MQConstants.MQGMO_BROWSE_FIRST;
		
		if(blockRead){
			getMessageOptions.options |= MQConstants.MQGMO_WAIT;
			getMessageOptions.waitInterval = waitInterval;
		}
		
		//获取消息
		byte[] msgContent = this.getMsg(openOptions, getMessageOptions);
		return msgContent;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#readMsg()
	 */
	public byte[] readMsg() throws AccessQueueException, IOException, ConnectionException{
		
		//读取方式打开
		int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF;
		
		//读取方式获取
		MQGetMessageOptions getMessageOptions = new MQGetMessageOptions();
		if(blockRead){
			getMessageOptions.options |= MQConstants.MQGMO_WAIT;
			getMessageOptions.waitInterval = waitInterval;
		}
		
		//获取消息
		byte[] msgContent = this.getMsg(openOptions, getMessageOptions);
		return msgContent;
	}
		
	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#removeMsg()
	 */
	public byte[] removeMsg() throws AccessQueueException, IOException, ConnectionException{
		return readMsg();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#sendMsg(byte[])
	 */
	public void sendMsg(byte[] msg) throws IOException, AccessQueueException, ConnectionException{
		
		if(msg == null || msg.length <= 0){
			return;
		}
		
		MQQueueManager qMgr = null;
		MQQueue queue = null;
		try {
			
			qMgr = this.connect();
			
			//获取要访问的Q
			int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_OUTPUT;
			queue = qMgr.accessQueue(queueName, openOptions);
			
			//定义消息
			MQMessage message = new MQMessage();
			message.write(msg);
			
			//发送消息
			queue.put(message);
						
		} catch (MQException e) {
			
			AccessQueueException aqe = new AccessQueueException("access queue error!");
			aqe.initCause(e);
			
			throw aqe;
			
		}finally{
			try {
				if(queue != null){
					queue.close();
				}
				
				if(qMgr != null){
					this.close(qMgr);
				}
				
			} catch (MQException e) {
				AccessQueueException aqe = new AccessQueueException("close queue error!");
				aqe.initCause(e);
				
				throw aqe;
			}
		}
	}
	
	/**
	 * 获取消息内容
	 * @param openOptions 打开队列方式
	 * @param getMessageOptions 获取消息方式
	 * @return 消息内容
	 * @throws AccessQueueException
	 * @throws IOException
	 * @throws ConnectionException 
	 */
	public byte[] getMsg(int openOptions, MQGetMessageOptions getMessageOptions) throws AccessQueueException, IOException, ConnectionException{
		
		MQQueueManager qMgr = null;
		MQQueue queue = null;
		try {
			
			qMgr = this.connect();
			
			//获取要访问的Q
			queue = qMgr.accessQueue(queueName, openOptions);
			
			//定义消息
			MQMessage message = new MQMessage();
			
			//获取消息
			queue.get(message, getMessageOptions);

			//获取内容
			byte[] msgContent = null;
			
			int msgLength = message.getMessageLength();
			if( msgLength > 0){
				msgContent = new byte[msgLength];
				message.readFully(msgContent);
			}
			
			//返回消息内容
			return msgContent;
			
		} catch (MQException e) {
			
			AccessQueueException aqe = new AccessQueueException("access queue error!");
			aqe.initCause(e);
			
			throw aqe;
			
		}finally{
			try {
				if(queue != null){
					queue.close();
				}
				
				if(qMgr != null){
					this.close(qMgr);
				}
				
			} catch (MQException e) {
				AccessQueueException aqe = new AccessQueueException("close queue error!");
				aqe.initCause(e);
				
				throw aqe;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#getQueueName()
	 */
	public String getQueueName() {
		return queueName;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#setQueueName(java.lang.String)
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#isBlockRead()
	 */
	public boolean isBlockRead() {
		return blockRead;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#setBlockRead(boolean)
	 */
	public void setBlockRead(boolean blockRead) {
		this.blockRead = blockRead;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the queueManagerName
	 */
	public String getQueueManagerName() {
		return queueManagerName;
	}

	/**
	 * @param queueManagerName the queueManagerName to set
	 */
	public void setQueueManagerName(String queueManagerName) {
		this.queueManagerName = queueManagerName;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the ccsid
	 */
	public int getCcsid() {
		return ccsid;
	}

	/**
	 * @param ccsid the ccsid to set
	 */
	public void setCcsid(int ccsid) {
		this.ccsid = ccsid;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the waitInterval
	 */
	public int getWaitInterval() {
		return waitInterval;
	}

	/**
	 * @param waitInterval the waitInterval to set
	 */
	public void setWaitInterval(int waitInterval) {
		this.waitInterval = waitInterval;
	}

	/**
	 * @return the mqConnPool
	 */
	public MQConnPool getMqConnPool() {
		return mqConnPool;
	}

	/**
	 * @param mqConnPool the mqConnPool to set
	 */
	public void setMqConnPool(MQConnPool mqConnPool) {
		this.mqConnPool = mqConnPool;
	}
}
