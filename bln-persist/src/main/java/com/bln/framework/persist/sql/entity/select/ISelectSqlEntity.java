package com.bln.framework.persist.sql.entity.select;

import com.bln.framework.persist.sql.entity.ISqlEntity;
import com.bln.framework.persist.tablestru.component.column.IColumn;

public interface ISelectSqlEntity extends ISqlEntity<ISelectSqlEntity>{

	/**
	 * @return the columns
	 */
	public IColumn[] getColumns();

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(IColumn[] columns);

}