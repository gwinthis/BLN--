/**
 * @author gengw
 * Created on May 16, 2012
 */
package com.bln.framework.biz.module.entity;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.bln.framework.biz.module.entity.exception.RefenceConstraintException;
import com.bln.framework.biz.module.entity.lib.IEntityLib;
import com.bln.framework.biz.module.entity.listener.ISaveListener;
import com.bln.framework.biz.module.entity.listener.SaveListenerBase;
import com.bln.framework.biz.module.entity.strategy.rowstatus.IIdentifyRowStatusStrategy;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.row.Row;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.tablestru.ITableStru;

/**
 * 为处理子表的保存监听器
 */
public class SaveListenerForChild extends SaveListenerBase implements ISaveListener{
	
	/**
	 * 子表处理对象
	 */
	HandleChildRows handleChildRows = null;
	
	/**
	 * 构造函数
	 * @param childRowsMap
	 * @param tableStru
	 * @param entityLib
	 * @param identifyRowStatus
	 */
	public SaveListenerForChild(ITableStru tableStru, Map<String, List<IRow>> childRowsMap,IEntityLib entityLib, IIdentifyRowStatusStrategy identifyRowStatus) {
		super();
		handleChildRows = new HandleChildRows(childRowsMap, tableStru, entityLib);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#onInsert(com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeInsert(IRow row){
		
		//保存新建之前的记录
		IRow beforeInsertRow = new Row();
		beforeInsertRow.importRow(row);
		
		handleChildRows.setBeforeInsertRow(beforeInsertRow);
		
		//返回记录
		row = super.beforeInsert(row);
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#afterInsert(com.bln.framework.persist.row.IRow)
	 */
	public IRow afterInsert(IRow row) {
		
		//替换外键
		handleChildRows.replaceChildsForeignKey(row, true);
		
		//返回记录
		row = super.afterInsert(row);
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#onDelete(com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeDelete(IRow row, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException {
		
		//删除子表记录
		handleChildRows.deleteChildRows(row, deleteChildRowOnDelete);
		
		//返回行
		return row;
	}
}
