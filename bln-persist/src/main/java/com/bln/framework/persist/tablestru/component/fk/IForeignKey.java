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
	 * ��ȡ����Ĺ�����ϵ��leftΪ�����ֶΣ�rightΪ�ӱ��ֶ�
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