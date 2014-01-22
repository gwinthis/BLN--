/**
 * @author gengw
 * Created on May 16, 2012
 */
package com.bln.framework.biz.module.entity.strategy.rowstatus;

import com.bln.framework.persist.row.IRow;

/**
 * ��״̬����
 */
public class IdentifyRowStatusStrategy implements IIdentifyRowStatusStrategy {
	
	/**
	 * ��״̬�ֶ�
	 */
	protected String rowStatusFiled = "ROWSTATE";

	/**
	 * �½�����״̬
	 */
	protected String rowStatusNew = "added";

	/**
	 * �޸ĵ���״̬
	 */
	protected String rowStatusModified = "modified";

	/**
	 * ɾ������״̬
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
