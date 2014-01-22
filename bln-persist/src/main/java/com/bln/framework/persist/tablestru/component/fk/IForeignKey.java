package com.bln.framework.persist.tablestru.component.fk;

import com.bln.framework.util.pair.IPair;

public interface IForeignKey{

	/**
	 * @return the refTableName
	 */
	public abstract String getRefTableName();

	/**
	 * @param refTableName the refTableName to set
	 */
	public abstract void setRefTableName(String refTableName);

	/**
	 * 获取外键的关联关系，left为主表字段，right为子表字段
	 * @return the refColNames
	 */
	public abstract IPair<String, String>[] getRefColNames();

	/**
	 * @param refColNames the refColNames to set
	 */
	public abstract void setRefColNames(IPair<String, String>[] refColNames);

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param name the name to set
	 */
	public void setName(String name);

}