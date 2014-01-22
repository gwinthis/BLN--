package com.bln.framework.edi.edge.rcver;

import com.bln.framework.edi.edge.IEdiEdge;

public interface IEdiRcver extends IEdiEdge{

	/**
	 * ����EDIԭʼ���ݣ�������Ϣ����
	 * @param reqEdiData EDIԭʼ����
	 * @param originalRequestObject ԭʼ�����������HTTPServletRequest
	 * @return IMessageObject ���ɵ���Ϣ����
	 * @throws Throwable ����EDIʱ�׳����쳣
	 */
	public byte[] receive(byte[] reqEdiData, Object originalRequestObject) throws Throwable;

}