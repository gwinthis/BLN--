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
 * ��ĻỰ����ִ������
 */
public class TableUpdateExecutor extends TableExecutorBase<TableUpdateExecutor> implements ITableUpdateExecutor{
	
	/**
	 * ����������
	 */
	ITableUpdateExecutorListener listener = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.table.ITableSession#insert(com.bln.framework.persist.row.IRow)
	 */
	public IRow insert(IRow row) throws SQLException{
		
		//1.������Ҫ��������ֵ���ֶ�
		IInsertSqlEntity insertSql = sqlt.getInsertSql();
		List<IColumn> notDbGenColumns = insertSql.getNotDbGenColumns();
		
		if(notDbGenColumns != null && notDbGenColumns.size() > 0){
			for ( int i = 0, n = notDbGenColumns.size(); i < n; i++){
				IColumn column = notDbGenColumns.get(i);
				IGenerator generator = column.getGenerator();
				
				//��ȡ�ֶ�ֵ������
				IColumnValueGenerator colValueGenerator = valGeneratorLib.getInstance(generator.getType());
				
				//��ȡ�ֶ�ֵ
				Object obj = colValueGenerator.getVal(conn, generator.getParams(), null);
				String valInfo = converterUtil.toString(obj, column.getColType());
				
				//���嵽row��
				row.setValue(column.getColName(), valInfo);
			}
		}
		
		//��������֮ǰ���¼�
		row = listener == null ? row : listener.beforeRowInsert(tableStru, row);
		
		//2.��������Ҫִ�е�SQL
		insertSql = this.sqlt.insertSql(row);
		
		//3.ִ�и���
		try {
			row = executeUpdateRow(row, insertSql, insertSql.getDbGenColIdxs(), insertSql.getDbGenColumns());
		} catch (PersistStaleEntityException e) {
			SQLException se = new SQLException("unknow error about PersistStaleEntityException!");
			se.initCause(e);
			throw se;
		}
		
		//����������ɵ��¼�
		row = listener == null ? row : listener.rowInserted(tableStru, row);
		
		//4.������
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#delete(com.bln.framework.persist.row.IRow)
	 */
	public IRow delete(IRow row) throws PersistStaleEntityException, SQLException{
				
		//2.�����������

		//2.1��������
		IConditionClause conClause = new ConditionClause();
				
		//2.2������������
		conClause = this.buildEntityUpdateIdCondition(conClause, row, tableStru.getIds());
		
		//2.3�汾������
		
		//��ȡ�汾����Ϣ
		IColumn versionColumn = tableStru.getVerCol();

		//��������
		if(versionColumn != null){
			conClause.addCondition(" AND ");
			
			IStruRow struRow = new StruRow(row, tableStru.getAllColumns().getColumnTypeMap(), converterUtil);
			this.buildEntityUpdateVersionCondition(conClause, struRow, versionColumn);
		}
		
		//����ɾ��֮ǰ���¼�
		row = listener == null ? row : listener.beforeRowDelete(tableStru, row);
		
		//3.����SQLEntity
		IDeleteSqlEntity deleteSqlEntity = this.sqlt.deleteSql(conClause);
		
		//4.ִ�и���
		executeUpdateRow(row, deleteSqlEntity, null, null);

		//����ɾ��֮����¼�
		row = listener == null ? row : listener.rowDeleted(tableStru, row);
		
		//5.���ؼ�¼
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#deleteByCondition(com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public int deleteByCondition(IConditionClause conClause) throws SQLException{
		
		//1.У�����
		Assert.paramIsNotNull(conClause, "conClause");
		
		//����ɾ��֮ǰ���¼�
		conClause = listener == null ? conClause : listener.beforeDeleteByCondition(tableStru, conClause);

		//2.��ȡɾ�����
		IDeleteSqlEntity deleteSqlEntity = this.sqlt.deleteSql(conClause);
		
		//3.ִ��ɾ��
		int deletedCount = this.executeUpdate(deleteSqlEntity, null, null);

		//����ɾ��֮ǰ���¼�
		if(listener != null){
			listener.deletedByCondition(tableStru, conClause, deletedCount);
		}

		//4.����ɾ������
		return deletedCount;
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.table.ITableSession#update(com.bln.framework.persist.row.IRow)
	 */
	public IRow update(IRow row) throws SQLException, PersistStaleEntityException{
		
		//У�����
		Assert.paramIsNotNull(row, "row");
		IStruRow struRow = new StruRow(row, tableStru.getAllColumns().getColumnTypeMap(), converterUtil);

		//1.��������������
		IConditionClause conClause = new ConditionClause();
		
		//1.1������������
		conClause = this.buildEntityUpdateIdCondition(conClause, row, tableStru.getIds());
		
		//1.2�汾������
		IColumn versionColumn = tableStru.getVerCol();
		if(versionColumn != null){
			conClause.addCondition(" AND ");
			this.buildEntityUpdateVersionCondition(conClause, struRow, versionColumn);
		}
		
		//2.���°汾��
		if( versionColumn != null){
			refreshVersion(struRow, versionColumn);
		}
		
		//��������֮ǰ���¼�
		row = listener == null ? row : listener.beforeRowUpdate(tableStru, row);

		//3.����SQLEntity
		IUpdateSqlEntity updateSqlEntity = this.sqlt.updateSqlInTime(row, conClause);
		
		//4.ִ�и��¼�¼
		row = executeUpdateRow(row, updateSqlEntity, null, null);

		//��������֮����¼�
		row = listener == null ? row : listener.rowUpdated(tableStru, row);

		//5.���ؼ�¼
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#updateByCondition(com.bln.framework.persist.row.IRow, com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public int updateByCondition(IRow row, IConditionClause conClause) throws SQLException{
		
		//1.У�����
		Assert.paramIsNotNull(conClause, "conClause");
		
		//��������֮ǰ���¼�
		row = listener == null ? row : listener.beforeUpdateByCondition(tableStru, row, conClause);

		//2.��ȡ�������
		IUpdateSqlEntity updateSqlEntity = this.sqlt.updateSqlInTime(row, conClause);
		
		//3.ִ�и���
		int updateCount = this.executeUpdate(updateSqlEntity, null, null);

		//��������֮����¼�
		if(listener != null){
			listener.updatedByCondition(tableStru, row, conClause, updateCount);
		}
		
		//4.���ظ�������
		return updateCount;
		
	}
	
	/**
	 * ����ʵ����µ���������
	 * @param conClause �������
	 * @param row Ҫ���µļ�¼
	 * @param ids ����
	 * @return ����ƴװ�������
	 */
	protected IConditionClause buildEntityUpdateIdCondition(IConditionClause conClause, IRow row, IColumn[] ids){
		
		//1.У������
		if(ids == null || ids.length <= 0){
			SessionExecuteException see = new SessionExecuteException(" when updating row, not found ids of table structure!");
			see.addContextValue("ids", ids);
			see.addContextValue("tableStru", tableStru);
			
			throw see;			
		}
		
		//2.������������
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
		
		//3.��������
		return conClause;
	}
	
	/**
	 * ����ʵ����µİ汾������
	 * @param conClause �������
	 * @param row Ҫ���µļ�¼
	 * @param versionColumn �汾���ֶ�
	 * @return
	 */
	protected IConditionClause buildEntityUpdateVersionCondition(IConditionClause conClause, IStruRow row, IColumn versionColumn ){
		
		String verColName = versionColumn.getColName();
		Object currVersion = row.getValueAsObject(verColName);
		
		if(currVersion == null){
			SessionExecuteException see = new SessionExecuteException(" when updating row, the version name  " + verColName + " can't be null in row!");
			throw see;
		}
		
		//1.��������
		conClause.addConValue(new StringBuilder(verColName).append(" = ?").toString(), currVersion);
		
		//2.��������
		return conClause;
	}
	
	/**
	 * ˢ�°汾
	 * @param row ��¼����
	 * @param versionColumn �汾�ֶ�
	 * @throws SQLException 
	 * @throws Throwable
	 */
	protected void refreshVersion(IStruRow row, IColumn versionColumn) throws SQLException{
		
		//�汾������
		String verColName = versionColumn.getColName();
		
		//��ȡ��ǰ�汾��ֵ
		Object currVersion = row.getValueAsObject(verColName);
		if(currVersion == null){
			SessionExecuteException see = new SessionExecuteException(" when update row, not found value of version column!");
			see.addContextValue("row", row);
			see.addContextValue("verColName", verColName);
			
			throw see;
		}
		
		//��ȡ�ֶ�ֵ������
		IGenerator generator = versionColumn.getGenerator();
		Assert.paramIsNotNull(generator, "generator");
		
		IColumnValueGenerator colValueGenerator = valGeneratorLib.getNotNullInstance(generator.getType());
		
		//����ֶ�ֵ
		Object newVersion = colValueGenerator.getVal(conn, generator.getParams(), currVersion);
			
		//�����µİ汾��
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
