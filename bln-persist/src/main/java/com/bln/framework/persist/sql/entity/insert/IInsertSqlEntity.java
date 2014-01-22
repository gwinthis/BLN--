package com.bln.framework.persist.sql.entity.insert;

import java.util.List;

import com.bln.framework.persist.sql.entity.ISqlEntity;
import com.bln.framework.persist.tablestru.component.column.IColumn;

public interface IInsertSqlEntity extends ISqlEntity<IInsertSqlEntity>{

	/**
	 * @return the dbGenColIdxs
	 */
	public abstract int[] getDbGenColIdxs();

	/**
	 * @param dbGenColIdxs the dbGenColIdxs to set
	 */
	public abstract void setDbGenColIdxs(int[] dbGenColIdxs);

	/**
	 * @return the dbGenColumns
	 */
	public List<IColumn> getDbGenColumns();

	/**
	 * @param dbGenColumns the dbGenColumns to set
	 */
	public void setDbGenColumns(List<IColumn> dbGenColumns);

	/**
	 * @return the notDbGenColumns
	 */
	public List<IColumn> getNotDbGenColumns();

	/**
	 * @param notDbGenColumns the notDbGenColumns to set
	 */
	public void setNotDbGenColumns(List<IColumn> notDbGenColumns);

}