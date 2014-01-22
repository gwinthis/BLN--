package com.bln.framework.persist.session.executor.material.query;

import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

public interface IQueryInfo {

	/**
	 * @return the cols
	 */
	public abstract String getCols();

	/**
	 * @param cols the cols to set
	 */
	public abstract void setCols(String cols);

	/**
	 * @return the groupby
	 */
	public abstract String getGroupby();

	/**
	 * @param groupby the groupby to set
	 */
	public abstract void setGroupby(String groupby);

	/**
	 * @return the orderby
	 */
	public abstract String getOrderby();

	/**
	 * @param orderby the orderby to set
	 */
	public abstract void setOrderby(String orderby);

	/**
	 * @return the tables
	 */
	public abstract String[] getTables();

	/**
	 * @param tables the tables to set
	 */
	public abstract void setTables(String[] tables);

	/**
	 * @return the conClause
	 */
	public abstract IConditionClause getConClause();

	/**
	 * @param conClause the conClause to set
	 */
	public abstract void setConClause(IConditionClause conClause);

	/**
	 * @return the pagination
	 */
	public abstract IPagination getPagination();

	/**
	 * @param pagination the pagination to set
	 */
	public abstract void setPagination(IPagination pagination);

}