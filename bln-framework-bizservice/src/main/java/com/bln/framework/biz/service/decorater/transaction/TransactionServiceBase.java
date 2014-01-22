/**
 * @author gengw
 * Created on Mar 31, 2012
 */
package com.bln.framework.biz.service.decorater.transaction;

import com.bln.framework.biz.service.decorater.ServiceDecoraterBase;
import com.bln.framework.transaction.db.ITransaction;

/**
 * 事务处理的服务装饰器基类
 */
public abstract class TransactionServiceBase extends ServiceDecoraterBase{
	
	/**
	 * 事务处理对象
	 */
	ITransaction transaction = null;

	/**
	 * @return the transaction
	 */
	public ITransaction getTransaction() {
		return transaction;
	}

	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(ITransaction transaction) {
		this.transaction = transaction;
	}

}
