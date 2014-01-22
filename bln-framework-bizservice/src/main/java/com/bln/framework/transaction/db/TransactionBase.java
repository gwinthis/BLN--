/**
 * @author gengw
 * Created on Mar 31, 2012
 */
package com.bln.framework.transaction.db;

import com.bln.framework.base.BaseObj;
import com.bln.framework.biz.service.IService;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.transaction.exception.TransException;

/**
 * ���������
 */
public abstract class TransactionBase extends BaseObj implements ITransaction{

	/* (non-Javadoc)
	 * @see com.bln.framework.transaction.db.ITransaction#requireNewTrans(com.bln.framework.biz.service.IService, com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject requireNewTrans(IService serviceObj, IMessageObject reqMo) throws TransException {
		return this.transaction(serviceObj, reqMo);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.transaction.db.ITransaction#requireTrans(com.bln.framework.biz.service.IService, com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject requireTrans(IService serviceObj, IMessageObject reqMo) throws TransException {
		return this.transaction(serviceObj, reqMo);
	}
	
	/**
	 * Ĭ�ϵ�������ʽ
	 * @param serviceObj �������
	 * @param reqMo ������Ϣ����
	 * @return ��Ӧ��Ϣ����
	 * @throws TransException
	 */
	protected IMessageObject transaction(IService serviceObj, IMessageObject reqMo) throws TransException {
		
		IMessageObject respMo = null;
		try {
			respMo = serviceObj.execute(reqMo);
		} catch (Throwable e) {
			TransException te = new TransException("it found error in db tranaction!");
			te.initCause(e);
			throw te;
		}
		return respMo;
	}
}
