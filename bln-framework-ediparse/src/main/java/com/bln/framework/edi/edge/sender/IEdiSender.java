/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.edi.edge.sender;

import com.bln.framework.edi.edge.IEdiEdge;
import com.bln.framework.mo.IMessageObject;

/**
 * EDI������
 */
public interface IEdiSender extends IEdiEdge{
	
	/**
	 * ͬ�����ͱ���
	 * @param reqMo Ҫ���͵�����
	 * @return ������Ӧ��Ϣ,��װ����Ϣ����
	 * @throws Throwable ����ʱ���׳����쳣
	 */
	public abstract IMessageObject send(IMessageObject reqMo) throws Throwable;
}
