/**
 * @author gengw
 * Created on May 16, 2012
 */
package com.bln.framework.biz.module.entity.listener;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import com.bln.framework.biz.module.entity.exception.RefenceConstraintException;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;

/**
 * ±£´æ¼àÌýÆ÷»ùÀà
 */
public abstract class SaveListenerBase implements ISaveListener{

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#onDelete(com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeDelete(IRow row, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException {
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#onInsert(com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeInsert(IRow row) {
		
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#onUpdate(com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeUpdate(IRow row) {
		
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#afterDelete(com.bln.framework.persist.row.IRow)
	 */
	public IRow afterDelete(IRow row) {
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#afterInsert(com.bln.framework.persist.row.IRow)
	 */
	public IRow afterInsert(IRow row) {
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#afterUpdate(com.bln.framework.persist.row.IRow)
	 */
	public IRow afterUpdate(IRow row) {
		return row;
	}

}
