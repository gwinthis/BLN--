/**
 * @author gengw
 * Created on Jan 19, 2013
 */
package com.bln.framework.net.mq;

/**
 * ��Ϣ������
 */
public interface IMsgConsumer {
	
	/**
	 * ������Ϣ
	 * @throws Throwable
	 */
	public void consume(byte[] msg) throws Throwable;

}
