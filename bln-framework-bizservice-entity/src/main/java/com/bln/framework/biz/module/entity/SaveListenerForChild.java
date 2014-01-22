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
 * Ϊ�����ӱ�ı��������
 */
public class SaveListenerForChild extends SaveListenerBase implements ISaveListener{
	
	/**
	 * �ӱ������
	 */
	HandleChildRows handleChildRows = null;
	
	/**
	 * ���캯��
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
		
		//�����½�֮ǰ�ļ�¼
		IRow beforeInsertRow = new Row();
		beforeInsertRow.importRow(row);
		
		handleChildRows.setBeforeInsertRow(beforeInsertRow);
		
		//���ؼ�¼
		row = super.beforeInsert(row);
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#afterInsert(com.bln.framework.persist.row.IRow)
	 */
	public IRow afterInsert(IRow row) {
		
		//�滻���
		handleChildRows.replaceChildsForeignKey(row, true);
		
		//���ؼ�¼
		row = super.afterInsert(row);
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.listener.ISaveListener#onDelete(com.bln.framework.persist.row.IRow)
	 */
	public IRow beforeDelete(IRow row, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException {
		
		//ɾ���ӱ��¼
		handleChildRows.deleteChildRows(row, deleteChildRowOnDelete);
		
		//������
		return row;
	}
}
