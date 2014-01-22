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
 * ��״̬ʵ����ģ��
 */
public class EntityStatelessModule extends BizModuleBase implements IEntityStatelessModule {
	
	/**
	 * ����
	 */
	protected String tableName = null;
	
	/**
	 * ��ǰʵ����ʹ�õı�ṹ
	 */
	protected ITableStru tableStru = null;
		
	/**
	 * ������������
	 */
	protected IParseCondition parseCondition = null;
	
	/**
	 * ������״̬�Ĳ��Զ���
	 */
	protected IIdentifyRowStatusStrategy identifyRowStatus = null;
	
	/**
	 * ���½��͸��¼�¼ʱ�Ƿ�У���¼�Ƿ���Ч
	 */
	protected final boolean isValidateRowOnSave = false;
	
	/**
	 * ��ɾ����¼ʱ�Ƿ�У�������
	 */
	protected final boolean isValidateRefenceOnDelete = false;
	
	/**
	 * ������
	 */
	protected IEntityListener listener = null;
	
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#query(com.bln.framework.persist.row.IRow, com.bln.framework.persist.session.executor.material.pagination.IPagination, java.lang.String)
	 */
	public List<IRow> query(IRow condition, IPagination pagination, String orderby) throws SQLException, IOException, ParseException{
		
		//1.��ȡ��ѯ����
		IConditionClause conClause = parseQueryCondition(condition);

		//�����¼�
		if(listener != null){
			listener.beforeQueryEvt(conClause, pagination, orderby);
		}
		
		//2.ִ�в�ѯ
		List<IRow> rows = this.executeQuery(tableName, conClause, pagination, orderby);
		
		//�����¼�
		if(listener != null){
			rows = listener.afterQueryEvt(rows);
		}
		
		//3.���ؽ��
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#queryById(com.bln.framework.persist.row.IRow)
	 */
	public IRow queryById(IRow condition) throws ParseException, SQLException, IOException{
		
		//1.����������ѯ��¼
		List<IRow> rows = this.queryByIdForList(condition);
		
		//2.��ȡ��һ�м�¼
		IRow row = null;
		if(rows != null && rows.size() > 0){
			row = rows.get(0);
		}
		
		//3.���ؼ�¼
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#queryByIdWithChilds(com.bln.framework.persist.row.IRow, java.lang.String[])
	 */
	public Map<String, List<IRow>> queryByIdWithChilds(IRow condition, String[] childTableNames) throws SQLException, IOException, ParseException{
		
		//1.��ȡ�����¼
		List<IRow> parentRows = this.queryByIdForList(condition);
		
		IRow parentRow = null;
		if(parentRows != null && parentRows.size() > 0){
			parentRow = parentRows.get(0);
		}
		
		if(parentRow == null){
			return null;
		}
		
		//2.��ȡ�ӱ�����
		Map<String,List<IRow>> resultMap = this.queryChilds(parentRow, childTableNames);
		
		//3.��Ӽ�¼
		if(resultMap == null){
			resultMap = new HashMap<String,List<IRow>>();
		}
		resultMap.put(this.tableStru.getTableName(), parentRows);
		
		//4.���ؽ��Map
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
				
				//��ȡ�ӱ�����
				String childTableName = childTableNames[i];
				IConditionClause conClause = parseCondition.parseChildReferCondition(row, tableStru, childTableName);
				
				//ִ�в�ѯ
				List<IRow> childRows = this.executeChildTableQuery(childTableName, conClause);
				
				//��ӵ����ؽ����
				resultMap.put(childTableName, childRows);
			}
		}
		
		return resultMap;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#save(java.util.List, java.util.Map)
	 */
	public Map<String, List<IRow>> save(List<IRow> rows, Map<String, List<IRow>> childRowsMap, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, InvalidRowException, SQLException, IOException, ParseException{
		
		//1.���������¼
		ISaveListener saveListener = new SaveListenerForChild(this.tableStru, childRowsMap, entityLib, identifyRowStatus);
		this.save(rows, saveListener, deleteChildRowOnDelete);
		
		//2.�����ӱ��¼
		if(childRowsMap == null || childRowsMap.isEmpty()){
			childRowsMap = new HashMap<String, List<IRow>>();
		}else{
			for (Map.Entry<String, List<IRow>> childEntry : childRowsMap.entrySet()){
				IEntityStatelessModule entity = entityLib.getInstance(childEntry.getKey());
				entity.save(childEntry.getValue(), null, deleteChildRowOnDelete);
			}
		}
		
		//��������¼
		childRowsMap.put(this.tableName, rows);
		
		//���ؽ����
		return childRowsMap;
		
	}
	
	/**
	 * ���������¼
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
		
		//У�����
		if(rows == null || rows.size() <= 0){
			return rows;
		}
		
		//1.ɾ����¼
		for (int i = rows.size() - 1; i >= 0; i--){
			IRow row = rows.get(i);
			if (identifyRowStatus.isDeleted(row)){
				row = saveListener == null ? row : saveListener.beforeDelete(row, deleteChildRowOnDelete);
				this.delete(row, deleteChildRowOnDelete);
				row = saveListener == null ? row : saveListener.afterDelete(row);
				
				rows.remove(i);
			}
		}
		
		//2.��������¼�¼
		for ( IRow row : rows){
			row = this.save(row, saveListener, deleteChildRowOnDelete);
		}
		
		//3.���ؼ�¼
		return rows;
	}

	/**
	 * ���浥����¼
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
		
		//������״̬�����¼
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

		//������
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#insertRows(java.util.List)
	 */
	public List<IRow> insertRows(List<IRow> rows) throws InvalidRowException, SQLException, IOException{
		
		//�����½�
		//int i = 0;
		//System.out.println("rows count " + rows.size());
		for ( IRow row : rows){
			
			//System.out.println("process " + i++);
			row = this.insert(row);
		}		
		//���ؼ�¼
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#insert(com.bln.framework.persist.row.IRow)
	 */
	public IRow insert(IRow row) throws SQLException, IOException, InvalidRowException{
		
		//���Ϊ��ֱ�ӷ���
		if (row == null){
			return row;
		}
		
		//�������ݳ�ʼֵ����
		row = initDefaultValue(row);
				
		//У���¼�Ƿ���Ч
		if(isValidateRowOnSave){
			IRow reason = isValidRow(row);
			if(reason != null){
				String desc = reason.toString();
				InvalidRowException ivre = new InvalidRowException(desc);
				throw ivre;				
			}
		}
		
		//�����¼�
		if(listener != null){
			row = listener.beforeInsertEvt(row);
		}
		
		//ִ������
		row = executeInsert(tableName, row);
		
		//�����¼�
		if(listener != null){
			row = listener.afterInsertEvt(row);
		}
		
		//���ؼ�¼
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#updateRows(java.util.List)
	 */
	public List<IRow> updateRows(List<IRow> rows) throws PersistStaleEntityException, InvalidRowException, SQLException, IOException{
		
		//�����½�
		for ( IRow row : rows){
			row = this.update(row);
		}		
		//���ؼ�¼
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#update(com.bln.framework.persist.row.IRow)
	 */
	public IRow update(IRow row) throws PersistStaleEntityException, SQLException, IOException, InvalidRowException{
		
		//���Ϊ��ֱ�ӷ���
		if (row == null){
			return row;
		}
				
		//У���¼�Ƿ���Ч
		if(isValidateRowOnSave){
			IRow reason = isValidRow(row);
			if(reason != null){
				String desc = reason.toString();
				InvalidRowException ivre = new InvalidRowException(desc);
				throw ivre;				
			}
		}

		//�����¼�
		if(listener != null){
			row = listener.beforeUpdateEvt(row);
		}
		
		//ִ�и���
		row = this.executeUpdate(tableName, row);
		
		//�����¼�
		if(listener != null){
			row = listener.afterUpdateEvt(row);
		}
		
		//���ؼ�¼
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#deleteRows(java.util.List)
	 */
	public List<IRow> deleteRows(List<IRow> rows, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException{
		
		//1.ɾ����¼
		for (int i = rows.size() - 1; i >= 0; i--){
			IRow row = rows.get(i);
			this.delete(row, deleteChildRowOnDelete);
		}
		
		//2.����
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.entity.IEntityStatelessModule#delete(com.bln.framework.persist.row.IRow)
	 */
	public IRow delete(IRow row, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, SQLException, IOException, ParseException, RefenceConstraintException{
		
		//���Ϊ��ֱ�ӷ���
		if (row == null){
			return row;
		}
		
		//�����ӱ��¼
		if(deleteChildRowOnDelete){
			this.deleteChildRows(row);
		}else{
			//У�����ʵ���������
			if (isValidateRefenceOnDelete){
				String eChildName = firstChildNameExistsRows(row);
				if(eChildName != null){
					RefenceConstraintException rce = new RefenceConstraintException(eChildName);
					throw rce;
				}
			}			
		}

		//�����¼�
		if(listener != null){
			row = listener.beforeDeleteEvt(row);
		}
		
		//ִ�и���
		row = this.executeDelete(tableName, row);
		
		//�����¼�
		if(listener != null){
			row = listener.afterDeleteEvt(row);
		}
		
		//���ؼ�¼
		return row;
	}
	
	/**
	 * ��ȡ��һ����ǰ��¼�²�Ϊ�յ��ӱ�����
	 * @param parentRow �����¼
	 * @return ���Ϊ�գ���ʾ�������ӱ��¼����Ϊ�գ���һ����Ϊ�յ��ӱ�
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String firstChildNameExistsRows(IRow parentRow)throws ParseException, SQLException, IOException{
		
		//����������
		String childName = null;
		
		//���������ϵ���ж��ӱ��Ƿ���ڼ�¼
		IForeignKey[] foreignKeys = tableStru.getFks();
		if(foreignKeys != null && foreignKeys.length > 0){
			
			for ( IForeignKey fk : foreignKeys){
				
				//��ȡ��ѯ����
				String childTableName = fk.getRefTableName();
				IConditionClause conClause = parseCondition.parseChildReferCondition(parentRow, tableStru, childTableName);
	
				//��ȡ��ѯִ����
				ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
				ITableQueryExecutor tqe = session.getTableQueryExecutor(childTableName);
				
				//������ڼ�¼��ӵ��������
				if(tqe.isExists(conClause)){
					childName = childTableName;
					break;
				}
			}
		}
		
		//���ؽ��
		return childName;		
	}
	
	/**
	 * ɾ����ǰ��¼�µ��ӱ��¼
	 * @param parentRow �����¼
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void deleteChildRows(IRow parentRow) throws ParseException, SQLException, IOException{
		
		//������������ϵ
		IForeignKey[] foreignKeys = tableStru.getFks();
		if(foreignKeys == null || foreignKeys.length <= 0){
			return;
		}
				
		//ɾ���ӱ��¼
		for ( IForeignKey fk : foreignKeys){
			
			//��ȡ��ѯ����
			String childTableName = fk.getRefTableName();
			IConditionClause conClause = parseCondition.parseChildReferCondition(parentRow, tableStru, childTableName);

			//��ȡ��ѯִ����
			ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
			ITableUpdateExecutor tqe = session.getTableUpdateExecutor(childTableName);
			
			//ɾ���ӱ��¼
			tqe.deleteByCondition(conClause);
		}	
	}
	
	/**
	 * ��ʼ��Ĭ��ֵ
	 * @param row
	 * @return
	 */
	protected IRow initDefaultValue(IRow row)throws SQLException, IOException{
		return row;
	}
	
	/**
	 * У���¼�Ƿ���Ч
	 * @param row ��¼
	 * @return ��Ч��¼�����������Ϊ�ձ�ʾ��¼��Ч
	 */
	protected IRow isValidRow(IRow row)throws SQLException, IOException{
		return null;
	}
	
	/**
	 * ����������ѯ��¼
	 * @param condition ��ѯ����
	 * @return ���ط��������ļ�¼
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	protected List<IRow> queryByIdForList(IRow condition) throws ParseException, SQLException, IOException{

		//1.��ȡ��������
		IConditionClause conClause = parseCondition.parseAllIdsCondition(condition, tableStru);

		//2.����������ѯ��¼
		List<IRow> rows = this.executeQuery(tableStru.getTableName(), conClause, null, null);
		
		//3.���ؼ�¼
		return rows;
	}
	
	/**
	 * ִ�в�ѯ
	 * @param tableName Ҫ��ѯ�ı���
	 * @param conClause ��������
	 * @param pagination ��ҳ����
	 * @param orderby ������Ϣ
	 * @return ��¼����
	 * @throws SQLException
	 * @throws IOException
	 */
	protected List<IRow> executeQuery(String tableName, IConditionClause conClause, IPagination pagination, String orderby) throws SQLException, IOException{
				
		//1.��ȡ��ѯִ����
		ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
		ITableQueryExecutor tqe = session.getTableQueryExecutor(tableName);
		
		//2.ִ�в�ѯ
		List<IRow> rows = tqe.query(conClause, pagination, orderby);
		
		//3.���ؽ��
		return rows;
	}
	
	/**
	 * ִ�в�ѯ
	 * @param tableName Ҫ��ѯ�ı���
	 * @param conClause ��������
	 * @param pagination ��ҳ����
	 * @param orderby ������Ϣ
	 * @return ��¼����
	 * @throws SQLException
	 * @throws IOException
	 */
	protected List<IRow> executeChildTableQuery(String tableName, IConditionClause conClause) throws SQLException, IOException{
				
		//1.��ȡ��ѯִ����
		ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
		ITableQueryExecutor tqe = session.getTableQueryExecutor(tableName);
		
		//2.ִ�в�ѯ
		List<IRow> rows = tqe.query(conClause, null, null);
		
		//3.���ؽ��
		return rows;
	}
	
	/**
	 * ������ѯ��������������
	 * @param condition ��ѯ����
	 * @param tableName ����
	 * @return ������������
	 * @throws ParseException
	 */
	protected IConditionClause parseQueryCondition(IRow condition) throws ParseException{
		return parseCondition.parseQueryCondition(condition, tableStru, null, null);
	}

	/**
	 * ִ���½�
	 * @param tableName Ҫִ���½��ı���
	 * @param row Ҫ�½��ļ�¼
	 * @return �½���ļ�¼
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidRowException 
	 */
	protected IRow executeInsert(String tableName, IRow row) throws SQLException, IOException, InvalidRowException{
		
		//1.��ȡ�ӱ��¼
		HandleChildRows hanldeChildRows = null;
		Map<String,List<IRow>> subRows = row.getAllSubRows();
		if(subRows != null && !subRows.isEmpty()){
			hanldeChildRows = new HandleChildRows(subRows, tableStru, entityLib);
			hanldeChildRows.setBeforeInsertRow(row);
		}
		
		//2.ִ����������
		ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
		ITableUpdateExecutor tue = session.getTableUpdateExecutor(tableName);
		
		row = tue.insert(row);
		
		//3.�����ӱ�����
		if(hanldeChildRows != null){
			hanldeChildRows.insertChildRows(row);
		}
		
		//4.���ؽ��
		return row;
	}
	
	/**
	 * ִ�и���
	 * @param tableName Ҫִ�и��µı���
	 * @param row Ҫ���µļ�¼
	 * @return ���º�ļ�¼
	 * @throws SQLException
	 * @throws IOException
	 * @throws PersistStaleEntityException
	 * @throws InvalidRowException 
	 * @throws RefenceConstraintException 
	 */
	protected IRow executeUpdate(String tableName, IRow row) throws SQLException, IOException, PersistStaleEntityException{
		
		//1.��ȡ�ӱ��¼
		HandleChildRows hanldeChildRows = null;
		Map<String,List<IRow>> subRows = row.getAllSubRows();
		if(subRows != null && !subRows.isEmpty()){
			hanldeChildRows = new HandleChildRows(subRows, tableStru, entityLib);
		}
		
		//2.ִ�и���
		ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
		ITableUpdateExecutor tue = session.getTableUpdateExecutor(tableName);
		
		row = tue.update(row);
		
		//3.�����ӱ��¼
		if(hanldeChildRows != null){
			try {
				hanldeChildRows.saveChildRows(row);
			} catch (Throwable e) {
				SQLException se = new SQLException();
				se.initCause(e);
				throw se;
			}
		}
		
		//4.���ؽ��
		return row;
	}

	/**
	 * ִ��ɾ��
	 * @param tableName Ҫִ��ɾ���ı���
	 * @param row Ҫɾ���ļ�¼
	 * @return ɾ��ǰ�ļ�¼
	 * @throws SQLException
	 * @throws IOException
	 * @throws PersistStaleEntityException
	 * @throws ParseException 
	 * @throws RefenceConstraintException 
	 */
	protected IRow executeDelete(String tableName, IRow row) throws SQLException, IOException, PersistStaleEntityException, RefenceConstraintException, ParseException{
		
		//1.ɾ���ӱ��¼
		HandleChildRows hanldeChildRows = null;
		Map<String,List<IRow>> subRows = row.getAllSubRows();
		if(subRows != null && !subRows.isEmpty()){
			hanldeChildRows = new HandleChildRows(subRows, tableStru, entityLib);
			
			hanldeChildRows.deleteChildRows(row, true);
		}
		
		//2.ִ��ɾ��
		ISession session = sessionStrategy.getSession(sessionFactory, defaultSessionKey);
		ITableUpdateExecutor tue = session.getTableUpdateExecutor(tableName);
		
		row = tue.delete(row);
		
		//3.���ؽ��
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
