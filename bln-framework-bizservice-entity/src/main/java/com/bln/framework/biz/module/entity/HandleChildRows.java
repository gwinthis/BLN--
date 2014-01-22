/**
 * @author gengw
 * Created on Jan 15, 2013
 */
package com.bln.framework.biz.module.entity;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bln.framework.biz.module.entity.exception.InvalidRowException;
import com.bln.framework.biz.module.entity.exception.RefenceConstraintException;
import com.bln.framework.biz.module.entity.lib.IEntityLib;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.component.fk.IForeignKey;
import com.bln.framework.persist.tablestru.util.TableStruUtil;
import com.bln.framework.util.pair.IPair;

/**
 * �����ӱ��¼
 */
public class HandleChildRows {
	
	/**
	 * Ҫ������ӱ�
	 */
	private Map<String, List<IRow>> childRowsMap = new HashMap<String, List<IRow>>();

	/**
	 * ��ǰʵ����ʹ�õı�ṹ
	 */
	private ITableStru tableStru = null;
	
	/**
	 * ״̬ʵ�����
	 */
	private IEntityLib entityLib = null;
	
	/**
	 * �½�֮ǰ�ļ�¼
	 */
	private IRow beforeInsertRow = null;
	
	/**
	 * @param childRowsMap
	 * @param tableStru
	 * @param entityLib
	 */
	public HandleChildRows(Map<String, List<IRow>> childRowsMap,
			ITableStru tableStru, IEntityLib entityLib) {
		super();
		this.childRowsMap = childRowsMap;
		this.tableStru = tableStru;
		this.entityLib = entityLib;
	}

	/**
	 * ɾ����������ӱ��¼
	 * @param parentRow �����¼
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws RefenceConstraintException 
	 * @throws PersistStaleEntityException 
	 */
	protected void deleteChildRows(IRow parentRow, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException{
		
		//У�����
		if(childRowsMap == null || childRowsMap.isEmpty()){
			return;
		}
		
		//��������ӱ��¼��ɾ���ӱ��¼
		for (Map.Entry<String, List<IRow>> childRowsEntry : childRowsMap.entrySet()){

			//�ж��Ƿ�Ϊ�ӱ�
			String childTableName = childRowsEntry.getKey();
			if(!TableStruUtil.isChildTable(tableStru, childTableName)){
				continue;
			}
			
			IEntityStatelessModule childEntity = entityLib.getInstance(childTableName);
			List<IRow> childRows = childRowsEntry.getValue();
			if(childRows != null && childRows.size() > 0){
				for ( int i = childRows.size() - 1; i >= 0; i--){
					IRow childRow = childRows.get(i);
					childEntity.delete(childRow, deleteChildRowOnDelete);
					
					childRows.remove(i);
				}
			}
		}
	}
	
	/**
	 * �����ӱ��¼
	 * @param parentRow �����¼
	 * @throws InvalidRowException
	 * @throws SQLException
	 * @throws IOException
	 */
	protected void insertChildRows(IRow parentRow) throws InvalidRowException, SQLException, IOException{
		
		//У�����
		if(childRowsMap == null || childRowsMap.isEmpty()){
			return;
		}
		
		//�滻���
		this.replaceChildsForeignKey(parentRow, false);
		
		//ִ���ӱ��½�����
		for (Map.Entry<String, List<IRow>> childRowsEntry : childRowsMap.entrySet()){

			//�ж��Ƿ�Ϊ�ӱ�
			String childTableName = childRowsEntry.getKey();
			if(!TableStruUtil.isChildTable(tableStru, childTableName)){
				continue;
			}
			
			IEntityStatelessModule childEntity = entityLib.getInstance(childRowsEntry.getKey());
			List<IRow> childRows = childRowsEntry.getValue();
			childEntity.insertRows(childRows);
		}
	}
	
	/**
	 * �����ӱ��¼
	 * @param parentRow �����¼
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws InvalidRowException 
	 * @throws RefenceConstraintException 
	 * @throws PersistStaleEntityException 
	 * @throws InvalidRowException
	 * @throws SQLException
	 * @throws IOException
	 */
	protected void saveChildRows(IRow parentRow) throws PersistStaleEntityException, RefenceConstraintException, InvalidRowException, SQLException, IOException, ParseException{
		
		//У�����
		if(childRowsMap == null || childRowsMap.isEmpty()){
			return;
		}

		//ִ���ӱ��½�����
		for (Map.Entry<String, List<IRow>> childRowsEntry : childRowsMap.entrySet()){

			//�ж��Ƿ�Ϊ�ӱ�
			String childTableName = childRowsEntry.getKey();
			if(!TableStruUtil.isChildTable(tableStru, childTableName)){
				continue;
			}
			
			IEntityStatelessModule childEntity = entityLib.getInstance(childRowsEntry.getKey());
			List<IRow> childRows = childRowsEntry.getValue();
			childEntity.save(childRows, null, true);
		}
	}
	
	/**
	 * �滻���
	 * @param row �����¼
	 * @param checkChild �Ƿ�У���¼Ϊ�ӱ��¼
	 */
	protected void replaceChildsForeignKey(IRow row, boolean checkChild){
		
		//У�����
		if(childRowsMap == null || childRowsMap.isEmpty()){
			return;
		}
		
		
		//��������ӱ��¼�������ӱ����
		for (Map.Entry<String, List<IRow>> childRowsEntry : childRowsMap.entrySet()){

			//�ж��Ƿ�Ϊ�ӱ�
			String childTableName = childRowsEntry.getKey();
			if(!TableStruUtil.isChildTable(tableStru, childTableName)){
				continue;
			}
			
			//��ȡ���
			IForeignKey foreignKey = TableStruUtil.findForeignKey(tableStru, childTableName);

			//��ȡ������ϵ
			IPair<String, String>[] refers = foreignKey.getRefColNames();
			
			//�����ӱ����
			List<IRow> childRows = childRowsEntry.getValue();
			for ( IRow childRow : childRows){
				
				for ( IPair<String, String> refer : refers ){
					childRow.setValue(refer.getLeft(), row.getValue(refer.getRight()));
				}
			}
		}
		
	}

	/**
	 * @return the beforeInsertRow
	 */
	public IRow getBeforeInsertRow() {
		return beforeInsertRow;
	}

	/**
	 * @param beforeInsertRow the beforeInsertRow to set
	 */
	public void setBeforeInsertRow(IRow beforeInsertRow) {
		this.beforeInsertRow = beforeInsertRow;
	}

}
