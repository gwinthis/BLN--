package com.bln.framework.persist.tablestru.component.column;

import com.bln.framework.persist.tablestru.component.column.generator.IGenerator;

public interface IColumn{

	/**
	 * @return the colName
	 */
	public abstract String getColName();

	/**
	 * @param colName the colName to set
	 */
	public abstract void setColName(String colName);

	/**
	 * @return the colType
	 */
	public abstract int getColType();

	/**
	 * @param colType the colType to set
	 */
	public abstract void setColType(int colType);

	public IGenerator getGenerator();

	public void setGenerator(IGenerator generator);

	/**
	 * @return the defaultVal
	 */
	public String getDefaultVal();

	/**
	 * @param defaultVal the defaultVal to set
	 */
	public void setDefaultVal(String defaultVal);

	/**
	 * @return the colIndx
	 */
	public int getColIndx();

	/**
	 * @param colIndx the colIndx to set
	 */
	public void setColIndx(int colIndx);

	/**
	 * @param isIdenty the isIdenty to set
	 */
	public abstract void setIdenty(boolean isIdenty);

	/**
	 * @return the isIdenty
	 */
	public abstract boolean isIdenty();

}