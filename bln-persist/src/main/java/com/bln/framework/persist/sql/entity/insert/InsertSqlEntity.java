/**
 * @author gengw
 * Created on Apr 19, 2012
 */
package com.bln.framework.persist.sql.entity.insert;

import java.util.Arrays;
import java.util.List;

import com.bln.framework.persist.sql.entity.SqlEntity;
import com.bln.framework.persist.tablestru.component.column.IColumn;

/**
 * Insert SQL语句实体
 */
public class InsertSqlEntity extends SqlEntity<IInsertSqlEntity> implements IInsertSqlEntity{

	/**
	 * 需要数据库生成的字段在Insert中的索引
	 */
	public int[] dbGenColIdxs = null;

	/**
	 * 需要数据库生成的字段
	 */
	public List <IColumn> dbGenColumns = null;
	
	/**
	 * 需要自动生成的字段
	 */
	public List <IColumn> notDbGenColumns = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.insert.IInsertSqlEntity#getDbGenColIdxs()
	 */
	public int[] getDbGenColIdxs() {
		return dbGenColIdxs;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.insert.IInsertSqlEntity#setDbGenColIdxs(int[])
	 */
	public void setDbGenColIdxs(int[] dbGenColIdxs) {
		this.dbGenColIdxs = dbGenColIdxs;
	}

	/**
	 * @return the dbGenColumns
	 */
	public List<IColumn> getDbGenColumns() {
		return dbGenColumns;
	}

	/**
	 * @param dbGenColumns the dbGenColumns to set
	 */
	public void setDbGenColumns(List<IColumn> dbGenColumns) {
		this.dbGenColumns = dbGenColumns;
	}

	/**
	 * @return the notDbGenColumns
	 */
	public List<IColumn> getNotDbGenColumns() {
		return notDbGenColumns;
	}

	/**
	 * @param notDbGenColumns the notDbGenColumns to set
	 */
	public void setNotDbGenColumns(List<IColumn> notDbGenColumns) {
		this.notDbGenColumns = notDbGenColumns;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder info = new StringBuilder(super.toString());
		
		info.append("dbGenColIdxs : ").append(Arrays.toString(dbGenColIdxs)).append("\r\n");
		info.append("dbGenColumns : ").append(dbGenColumns.toString()).append("\r\n");
		info.append("notDbGenColumns : ").append(notDbGenColumns.toString()).append("\r\n");
		
		return info.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public IInsertSqlEntity clone(){
		
		IInsertSqlEntity insertSqlEntity = super.clone();
		
		insertSqlEntity.setDbGenColIdxs(dbGenColIdxs);
		insertSqlEntity.setDbGenColumns(dbGenColumns);
		insertSqlEntity.setNotDbGenColumns(notDbGenColumns);
		
		return insertSqlEntity;
	}

	@Override
	protected IInsertSqlEntity newInstance() {
		return new InsertSqlEntity();
	}
}
