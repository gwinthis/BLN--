/**
 * @author gengw
 * Created on Apr 13, 2012
 */
package com.bln.framework.persist.session.executor.row.table;

import java.sql.SQLException;
import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.session.executor.row.SessionExecutorRowBase;
import com.bln.framework.persist.sql.entity.ISqlEntity;
import com.bln.framework.persist.sql.template.ISQLTemplate;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.component.column.IColumn;

/**
 * ���ݿ��б�ĻỰ��
 */
public abstract class TableExecutorBase<T extends TableExecutorBase<T>> extends SessionExecutorRowBase<T> implements ITableExecutor, ITableExecutorBuildable{
	
	/**
	 * ��ṹ����
	 */
	protected ITableStru tableStru = null;
	
	/**
	 * ���SQLģ��
	 */
	protected ISQLTemplate sqlt = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSessionBuildable#setTableStru(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public void setTableStru(ITableStru tableStru) {
		this.tableStru = tableStru;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSessionBuildable#setSqlt(com.bln.framework.persist.sql.template.ISQLTemplate)
	 */
	public void setSqlt(ISQLTemplate sqlt) {
		this.sqlt = sqlt;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSessionBuildable#clone()
	 */
	public ITableExecutorBuildable cloneBuildable(){
		return clone();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public T clone(){
		
		T ts = super.clone();
		
		ts.setTableStru(tableStru);
		ts.setSqlt(sqlt);
		
		return ts;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSessionBuildable#buildToTableSession()
	 */
	@SuppressWarnings("unchecked")
	public T buildToTableExecutor(){
		return (T)this;
	}
	
	/**
	 * ���¼�¼
	 * @param row ��¼����
	 * @param sqlEntity SQLʵ��
	 * @param dbGenIdx ���ݿ��Զ����ɵ��ֶ�����
	 * @param dbGenColumns ���ݿ��Զ����ɵ��ֶ�
	 * @return ���µ��ļ�¼��
	 * @throws SQLException 
	 * @throws SQLException
	 * @throws PersistStaleEntityException 
	 */
	protected IRow executeUpdateRow(IRow row, ISqlEntity<?> sqlEntity, int[] dbGenIdx, List<IColumn> dbGenColumns) throws SQLException, PersistStaleEntityException{
		
		//1.�������ݿ��Զ����ɵ�����
		Object[] dbGenVals = null;
		if(dbGenIdx != null && dbGenIdx.length > 0){
			dbGenVals = new Object[dbGenIdx.length];
		}
		
		//2.ִ�и���
		int updateCount = this.executeUpdate(sqlEntity, dbGenIdx, dbGenVals);
		
		//���δ���µ���¼����Ϊ����ʵ��Ϊ�¾ɵģ����׳��쳣
		if(updateCount <= 0){
			PersistStaleEntityException psee = new PersistStaleEntityException("persisting stale entity, please obtainting fresh entity to persist!");
			psee.addContextValue("row", row);
			psee.addContextValue("sqlEntity", sqlEntity);
			
			throw psee;
		}
		
		
		//3.�������ݿ��Զ����ɵ�ֵ
		if(dbGenVals != null && dbGenVals.length > 0){
			for ( int i = 0, n = dbGenColumns.size(); i < n; i++){
				IColumn column = dbGenColumns.get(i);
				String valInfo = converterUtil.toString(dbGenVals[i], column.getColType());
				
				row.setValue(column.getColName(), valInfo);
			}
		}
		
		//4.������
		return row;
	}
	
	/**
	 * ִ�и���
	 * @param sqlEntity SQLʵ��
	 * @param dbGenIdx ���ݿ��Զ����ɵ��ֶ�����
	 * @param dbGenColumns ���ݿ��Զ����ɵ��ֶ�
	 * @return ���µ��ļ�¼��
	 * @throws SQLException
	 */
	protected int executeUpdate(ISqlEntity<?> sqlEntity, int[] dbGenIdx, Object[] dbGenVals) throws SQLException{

		//2.ִ�и���
		int updateCount = this.jdbcTemplate.update(conn, sqlEntity.getSqlInfo(), sqlEntity.getParams(), dbGenIdx, dbGenVals);
				
		//3.����Ӱ�������
		return updateCount;
	}
	
	

}
