/**
 * @author gengw
 * Created on Apr 13, 2012
 */
package com.bln.framework.persist.session.executor.row.table.update;

import java.sql.SQLException;
import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.row.enhance.stru.IStruRow;
import com.bln.framework.persist.row.enhance.stru.StruRow;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.session.exception.SessionExecuteException;
import com.bln.framework.persist.session.executor.material.condition.ConditionClause;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.row.table.TableExecutorBase;
import com.bln.framework.persist.session.executor.row.table.update.listener.ITableUpdateExecutorListener;
import com.bln.framework.persist.sql.entity.delete.IDeleteSqlEntity;
import com.bln.framework.persist.sql.entity.insert.IInsertSqlEntity;
import com.bln.framework.persist.sql.entity.update.IUpdateSqlEntity;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.component.column.generator.IGenerator;
import com.bln.framework.persist.valgenerator.IColumnValueGenerator;
import com.bln.framework.util.asserter.Assert;

/**
 * 表的会话更新执行器类
 */
public class TableUpdateExecutor extends TableExecutorBase<TableUpdateExecutor> implements ITableUpdateExecutor{
	
	/**
	 * 监听器对象
	 */
	ITableUpdateExecutorListener listener = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.table.ITableSession#insert(com.bln.framework.persist.row.IRow)
	 */
	public IRow insert(IRow row) throws SQLException{
		
		//1.处理需要自行生成值的字段
		IInsertSqlEntity insertSql = sqlt.getInsertSql();
		List<IColumn> notDbGenColumns = insertSql.getNotDbGenColumns();
		
		if(notDbGenColumns != null && notDbGenColumns.size() > 0){
			for ( int i = 0, n = notDbGenColumns.size(); i < n; i++){
				IColumn column = notDbGenColumns.get(i);
				IGenerator generator = column.getGenerator();
				
				//获取字段值生成器
				IColumnValueGenerator colValueGenerator = valGeneratorLib.getInstance(generator.getType());
				
				//获取字段值
				Object obj = colValueGenerator.getVal(conn, generator.getParams(), null);
				String valInfo = converterUtil.toString(obj, column.getColType());
				
				//定义到row中
				row.setValue(column.getColName(), valInfo);
			}
		}
		
		//触发插入之前的事件
		row = listener == null ? row : listener.beforeRowInsert(tableStru, row);
		
		//2.重新生成要执行的SQL
		insertSql = this.sqlt.insertSql(row);
		
		//3.执行更新
		try {
			row = executeUpdateRow(row, insertSql, insertSql.getDbGenColIdxs(), insertSql.getDbGenColumns());
		} catch (PersistStaleEntityException e) {
			SQLException se = new SQLException("unknow error about PersistStaleEntityException!");
			se.initCause(e);
			throw se;
		}
		
		//触发插入完成的事件
		row = listener == null ? row : listener.rowInserted(tableStru, row);
		
		//4.返回行
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#delete(com.bln.framework.persist.row.IRow)
	 */
	public IRow delete(IRow row) throws PersistStaleEntityException, SQLException{
				
		//2.构造条件语句

		//2.1定义条件
		IConditionClause conClause = new ConditionClause();
				
		//2.2构造主键条件
		conClause = this.buildEntityUpdateIdCondition(conClause, row, tableStru.getIds());
		
		//2.3版本号条件
		
		//获取版本号信息
		IColumn versionColumn = tableStru.getVerCol();

		//构造条件
		if(versionColumn != null){
			conClause.addCondition(" AND ");
			
			IStruRow struRow = new StruRow(row, tableStru.getAllColumns().getColumnTypeMap(), converterUtil);
			this.buildEntityUpdateVersionCondition(conClause, struRow, versionColumn);
		}
		
		//触发删除之前的事件
		row = listener == null ? row : listener.beforeRowDelete(tableStru, row);
		
		//3.生成SQLEntity
		IDeleteSqlEntity deleteSqlEntity = this.sqlt.deleteSql(conClause);
		
		//4.执行更新
		executeUpdateRow(row, deleteSqlEntity, null, null);

		//触发删除之后的事件
		row = listener == null ? row : listener.rowDeleted(tableStru, row);
		
		//5.返回记录
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#deleteByCondition(com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public int deleteByCondition(IConditionClause conClause) throws SQLException{
		
		//1.校验参数
		Assert.paramIsNotNull(conClause, "conClause");
		
		//触发删除之前的事件
		conClause = listener == null ? conClause : listener.beforeDeleteByCondition(tableStru, conClause);

		//2.获取删除语句
		IDeleteSqlEntity deleteSqlEntity = this.sqlt.deleteSql(conClause);
		
		//3.执行删除
		int deletedCount = this.executeUpdate(deleteSqlEntity, null, null);

		//触发删除之前的事件
		if(listener != null){
			listener.deletedByCondition(tableStru, conClause, deletedCount);
		}

		//4.返回删除条数
		return deletedCount;
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.table.ITableSession#update(com.bln.framework.persist.row.IRow)
	 */
	public IRow update(IRow row) throws SQLException, PersistStaleEntityException{
		
		//校验参数
		Assert.paramIsNotNull(row, "row");
		IStruRow struRow = new StruRow(row, tableStru.getAllColumns().getColumnTypeMap(), converterUtil);

		//1.构造更新条件语句
		IConditionClause conClause = new ConditionClause();
		
		//1.1构造主键条件
		conClause = this.buildEntityUpdateIdCondition(conClause, row, tableStru.getIds());
		
		//1.2版本号条件
		IColumn versionColumn = tableStru.getVerCol();
		if(versionColumn != null){
			conClause.addCondition(" AND ");
			this.buildEntityUpdateVersionCondition(conClause, struRow, versionColumn);
		}
		
		//2.更新版本号
		if( versionColumn != null){
			refreshVersion(struRow, versionColumn);
		}
		
		//触发更新之前的事件
		row = listener == null ? row : listener.beforeRowUpdate(tableStru, row);

		//3.生成SQLEntity
		IUpdateSqlEntity updateSqlEntity = this.sqlt.updateSqlInTime(row, conClause);
		
		//4.执行更新记录
		row = executeUpdateRow(row, updateSqlEntity, null, null);

		//触发更新之后的事件
		row = listener == null ? row : listener.rowUpdated(tableStru, row);

		//5.返回记录
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#updateByCondition(com.bln.framework.persist.row.IRow, com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public int updateByCondition(IRow row, IConditionClause conClause) throws SQLException{
		
		//1.校验参数
		Assert.paramIsNotNull(conClause, "conClause");
		
		//触发更新之前的事件
		row = listener == null ? row : listener.beforeUpdateByCondition(tableStru, row, conClause);

		//2.获取更新语句
		IUpdateSqlEntity updateSqlEntity = this.sqlt.updateSqlInTime(row, conClause);
		
		//3.执行更新
		int updateCount = this.executeUpdate(updateSqlEntity, null, null);

		//触发更新之后的事件
		if(listener != null){
			listener.updatedByCondition(tableStru, row, conClause, updateCount);
		}
		
		//4.返回更新条数
		return updateCount;
		
	}
	
	/**
	 * 构造实体更新的主键条件
	 * @param conClause 条件语句
	 * @param row 要更新的记录
	 * @param ids 主键
	 * @return 返回拼装后的条件
	 */
	protected IConditionClause buildEntityUpdateIdCondition(IConditionClause conClause, IRow row, IColumn[] ids){
		
		//1.校验主键
		if(ids == null || ids.length <= 0){
			SessionExecuteException see = new SessionExecuteException(" when updating row, not found ids of table structure!");
			see.addContextValue("ids", ids);
			see.addContextValue("tableStru", tableStru);
			
			throw see;			
		}
		
		//2.构造主键条件
		for ( int i = 0, n = ids.length; i < n; i++){
			IColumn id = ids[i];
			String idName = id.getColName();
			
			if(i != 0){
				conClause.addCondition(" AND ");
			}
		
			String con = idName + " = ?";
			Object param = converterUtil.convert(row.getValue(idName), id.getColType());
			if(param == null){
				SessionExecuteException see = new SessionExecuteException(" when updating row, the id " + idName + " can't be null in row!");
				see.addContextValue("id", idName);
				see.addContextValue("row", row);
				
				throw see;
			}
			conClause.addConValue(con, param);
		}
		
		//3.返回条件
		return conClause;
	}
	
	/**
	 * 构造实体更新的版本号条件
	 * @param conClause 条件语句
	 * @param row 要更新的记录
	 * @param versionColumn 版本号字段
	 * @return
	 */
	protected IConditionClause buildEntityUpdateVersionCondition(IConditionClause conClause, IStruRow row, IColumn versionColumn ){
		
		String verColName = versionColumn.getColName();
		Object currVersion = row.getValueAsObject(verColName);
		
		if(currVersion == null){
			SessionExecuteException see = new SessionExecuteException(" when updating row, the version name  " + verColName + " can't be null in row!");
			throw see;
		}
		
		//1.构造条件
		conClause.addConValue(new StringBuilder(verColName).append(" = ?").toString(), currVersion);
		
		//2.返回条件
		return conClause;
	}
	
	/**
	 * 刷新版本
	 * @param row 记录对象
	 * @param versionColumn 版本字段
	 * @throws SQLException 
	 * @throws Throwable
	 */
	protected void refreshVersion(IStruRow row, IColumn versionColumn) throws SQLException{
		
		//版本号名称
		String verColName = versionColumn.getColName();
		
		//获取当前版本号值
		Object currVersion = row.getValueAsObject(verColName);
		if(currVersion == null){
			SessionExecuteException see = new SessionExecuteException(" when update row, not found value of version column!");
			see.addContextValue("row", row);
			see.addContextValue("verColName", verColName);
			
			throw see;
		}
		
		//获取字段值生成器
		IGenerator generator = versionColumn.getGenerator();
		Assert.paramIsNotNull(generator, "generator");
		
		IColumnValueGenerator colValueGenerator = valGeneratorLib.getNotNullInstance(generator.getType());
		
		//获得字段值
		Object newVersion = colValueGenerator.getVal(conn, generator.getParams(), currVersion);
			
		//设置新的版本号
		row.setValueFromObject(verColName, newVersion);
	
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.SessionExecutorBase#newInstance()
	 */
	@Override
	protected TableUpdateExecutor newInstance() {
		return new TableUpdateExecutor();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public TableUpdateExecutor clone(){
		
		TableUpdateExecutor tue = super.clone();
		tue.setListener(listener);
		
		return tue;
	}

	/**
	 * @return the listener
	 */
	public ITableUpdateExecutorListener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(ITableUpdateExecutorListener listener) {
		this.listener = listener;
	}
}
