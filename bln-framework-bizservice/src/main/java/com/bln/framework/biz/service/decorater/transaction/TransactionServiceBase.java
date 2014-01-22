/**
 * @author gengw
 * Created on Mar 31, 2012
 */
package com.bln.framework.biz.service.decorater.transaction;

import com.bln.framework.biz.service.decorater.ServiceDecoraterBase;
import com.bln.framework.transaction.db.ITransaction;

/**
 * ������ķ���װ��������
 */
public abstract class TransactionServiceBase extends ServiceDecoraterBase{
	
	/**
	 * ���������
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
