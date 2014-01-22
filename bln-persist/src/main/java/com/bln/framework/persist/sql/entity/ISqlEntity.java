package com.bln.framework.persist.sql.entity;

import java.util.List;

public interface ISqlEntity<T extends ISqlEntity<T>> {

	/**
	 * @return the sqlInfo
	 */
	public abstract String getSqlInfo();

	/**
	 * @param sqlInfo the sqlInfo to set
	 */
	public abstract void setSqlInfo(String sqlInfo);

	/**
	 * @return the params
	 */
	public abstract List<Object> getParams();

	/**
	 * @param params the params to set
	 */
	public abstract void setParams(List<Object> params);

	public T clone();

	/**
	 * @return the tableName
	 */
	public String[] getTableNames();

	/**
	 * @param tableNames the tableName to set
	 */
	public void setTableName(String[] tableNames);

	/**
	 * 设置变量值
	 * @param param
	 */
	public void addParams(Object...param);

}