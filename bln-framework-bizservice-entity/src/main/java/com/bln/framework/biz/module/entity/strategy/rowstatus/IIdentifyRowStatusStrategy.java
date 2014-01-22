package com.bln.framework.biz.module.entity.strategy.rowstatus;

import com.bln.framework.persist.row.IRow;

public interface IIdentifyRowStatusStrategy {

	/**
	 * �Ƿ��½�״̬
	 * @param row ��¼
	 * @return �Ƿ��½�״̬
	 */
	public abstract boolean isNew(IRow row);

	/**
	 * �Ƿ��޸�״̬
	 * @param row ��¼
	 * @return �Ƿ��½�״̬
	 */
	public abstract boolean isModified(IRow row);

	/**
	 * �Ƿ��½�״̬
	 * @param row ��¼
	 * @return �Ƿ��½�״̬
	 */
	public abstract boolean isDeleted(IRow row);

}