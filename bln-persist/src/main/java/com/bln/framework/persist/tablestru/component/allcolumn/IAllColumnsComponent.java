package com.bln.framework.persist.tablestru.component.allcolumn;

import java.util.Map;

import com.bln.framework.persist.tablestru.component.column.IColumn;

public interface IAllColumnsComponent {

	/**
	 * @return the columns
	 */
	public abstract IColumn[] getColumns();

	/**
	 * @param columns the columns to set
	 */
	public abstract void setColumns(IColumn[] columns);

	/**
	 * @return the orderColNames
	 */
	public abstract String[] getOrderColNames();

	/**
	 * @param orderColNames the orderColNames to set
	 */
	public abstract void setOrderColNames(String[] orderColNames);

	/**
	 * @return the colNames
	 */
	public abstract String[] getColNames();

	/**
	 * @param colNames the colNames to set
	 */
	public abstract void setColNames(String[] colNames);

	/**
	 * @return the colNamesAsStr
	 */
	public abstract String getColNamesAsStr();

	/**
	 * @param colNamesAsStr the colNamesAsStr to set
	 */
	public abstract void setColNamesAsStr(String colNamesAsStr);

	/**
	 * @return the colNamesWithTableNm
	 */
	public abstract String getColNamesWithTableNm();

	/**
	 * @param colNamesWithTableNm the colNamesWithTableNm to set
	 */
	public abstract void setColNamesWithTableNm(String colNamesWithTableNm);

	/**
	 * @return the orderColumns
	 */
	public IColumn[] getOrderColumns();

	/**
	 * @param orderColumns the orderColumns to set
	 */
	public void setOrderColumns(IColumn[] orderColumns);

	/**
	 * @return the orderColumnNoIds
	 */
	public IColumn[] getOrderColumnNoIds();

	/**
	 * @param orderColumnNoIds the orderColumnNoIds to set
	 */
	public void setOrderColumnNoIds(IColumn[] orderColumnNoIds);

	/**
	 * @return the columnTypeMap
	 */
	public Map<String, Integer> getColumnTypeMap();

	/**
	 * @param columnTypeMap the columnTypeMap to set
	 */
	public void setColumnTypeMap(Map<String, Integer> columnTypeMap);

}