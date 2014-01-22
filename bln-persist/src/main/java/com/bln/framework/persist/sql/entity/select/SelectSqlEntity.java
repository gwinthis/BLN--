package com.bln.framework.persist.sql.entity.select;

import java.util.List;

import com.bln.framework.persist.sql.entity.SqlEntity;
import com.bln.framework.persist.tablestru.component.column.IColumn;

public class SelectSqlEntity extends SqlEntity<ISelectSqlEntity> implements ISelectSqlEntity{
	
	public SelectSqlEntity() {
		super();
	}

	public SelectSqlEntity(String sqlInfo, List<Object> params,
			String[] tableNames) {
		super(sqlInfo, params, tableNames);
	}


	/**
	 * 要查询的字段名称
	 */
	protected IColumn[] columns = null;

	/**
	 * @return the columns
	 */
	public IColumn[] getColumns() {
		return columns;
	}


	/**
	 * @param columns the columns to set
	 */
	public void setColumns(IColumn[] columns) {
		this.columns = columns;
	}


	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.SqlEntity#newInstance()
	 */
	@Override
	protected ISelectSqlEntity newInstance() {
		return new SelectSqlEntity();
	}

}
