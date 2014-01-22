/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.edi.edge.sender;

import com.bln.framework.edi.edge.IEdiEdge;
import com.bln.framework.mo.IMessageObject;

/**
 * EDI发送类
 */
public interface IEdiSender extends IEdiEdge{
	
	/**
	 * 同步发送报文
	 * @param reqMo 要发送的数据
	 * @return 返回响应信息,组装成消息对象
	 * @throws Throwable 发送时所抛出的异常
	 */
	public abstract IMessageObject send(IMessageObject reqMo) throws Throwable;
}
