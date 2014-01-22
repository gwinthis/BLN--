/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.persist.session.executor.row.table.update.listener;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.tablestru.ITableStru;

/**
 * TableUpdateExecutor¼àÌýÆ÷»ùÀà
 */
public abstract class TableUpdateExecutorListenerBase implements ITableUpdateExecutorListener{

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener#beforeDeleteByCondition(com.bln.framework.persist.tablestru.ITableStru, com.bln.framework.persist.session.executor.material.condition.IConditionClause)
	 */
	public IConditionClause beforeDeleteByCondition(ITableStru tableStru, IConditionClause conClause) {
		return conClause;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener#beforeRowDelete(com.bln.framework.persist.tablestru.ITableStru, com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeRowDelete(ITableStru tableStru, IRow row) {
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener#beforeRowInsert(com.bln.framework.persist.tablestru.ITableStru, com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeRowInsert(ITableStru tableStru, IRow row) {
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener#beforeRowUpdate(com.bln.framework.persist.tablestru.ITableStru, com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeRowUpdate(ITableStru tableStru, IRow row) {
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener#beforeUpdateByCondition(com.bln.framework.persist.tablestru.ITableStru, com.bln.framework.persist.row.IRow, com.bln.framework.persist.session.executor.material.condition.IConditionClause)
	 */
	public IRow beforeUpdateByCondition(ITableStru tableStru, IRow row, IConditionClause conClause) {
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener#deletedByCondition(com.bln.framework.persist.tablestru.ITableStru, com.bln.framework.persist.session.executor.material.condition.IConditionClause, int)
	 */
	public void deletedByCondition(ITableStru tableStru, IConditionClause conClause, int deletedCount) {
		
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener#rowDeleted(com.bln.framework.persist.tablestru.ITableStru, com.bln.framework.persist.row.IRow)
	 */
	public IRow rowDeleted(ITableStru tableStru, IRow row) {
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener#rowInserted(com.bln.framework.persist.tablestru.ITableStru, com.bln.framework.persist.row.IRow)
	 */
	public IRow rowInserted(ITableStru tableStru, IRow row) {
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener#rowUpdated(com.bln.framework.persist.tablestru.ITableStru, com.bln.framework.persist.row.IRow)
	 */
	public IRow rowUpdated(ITableStru tableStru, IRow row) {
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener#updatedByCondition(com.bln.framework.persist.tablestru.ITableStru, com.bln.framework.persist.row.IRow, com.bln.framework.persist.session.executor.material.condition.IConditionClause, int)
	 */
	public IRow updatedByCondition(ITableStru tableStru, IRow row, IConditionClause conClause, int updatedCount) {
		return row;
	}

}
