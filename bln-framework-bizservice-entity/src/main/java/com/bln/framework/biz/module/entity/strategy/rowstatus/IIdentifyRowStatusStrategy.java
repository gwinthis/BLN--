package com.bln.framework.biz.module.entity.strategy.rowstatus;

import com.bln.framework.persist.row.IRow;

public interface IIdentifyRowStatusStrategy {

	/**
	 * 是否新建状态
	 * @param row 记录
	 * @return 是否新建状态
	 */
	public abstract boolean isNew(IRow row);

	/**
	 * 是否修改状态
	 * @param row 记录
	 * @return 是否新建状态
	 */
	public abstract boolean isModified(IRow row);

	/**
	 * 是否新建状态
	 * @param row 记录
	 * @return 是否新建状态
	 */
	public abstract boolean isDeleted(IRow row);

}