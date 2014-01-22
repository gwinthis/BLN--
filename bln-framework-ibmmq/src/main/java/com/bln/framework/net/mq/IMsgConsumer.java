/**
 * @author gengw
 * Created on Jan 19, 2013
 */
package com.bln.framework.net.mq;

/**
 * 消息消费者
 */
public interface IMsgConsumer {
	
	/**
	 * 消费消息
	 * @throws Throwable
	 */
	public void consume(byte[] msg) throws Throwable;

}
