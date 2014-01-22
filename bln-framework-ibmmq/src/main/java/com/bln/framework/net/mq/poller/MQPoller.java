/**
 * @author gengw
 * Created on Jan 17, 2013
 */
package com.bln.framework.net.mq.poller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.net.mq.IMsgConsumer;
import com.bln.framework.net.mq.dao.IQueueDao;

/**
 * MQ��Ϣ��ȡ��<br/><br/>
 * ����queue���ʶ������Ϣ�����ߣ�����pollMsg������ȡ��Ϣ��<br/><br/>
 */
public class MQPoller implements IMQPoller, Runnable{

	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(MQPoller.class);
	
	/**
	 * queue���ʶ���
	 */
	protected IQueueDao queueDao = null;

	/**
	 * ��ȡ�ȴ�ʱ��
	 */
	protected int waitInterval = 3 * 60 * 60 * 1000;
	
	/**
	 * ��Ϣ������
	 */
	protected IMsgConsumer msgConsumer = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.poller.IMQPoller#pollMsg()
	 */
	public void pollMsg(){
		
		queueDao.setBlockRead(true);
		queueDao.setWaitInterval(waitInterval);
		
		log.debug("start polling msg from mq ....");
		for(;;){
			log.debug("poll msg from mq ....");
			try {
				byte[] msg = queueDao.readMsg();
				msgConsumer.consume(msg);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			
			if(Thread.currentThread().isInterrupted()){
				break;
			}
		}
		log.debug("stoped polling msg from mq ....");
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		this.pollMsg();
	}
	
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.poller.IMQPoller#getQueueDao()
	 */
	public IQueueDao getQueueDao() {
		return queueDao;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.poller.IMQPoller#setQueueDao(com.bln.framework.net.mq.dao.IQueueDao)
	 */
	public void setQueueDao(IQueueDao queueDao) {
		this.queueDao = queueDao;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.poller.IMQPoller#getMsgConsumer()
	 */
	public IMsgConsumer getMsgConsumer() {
		return msgConsumer;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.poller.IMQPoller#setMsgConsumer(com.bln.framework.net.mq.IMsgConsumer)
	 */
	public void setMsgConsumer(IMsgConsumer msgConsumer) {
		this.msgConsumer = msgConsumer;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.poller.IMQPoller#getWaitInterval()
	 */
	public int getWaitInterval() {
		return waitInterval;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.net.mq.poller.IMQPoller#setWaitInterval(int)
	 */
	public void setWaitInterval(int waitInterval) {
		this.waitInterval = waitInterval;
	}
}


