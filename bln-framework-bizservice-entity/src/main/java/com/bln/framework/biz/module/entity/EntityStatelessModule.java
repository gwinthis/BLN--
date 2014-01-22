/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.biz.module.entity;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bln.framework.biz.module.BizModuleBase;
import com.bln.framework.biz.module.entity.exception.InvalidRowException;
import com.bln.framework.biz.module.entity.exception.RefenceConstraintException;
import com.bln.framework.biz.module.entity.listener.IEntityListener;
import com.bln.framework.biz.module.entity.listener.ISaveListener;
import com.bln.framework.biz.module.entity.strategy.rowstatus.IIdentifyRowStatusStrategy;
import com.bln.framework.biz.module.entity.strategy.rowstatus.IdentifyRowStatusStrategy;
import com.bln.framework.biz.module.parsecondition.IParseCondition;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor;
import com.bln.framework.persist.session.executor.row.table.update.ITableUpdateExecutor;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.component.fk.IForeignKey;

/**
 * 无状态实体类模型
 */
public class EntityStatelessModule extends BizModuleBase implements IEntityStatelessModule {
	
	/**
	 * 表名
	 */
	protected String tableName = null;
	
	/**
	 * 当前实体所使用的表结构
	 */
	protected ITableStru tableStru = null;
		
	/**
	 * 解析条件对象
	 */
	protected IParseCondition parseCondition = null;
	
	/**
	 * 鉴别行状态的策略对象
	 */
	protected IIdentifyRowStatusStrategy identifyRowStatus = null;
	
	/**
	 * 在新建和更新记录时是否校验记录是否有效
	 */
	protected final boolean isValidateRowOnSave = false;
	
	/**
	 * 在删除记录时是否校验关联性
	 */
	protected final boolean isValidateRefenceOnDelete = false;
	
