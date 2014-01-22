/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.persist.session.executor.row.table.update.listener;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.tablestru.ITableStru;

/**
 * 表更新执行器的监听器接口
 */
public interface ITableUpdateExecutorListener {
	
	/**
	 * 插入记录之前所触发的事件
	 * @param tableStru 表结构
	 * @param row 要插入的记录对象
	 * @return 记录对象
	 */
	public IRow beforeRowInsert(ITableStru tableStru, IRow row);
	
	/**
	 * 插入记录完成所触发的事件
	 * @param tableStru 表结构
	 * @param row 要插入的记录对象
	 * @return 记录对象
	 */
	public IRow rowInserted(ITableStru tableStru, IRow row);
	
	/**
	 * 更新记录之前所触发的事件
	 * @param tableStru 表结构
	 * @param row 要更新的记录对象
	 * @return 记录对象
	 */
	public IRow beforeRowUpdate(ITableStru tableStru, IRow row);
	
	/**
	 * 更新记录完成所触发的事件
	 * @param tableStru 表结构
	 * @param row 要更新的记录对象
	 * @return 记录对象
	 */
	public IRow rowUpdated(ITableStru tableStru, IRow row);

	/**
	 * 根据条件更新之前所触发的事件
	 * @param tableStru 表结构
	 * @param row 要更新的值
	 * @param conClause 需要更新的记录条件
	 * @return 要更新的值
	 */
	public IRow beforeUpdateByCondition(ITableStru tableStru, IRow row, IConditionClause conClause);
	
	/**
	 * 根据条件更新完成所触发的事件
	 * @param tableStru 表结构
	 * @param row 要更新的值
	 * @param conClause 需要更新的记录条件
	 * @param updatedCount 已更新记录的条数
	 * @return 要更新的值
	 */
	public IRow updatedByCondition(ITableStru tableStru, IRow row, IConditionClause conClause, int updatedCount);
	
	/**
	 * 删除记录之前所触发的事件
	 * @param tableStru 表结构
	 * @param row 要删除的记录对象
	 * @return 记录对象
	 */
	public IRow beforeRowDelete(ITableStru tableStru, IRow row);
	
	/**
	 * 删除记录完成所触发的事件
	 * @param tableStru 表结构
	 * @param row 要更新的记录对象
	 * @return 记录对象
	 */
	public IRow rowDeleted(ITableStru tableStru, IRow row);
	
	/**
	 * 根据条件删除记录之前所触发的事件
	 * @param tableStru 表结构
	 * @param conClause 需要删除的记录的条件
	 * @return 删除记录的条件
	 */
	public IConditionClause beforeDeleteByCondition(ITableStru tableStru, IConditionClause conClause);
	
	/**
	 * 根据条件删除记录完成所触发的事件
	 * @param tableStru 表结构
	 * @param conClause 需要删除的记录的条件
	 * @param deletedCount 已删除记录的条数
	 */
	public void deletedByCondition(ITableStru tableStru, IConditionClause conClause, int deletedCount);

	
}
