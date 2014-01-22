package com.bln.framework.edi.edge.rcver;

import com.bln.framework.edi.edge.IEdiEdge;

public interface IEdiRcver extends IEdiEdge{

	/**
	 * 接收EDI原始数据，生成消息对象
	 * @param reqEdiData EDI原始数据
	 * @param originalRequestObject 原始请求对象，例如HTTPServletRequest
	 * @return IMessageObject 生成的消息对象
	 * @throws Throwable 接收EDI时抛出的异常
	 */
	public byte[] receive(byte[] reqEdiData, Object originalRequestObject) throws Throwable;

}