/**
 * @author gengw
 * Created on Jan 18, 2013
 */
package com.bln.framework.net.mq.ibm;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.net.mq.dao.ITransactionQueueDao;
import com.bln.framework.net.mq.exception.AccessQueueException;
import com.bln.framework.net.mq.exception.ConnectionException;
import com.bln.framework.net.mq.exception.MQTransException;
import com.bln.framework.net.mq.ibm.connpool.MQConnPool;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

/**
 * ���з��ʶ���
 */
public class TransactionQueueDao implements ITransactionQueueDao {

	/**
	 * ��������(IP)
	 */
	protected String hostName = null;
	
	/**
	 * MQ�˿�
	 */
	protected int port = 1414;
	
	/**
	 * ���й���������
	 */
	protected String queueManagerName = null;
	
	/**
	 * ͨ������
	 */
	protected String channelName = null;
	
	/**
	 * �ַ�������
	 */
	protected int ccsid = 1381;
	
	/**
	 * �û���
	 */
	protected String userName = null;
	
	/**
	 * ����
	 */
	protected String password = null;
	
	/**
	 * ��������
	 */
	protected String queueName = null;
	
	/**
	 * ������
	 */
	protected boolean blockRead = false;

	/**
	 * �ȴ�ʱ��
	 * Ĭ��һ����
	 */
	protected int waitInterval = 120 * 1000;

	/**
	 * ���ӹ�����
	 */
	public MQQueueManager qMgr = null;
	
	/**
	 * ���������ӳ�
	 */
	protected MQConnPool mqConnPool = null;
	
	/**
	 * ���Ӷ��й�����
	 * @throws ConnectionException
	 */
	public void connect() throws ConnectionException{
		
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
			
			// create a properties Hashtable
			//Properties properties = new Properties();

//			// set the THREAD_AFFINITY_PROPERTY to TRUE
//			properties.put(MQC.THREAD_AFFINITY_PROPERTY, new Boolean(true));
//
//			// add other properties if required
//			properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_BINDINGS);
			
			this.qMgr = new MQQueueManager(this.queueManagerName);
		} catch (MQException e) {
			ConnectionException ce = new ConnectionException("Connect QueueManager " + queueManagerName + " error!");
			ce.initCause(e);
			
			throw ce;
		}
	}
	
	/**
	 * �ر�����
	 * @throws ConnectionException
	 */
	public void disconnect() throws ConnectionException{
		
		try {
			if(qMgr != null){
				qMgr.disconnect();
			}
			//qMgr.close();
		} catch (MQException e) {
			ConnectionException ce = new ConnectionException("close QueueManager " + queueManagerName + " error!");
			ce.initCause(e);
			
			throw ce;
		}
	}
	
	/**
	 * ��������
	 * @throws MQTransException
	 */
	public void beginTransaction() throws MQTransException{
		try {
			qMgr.begin();
		} catch (MQException e) {
			
			MQTransException mte = new MQTransException("begin mq transaction error!");
			mte.initCause(e);
			
			throw mte;
		}
	}

	/**
	 * �ύ����
	 * @throws MQTransException
	 */
	public void commit() throws MQTransException{
		try {
			if(qMgr != null){
				qMgr.commit();
			}
		} catch (MQException e) {
			
			MQTransException mte = new MQTransException("commit mq transaction error!");
			mte.initCause(e);
			
			throw mte;
		}
	}
	
	/**
	 * �ع�����
	 * @throws MQTransException
	 */
	public void rollback() throws MQTransException{
		try {
			if(qMgr != null){
				qMgr.backout();
			}
		} catch (MQException e) {
			
			MQTransException mte = new MQTransException("rollback mq transaction error!");
			mte.initCause(e);
			
			throw mte;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#msgDepth()
	 */
	public int msgDepth() throws AccessQueueException, ConnectionException{
		
		//MQQueueManager qMgr = null;
		MQQueue queue = null;
		
		try {
			
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
		
		//�����ʽ��
		int openOptions = MQConstants.MQOO_BROWSE;
		
		//�����ʽ��ȡ
		MQGetMessageOptions getMessageOptions = new MQGetMessageOptions();
		getMessageOptions.options |= MQConstants.MQGMO_BROWSE_FIRST;
		
		if(blockRead){
			getMessageOptions.options |= MQConstants.MQGMO_WAIT;
			getMessageOptions.waitInterval = waitInterval;
		}
		
		//��ȡ��Ϣ
		byte[] msgContent = this.getMsg(openOptions, getMessageOptions);
		return msgContent;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.IQueueDao#readMsg()
	 */
	public byte[] readMsg() throws AccessQueueException, IOException, ConnectionException{
		
		//��ȡ��ʽ��
		int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQGMO_SYNCPOINT;
		
		//��ȡ��ʽ��ȡ
		MQGetMessageOptions getMessageOptions = new MQGetMessageOptions();
		if(blockRead){
			getMessageOptions.options |= MQConstants.MQGMO_WAIT;
			getMessageOptions.waitInterval = waitInterval;
		}
		
		//��ȡ��Ϣ
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

		MQQueue queue = null;
		try {
			
			//��ȡҪ���ʵ�Q
			int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_OUTPUT;
			queue = qMgr.accessQueue(queueName, openOptions);
			
			//������Ϣ
			MQMessage message = new MQMessage();
			message.write(msg);
			
			//������Ϣ
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
				
			} catch (MQException e) {
				AccessQueueException aqe = new AccessQueueException("close queue error!");
				aqe.initCause(e);
				
				throw aqe;
			}
		}
	}
	
	/**
	 * ��ȡ��Ϣ����
	 * @param openOptions �򿪶��з�ʽ
	 * @param getMessageOptions ��ȡ��Ϣ��ʽ
	 * @return ��Ϣ����
	 * @throws AccessQueueException
	 * @throws IOException
	 * @throws ConnectionException 
	 */
	public byte[] getMsg(int openOptions, MQGetMessageOptions getMessageOptions) throws AccessQueueException, IOException, ConnectionException{
		
		MQQueue queue = null;
		try {
			
			//��ȡҪ���ʵ�Q
			queue = qMgr.accessQueue(queueName, openOptions);
			
			//������Ϣ
			MQMessage message = new MQMessage();
			
			//��ȡ��Ϣ
			queue.get(message, getMessageOptions);

			//��ȡ����
			byte[] msgContent = null;
			
			int msgLength = message.getMessageLength();
			if( msgLength > 0){
				msgContent = new byte[msgLength];
				message.readFully(msgContent);
			}
			
			//������Ϣ����
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
