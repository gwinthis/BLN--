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
 * 处理子表记录
 */
public class HandleChildRows {
	
	/**
	 * 要处理的子表
	 */
	private Map<String, List<IRow>> childRowsMap = new HashMap<String, List<IRow>>();

	/**
	 * 当前实体所使用的表结构
	 */
	private ITableStru tableStru = null;
	
	/**
	 * 状态实体类库
	 */
	private IEntityLib entityLib = null;
	
	/**
	 * 新建之前的记录
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
	 * 删除所请求的子表记录
	 * @param parentRow 主表记录
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws RefenceConstraintException 
	 * @throws PersistStaleEntityException 
	 */
	protected void deleteChildRows(IRow parentRow, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException{
		
		//校验参数
		if(childRowsMap == null || childRowsMap.isEmpty()){
			return;
		}
		
		//如果存在子表记录，删除子表记录
		for (Map.Entry<String, List<IRow>> childRowsEntry : childRowsMap.entrySet()){

			//判断是否为子表
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
	 * 新增子表记录
	 * @param parentRow 父表记录
	 * @throws InvalidRowException
	 * @throws SQLException
	 * @throws IOException
	 */
	protected void insertChildRows(IRow parentRow) throws InvalidRowException, SQLException, IOException{
		
		//校验参数
		if(childRowsMap == null || childRowsMap.isEmpty()){
			return;
		}
		
		//替换外键
		this.replaceChildsForeignKey(parentRow, false);
		
		//执行子表新建操作
		for (Map.Entry<String, List<IRow>> childRowsEntry : childRowsMap.entrySet()){

			//判断是否为子表
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
	 * 保存子表记录
	 * @param parentRow 父表记录
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
		
		//校验参数
		if(childRowsMap == null || childRowsMap.isEmpty()){
			return;
		}

		//执行子表新建操作
		for (Map.Entry<String, List<IRow>> childRowsEntry : childRowsMap.entrySet()){

			//判断是否为子表
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
	 * 替换外键
	 * @param row 主表记录
	 * @param checkChild 是否校验记录为子表记录
	 */
	protected void replaceChildsForeignKey(IRow row, boolean checkChild){
		
		//校验参数
		if(childRowsMap == null || childRowsMap.isEmpty()){
			return;
		}
		
		
		//如果存在子表记录，更新子表外键
		for (Map.Entry<String, List<IRow>> childRowsEntry : childRowsMap.entrySet()){

			//判断是否为子表
			String childTableName = childRowsEntry.getKey();
			if(!TableStruUtil.isChildTable(tableStru, childTableName)){
				continue;
			}
			
			//获取外键
			IForeignKey foreignKey = TableStruUtil.findForeignKey(tableStru, childTableName);

			//获取关联关系
			IPair<String, String>[] refers = foreignKey.getRefColNames();
			
			//处理子表外键
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