	/**
	 * 监听器
	 */
	protected IEntityListener listener = null;
	
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#query(com.bln.framework.persist.row.IRow, com.bln.framework.persist.session.executor.material.pagination.IPagination, java.lang.String)
	 */
	public List<IRow> query(IRow condition, IPagination pagination, String orderby) throws SQLException, IOException, ParseException{
		
		//1.获取查询条件
		IConditionClause conClause = parseQueryCondition(condition);

		//触发事件
		if(listener != null){
			listener.beforeQueryEvt(conClause, pagination, orderby);
		}
		
		//2.执行查询
		List<IRow> rows = this.executeQuery(tableName, conClause, pagination, orderby);
		
		//触发事件
		if(listener != null){
			rows = listener.afterQueryEvt(rows);
		}
		
		//3.返回结果
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#queryById(com.bln.framework.persist.row.IRow)
	 */
	public IRow queryById(IRow condition) throws ParseException, SQLException, IOException{
		
		//1.根据主键查询记录
		List<IRow> rows = this.queryByIdForList(condition);
		
		//2.获取第一行记录
		IRow row = null;
		if(rows != null && rows.size() > 0){
			row = rows.get(0);
		}
		
		//3.返回记录
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#queryByIdWithChilds(com.bln.framework.persist.row.IRow, java.lang.String[])
	 */
	public Map<String, List<IRow>> queryByIdWithChilds(IRow condition, String[] childTableNames) throws SQLException, IOException, ParseException{
		
		//1.获取主表记录
		List<IRow> parentRows = this.queryByIdForList(condition);
		
		IRow parentRow = null;
		if(parentRows != null && parentRows.size() > 0){
			parentRow = parentRows.get(0);
		}
		
		if(parentRow == null){
			return null;
		}
		
		//2.获取子表结果集
		Map<String,List<IRow>> resultMap = this.queryChilds(parentRow, childTableNames);
		
		//3.添加记录
		if(resultMap == null){
			resultMap = new HashMap<String,List<IRow>>();
		}
		resultMap.put(this.tableStru.getTableName(), parentRows);
		
		//4.返回结果Map
		return resultMap;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#queryChilds(com.bln.framework.persist.row.IRow, java.lang.String[])
	 */
	public Map<String, List<IRow>> queryChilds(IRow row, String[] childTableNames) throws SQLException, IOException, ParseException{
		
		Map<String,List<IRow>> resultMap = null;
		if(childTableNames != null && childTableNames.length > 0){
			
			resultMap = new HashMap<String,List<IRow>>();
			for ( int i = 0, n = childTableNames.length; i < n; i++){
				
				//获取子表条件
				String childTableName = childTableNames[i];
				IConditionClause conClause = parseCondition.parseChildReferCondition(row, tableStru, childTableName);
				
				//执行查询
				List<IRow> childRows = this.executeChildTableQuery(childTableName, conClause);
				
				//添加到返回结果中
				resultMap.put(childTableName, childRows);
			}
		}
		
		return resultMap;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#save(java.util.List, java.util.Map)
	 */
	public Map<String, List<IRow>> save(List<IRow> rows, Map<String, List<IRow>> childRowsMap, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, InvalidRowException, SQLException, IOException, ParseException{
		
		//1.更新主表记录
		ISaveListener saveListener = new SaveListenerForChild(this.tableStru, childRowsMap, entityLib, identifyRowStatus);
		this.save(rows, saveListener, deleteChildRowOnDelete);
		
		//2.保存子表记录
		if(childRowsMap == null || childRowsMap.isEmpty()){
			childRowsMap = new HashMap<String, List<IRow>>();
		}else{
			for (Map.Entry<String, List<IRow>> childEntry : childRowsMap.entrySet()){
				IEntityStatelessModule entity = entityLib.getInstance(childEntry.getKey());
				entity.save(childEntry.getValue(), null, deleteChildRowOnDelete);
			}
		}
		
		//添加主表记录
		childRowsMap.put(this.tableName, rows);
		
		//返回结果集
		return childRowsMap;
		
	}
	
	/**
	 * 保存多条记录
	 * @param rows
	 * @param saveListener
	 * @return
	 * @throws PersistStaleEntityException
	 * @throws RefenceConstraintException
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 * @throws InvalidRowException
	 */
	protected List<IRow> save(List<IRow> rows, ISaveListener saveListener, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException, InvalidRowException{
		
		//校验参数
		if(rows == null || rows.size() <= 0){
			return rows;
		}
		
		//1.删除记录
		for (int i = rows.size() - 1; i >= 0; i--){
			IRow row = rows.get(i);
			if (identifyRowStatus.isDeleted(row)){
				row = saveListener == null ? row : saveListener.beforeDelete(row, deleteChildRowOnDelete);
				this.delete(row, deleteChildRowOnDelete);
				row = saveListener == null ? row : saveListener.afterDelete(row);
				
				rows.remove(i);
			}
		}
		
		//2.新增或更新记录
		for ( IRow row : rows){
			row = this.save(row, saveListener, deleteChildRowOnDelete);
		}
		
		//3.返回记录
		return rows;
	}

	/**
	 * 保存单条记录
	 * @param row
	 * @param saveListener
	 * @return
	 * @throws PersistStaleEntityException
	 * @throws InvalidRowException
	 * @throws SQLException
	 * @throws IOException
	 * @throws RefenceConstraintException
	 * @throws ParseException
	 */
	protected IRow save(IRow row, ISaveListener saveListener, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, InvalidRowException, SQLException, IOException, RefenceConstraintException, ParseException{
		
		//根据行状态保存记录
		if (identifyRowStatus.isNew(row)){
			row = saveListener == null ? row : saveListener.beforeInsert(row);
			row = this.insert(row);
			row = saveListener == null ? row : saveListener.afterInsert(row);
		}else if (identifyRowStatus.isModified(row)){
			row = saveListener == null ? row : saveListener.beforeUpdate(row);
			row = this.update(row);
			row = saveListener == null ? row : saveListener.afterUpdate(row);
		}else if (identifyRowStatus.isDeleted(row)){
			row = saveListener == null ? row : saveListener.beforeDelete(row, deleteChildRowOnDelete);
			this.delete(row, deleteChildRowOnDelete);
			row = saveListener == null ? row : saveListener.afterDelete(row);
			
			row = null;
		}

		//返回行
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#insertRows(java.util.List)
	 */
	public List<IRow> insertRows(List<IRow> rows) throws InvalidRowException, SQLException, IOException{
		
		//逐条新建
		//int i = 0;
		//System.out.println("rows count " + rows.size());
		for ( IRow row : rows){
			
			//System.out.println("process " + i++);
			row = this.insert(row);
		}		
		//返回记录
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#insert(com.bln.framework.persist.row.IRow)
	 */
	public IRow insert(IRow row) throws SQLException, IOException, InvalidRowException{
		
		//如果为空直接返回
		if (row == null){
			return row;
		}
		
		//调用数据初始值方法
		row = initDefaultValue(row);
				
		//校验记录是否有效
		if(isValidateRowOnSave){
			IRow reason = isValidRow(row);
			if(reason != null){
				String desc = reason.toString();
				InvalidRowException ivre = new InvalidRowException(desc);
				throw ivre;				
			}
		}
		
		//触发事件
		if(listener != null){
			row = listener.beforeInsertEvt(row);
		}
		
		//执行新增
		row = executeInsert(tableName, row);
		
		//触发事件
		if(listener != null){
			row = listener.afterInsertEvt(row);
		}
		
		//返回记录
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#updateRows(java.util.List)
	 */
	public List<IRow> updateRows(List<IRow> rows) throws PersistStaleEntityException, InvalidRowException, SQLException, IOException{
		
		//逐条新建
		for ( IRow row : rows){
			row = this.update(row);
		}		
		//返回记录
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#update(com.bln.framework.persist.row.IRow)
	 */
	public IRow update(IRow row) throws PersistStaleEntityException, SQLException, IOException, InvalidRowException{
		
		//如果为空直接返回
		if (row == null){
			return row;
		}
				
		//校验记录是否有效
		if(isValidateRowOnSave){
			IRow reason = isValidRow(row);
			if(reason != null){
				String desc = reason.toString();
				InvalidRowException ivre = new InvalidRowException(desc);
				throw ivre;				
			}
		}

		//触发事件
		if(listener != null){
			row = listener.beforeUpdateEvt(row);
		}
		
		//执行更新
		row = this.executeUpdate(tableName, row);
		
		//触发事件
		if(listener != null){
			row = listener.afterUpdateEvt(row);
		}
		
		//返回记录
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#deleteRows(java.util.List)
	 */
	public List<IRow> deleteRows(List<IRow> rows, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException{
		
		//1.删除记录
		for (int i = rows.size() - 1; i >= 0; i--){
			IRow row = rows.get(i);
			this.delete(row, deleteChildRowOnDelete);
		}
		
		//2.返回
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#delete(com.bln.framework.persist.row.IRow)
	 */
	public IRow delete(IRow row, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, SQLException, IOException, ParseException, RefenceConstraintException{
		
		//如果为空直接返回
		if (row == null){
			return row;
		}
		
		//处理子表记录
		if(deleteChildRowOnDelete){
			this.deleteChildRows(row);
		}else{
			//校验相关实体的依赖性
			if (isValidateRefenceOnDelete){
				String eChildName = firstChildNameExistsRows(row);
				if(eChildName != null){
					RefenceConstraintException rce = new RefenceConstraintException(eChildName);
					throw rce;
				}
			}			
		}

		//触发事件
		if(listener != null){
			row = listener.beforeDeleteEvt(row);
		}
		
		//执行更新
		row = this.executeDelete(tableName, row);
		
		//触发事件
		if(listener != null){
			row = listener.afterDeleteEvt(row);
		}
		
		//返回记录
		return row;
	}
	
	/**
	 * 获取第一个当前记录下不为空的子表名称
	 * @param parentRow 主表记录
	 * @return 如果为空，表示不存在子表记录；不为空，第一个不为空的子表
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String firstChildNameExistsRows(IRow parentRow)throws ParseException, SQLException, IOException{
		
		//定义结果变量
		String childName = null;
		
		//根据外键关系，判断子表是否存在记录
		IForeignKey[] foreignKeys = tableStru.getFks();
		if(foreignKeys != null && foreignKeys.length > 0){
			
			for ( IForeignKey fk : foreignKeys){
				
				//获取查询条件
				String childTableName = fk.getRefTableName();
				IConditionClause conClause = parseCondition.parseChildReferCondition(parentRow, tableStru, childTableName);
	
				//获取查询执行器
				ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
				ITableQueryExecutor tqe = session.getTableQueryExecutor(childTableName);
				
				//如果存在记录添加到结果集中
				if(tqe.isExists(conClause)){
					childName = childTableName;
					break;
				}
			}
		}
		
		//返回结果
		return childName;		
	}
	
	/**
	 * 删除当前记录下的子表记录
	 * @param parentRow 父表记录
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void deleteChildRows(IRow parentRow) throws ParseException, SQLException, IOException{
		
		//获得所有外键关系
		IForeignKey[] foreignKeys = tableStru.getFks();
		if(foreignKeys == null || foreignKeys.length <= 0){
			return;
		}
				
		//删除子表记录
		for ( IForeignKey fk : foreignKeys){
			
			//获取查询条件
			String childTableName = fk.getRefTableName();
			IConditionClause conClause = parseCondition.parseChildReferCondition(parentRow, tableStru, childTableName);

			//获取查询执行器
			ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
			ITableUpdateExecutor tqe = session.getTableUpdateExecutor(childTableName);
			
			//删除子表记录
			tqe.deleteByCondition(conClause);
		}	
	}
	
	/**
	 * 初始化默认值
	 * @param row
	 * @return
	 */
	protected IRow initDefaultValue(IRow row)throws SQLException, IOException{
		return row;
	}
	
	/**
	 * 校验记录是否有效
	 * @param row 记录
	 * @return 无效记录的描述，如果为空表示记录有效
	 */
	protected IRow isValidRow(IRow row)throws SQLException, IOException{
		return null;
	}
	
	/**
	 * 根据主键查询记录
	 * @param condition 查询条件
	 * @return 返回符合条件的记录
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	protected List<IRow> queryByIdForList(IRow condition) throws ParseException, SQLException, IOException{

		//1.获取主键条件
		IConditionClause conClause = parseCondition.parseAllIdsCondition(condition, tableStru);

		//2.根据主键查询记录
		List<IRow> rows = this.executeQuery(tableStru.getTableName(), conClause, null, null);
		
		//3.返回记录
		return rows;
	}
	
	/**
	 * 执行查询
	 * @param tableName 要查询的表名
	 * @param conClause 条件对象
	 * @param pagination 分页对象
	 * @param orderby 排序信息
	 * @return 记录集合
	 * @throws SQLException
	 * @throws IOException
	 */
	protected List<IRow> executeQuery(String tableName, IConditionClause conClause, IPagination pagination, String orderby) throws SQLException, IOException{
				
		//1.获取查询执行器
		ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
		ITableQueryExecutor tqe = session.getTableQueryExecutor(tableName);
		
		//2.执行查询
		List<IRow> rows = tqe.query(conClause, pagination, orderby);
		
		//3.返回结果
		return rows;
	}
	
	/**
	 * 执行查询
	 * @param tableName 要查询的表名
	 * @param conClause 条件对象
	 * @param pagination 分页对象
	 * @param orderby 排序信息
	 * @return 记录集合
	 * @throws SQLException
	 * @throws IOException
	 */
	protected List<IRow> executeChildTableQuery(String tableName, IConditionClause conClause) throws SQLException, IOException{
				
		//1.获取查询执行器
		ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
		ITableQueryExecutor tqe = session.getTableQueryExecutor(tableName);
		
		//2.执行查询
		List<IRow> rows = tqe.query(conClause, null, null);
		
		//3.返回结果
		return rows;
	}
	
	/**
	 * 解析查询条件成条件对象
	 * @param condition 查询条件
	 * @param tableName 表名
	 * @return 返回条件对象
	 * @throws ParseException
	 */
	protected IConditionClause parseQueryCondition(IRow condition) throws ParseException{
		return parseCondition.parseQueryCondition(condition, tableStru, null, null);
	}

	/**
	 * 执行新建
	 * @param tableName 要执行新建的表名
	 * @param row 要新建的记录
	 * @return 新建后的记录
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidRowException 
	 */
	protected IRow executeInsert(String tableName, IRow row) throws SQLException, IOException, InvalidRowException{
		
		//1.获取子表记录
		HandleChildRows hanldeChildRows = null;
		Map<String,List<IRow>> subRows = row.getAllSubRows();
		if(subRows != null && !subRows.isEmpty()){
			hanldeChildRows = new HandleChildRows(subRows, tableStru, entityLib);
			hanldeChildRows.setBeforeInsertRow(row);
		}
		
		//2.执行主表新增
		ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
		ITableUpdateExecutor tue = session.getTableUpdateExecutor(tableName);
		
		row = tue.insert(row);
		
		//3.处理子表新增
		if(hanldeChildRows != null){
			hanldeChildRows.insertChildRows(row);
		}
		
		//4.返回结果
		return row;
	}
	
	/**
	 * 执行更新
	 * @param tableName 要执行更新的表名
	 * @param row 要更新的记录
	 * @return 更新后的记录
	 * @throws SQLException
	 * @throws IOException
	 * @throws PersistStaleEntityException
	 * @throws InvalidRowException 
	 * @throws RefenceConstraintException 
	 */
	protected IRow executeUpdate(String tableName, IRow row) throws SQLException, IOException, PersistStaleEntityException{
		
		//1.获取子表记录
		HandleChildRows hanldeChildRows = null;
		Map<String,List<IRow>> subRows = row.getAllSubRows();
		if(subRows != null && !subRows.isEmpty()){
			hanldeChildRows = new HandleChildRows(subRows, tableStru, entityLib);
		}
		
		//2.执行更新
		ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
		ITableUpdateExecutor tue = session.getTableUpdateExecutor(tableName);
		
		row = tue.update(row);
		
		//3.更新子表记录
		if(hanldeChildRows != null){
			try {
				hanldeChildRows.saveChildRows(row);
			} catch (Throwable e) {
				SQLException se = new SQLException();
				se.initCause(e);
				throw se;
			}
		}
		
		//4.返回结果
		return row;
	}

	/**
	 * 执行删除
	 * @param tableName 要执行删除的表名
	 * @param row 要删除的记录
	 * @return 删除前的记录
	 * @throws SQLException
	 * @throws IOException
	 * @throws PersistStaleEntityException
	 * @throws ParseException 
	 * @throws RefenceConstraintException 
	 */
	protected IRow executeDelete(String tableName, IRow row) throws SQLException, IOException, PersistStaleEntityException, RefenceConstraintException, ParseException{
		
		//1.删除子表记录
		HandleChildRows hanldeChildRows = null;
		Map<String,List<IRow>> subRows = row.getAllSubRows();
		if(subRows != null && !subRows.isEmpty()){
			hanldeChildRows = new HandleChildRows(subRows, tableStru, entityLib);
			
			hanldeChildRows.deleteChildRows(row, true);
		}
		
		//2.执行删除
		ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
		ITableUpdateExecutor tue = session.getTableUpdateExecutor(tableName);
		
		row = tue.delete(row);
		
		//3.返回结果
		return row;
	}
	
	/**
	 * @return the tableStru
	 */
	public ITableStru getTableStru() {
		return tableStru;
	}

	/**
	 * @param tableStru the tableStru to set
	 */
	public void setTableStru(ITableStru tableStru) {
		this.tableStru = tableStru;
		tableName = tableStru.getTableName();
	}

	/**
	 * @return the parseCondition
	 */
	public IParseCondition getParseCondition() {
		return parseCondition;
	}

	/**
	 * @param parseCondition the parseCondition to set
	 */
	public void setParseCondition(IParseCondition parseCondition) {
		this.parseCondition = parseCondition;
	}

	/**
	 * @param identifyRowStatus the identifyRowStatus to set
	 */
	public void setIdentifyRowStatus(IdentifyRowStatusStrategy identifyRowStatus) {
		this.identifyRowStatus = identifyRowStatus;
	}

	/**
	 * @return the listener
	 */
	public IEntityListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(IEntityListener listener) {
		this.listener = listener;
	}
}
