package com.bln.framework.persist.tablestru.component.column.generator;

import com.bln.framework.persist.tablestru.param.IParam;

public interface IGenerator {

	/**
	 * @return the type
	 */
	public abstract String getType();

	/**
	 * @param type the type to set
	 */
	public abstract void setType(String type);

	/**
	 * @return the params
	 */
	public abstract IParam[] getParams();

	/**
	 * @param params the params to set
	 */
	public abstract void setParams(IParam[] params);

}