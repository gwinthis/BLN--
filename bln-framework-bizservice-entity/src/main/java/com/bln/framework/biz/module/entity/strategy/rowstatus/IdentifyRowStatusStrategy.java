/**
 * @author gengw
 * Created on May 16, 2012
 */
package com.bln.framework.biz.module.entity.strategy.rowstatus;

import com.bln.framework.persist.row.IRow;

/**
 * 行状态策略
 */
public class IdentifyRowStatusStrategy implements IIdentifyRowStatusStrategy {
	
	/**
	 * 行状态字段
	 */
	protected String rowStatusFiled = "ROWSTATE";

	/**
	 * 新建的行状态
	 */
	protected String rowStatusNew = "added";

	/**
	 * 修改的行状态
	 */
	protected String rowStatusModified = "modified";

	/**
	 * 删除的行状态
	 */
	protected String rowStatusDeleted = "deleted";

	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.util.IRowStatusStrategy#isNew(com.bln.framework.persist.row.IRow)
	 */
	public boolean isNew(IRow row){
		return rowStatusNew.equals(row.getValue(rowStatusFiled));
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.util.IRowStatusStrategy#isModified(com.bln.framework.persist.row.IRow)
	 */
	public boolean isModified(IRow row){
		return rowStatusModified.equals(row.getValue(rowStatusFiled));
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.util.IRowStatusStrategy#isDeleted(com.bln.framework.persist.row.IRow)
	 */
	public boolean isDeleted(IRow row){
		return rowStatusDeleted.equals(row.getValue(rowStatusFiled));
	}
}
