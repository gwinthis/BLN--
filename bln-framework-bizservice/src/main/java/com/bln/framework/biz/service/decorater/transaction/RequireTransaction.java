/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.biz.service.decorater.transaction;

import com.bln.framework.mo.IMessageObject;

/**
 * 需要事务的服务装饰器
 */
public class RequireTransaction extends TransactionServiceBase{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.IServiceDecorater#service(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject service(IMessageObject reqMo) throws Throwable {
				
		//执行新的事务
		IMessageObject respMo = transaction.requireTrans(service, reqMo);
		
		//返回响应对象
		return respMo;
	}
}
