/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.biz.service.decorater.transaction;

import com.bln.framework.mo.IMessageObject;

/**
 * ����������ķ���װ����
 */
public class RequireNewTransaction extends TransactionServiceBase{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IServiceDecorater#service(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject service(IMessageObject reqMo) throws Throwable {
				
		//ִ���µ�����
		IMessageObject respMo = transaction.requireNewTrans(service, reqMo);
		
		//������Ӧ����
		return respMo;
	}
}