/**
 * @author gengw
 * created on 2012-03-12
 */
package com.bln.framework.mo.builder;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.mo.buildable.IBuildMOable;

/**
 * MO构造器接口
 */
public interface IMoBuilder <M extends IMessageObject, B extends IBuildMOable> {

	/**
	 * 把字节数组构造成成消息对象
	 * @param bytes 接收到的数据，以字节数组存储
	 * @return 根据字节数组所生成的消息对象
	 * @throws Throwable 解析报文所抛出的异常
	 */
	public IMessageObject buildMo(byte[] bytes) throws Throwable;

	/**
	 * 获得已生成的MO
	 * @return
	 */
	public M getMo();

	/**
	 * 设置要生成的MO
	 * @param mo
	 */
	public void setMo(M mo);

	/**
	 * 获取可生成MO的对象
	 * @return
	 */
	public B getBuildMOable();

	/**
	 * 设置可从EDI生成MO的对象
	 * @param buildMoAble
	 */
	public void setBuildMOable(B buildMOable);
}
