package com.bln.framework.persist.tablestru.param;

public interface IParam {

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @param name the name to set
	 */
	public abstract void setName(String name);

	/**
	 * @return the value
	 */
	public abstract String getValue();

	/**
	 * @param value the value to set
	 */
	public abstract void setValue(String value);

}