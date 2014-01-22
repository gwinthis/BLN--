/**
 * @author gengw
 * Created on Apr 20, 2012
 */
package com.bln.framework.persist.tablestru.component.allcolumn;

import java.util.Arrays;
import java.util.Map;

import com.bln.framework.persist.tablestru.component.column.IColumn;

/**
 * 所有字段的实体
 */
public class AllColumnsComponent implements IAllColumnsComponent {
	
	/**
	 * 表中的字段实体
	 */
	protected IColumn[] columns = null;

	/**
	 * 表中的字段实体,并且已排序
	 */
	protected IColumn[] orderColumns = null;

	/**
	 * 表中去除主键的字段实体,并且已排序
	 */
	public IColumn[] orderColumnNoIds = null;
	
	/**
	 * 表的所有字段，按数组形式存储，并且已排序
	 */
	protected String[] orderColNames = null;
	
	/**
	 * 表的所有字段，按数组形式存储
	 */
	protected String[] colNames = null;
	
	/**
	 * 表的所有字段，字段之间按逗号分隔
	 */
	protected String colNamesAsStr = null;
	
	/**
	 * 表的所有字段，字段名有表名的前缀，字段之间按逗号分隔
	 */
	protected String colNamesWithTableNm = null;

	/**
	 * 字段类型Map
	 */
	protected Map<String, Integer> columnTypeMap = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnComponent#getColumns()
	 */
	public IColumn[] getColumns() {
		return columns;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnComponent#setColumns(com.bln.framework.persist.tablestru.component.column.IColumn[])
	 */
	public void setColumns(IColumn[] columns) {
		this.columns = columns;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnComponent#getOrderColNames()
	 */
	public String[] getOrderColNames() {
		return orderColNames;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnComponent#setOrderColNames(java.lang.String[])
	 */
	public void setOrderColNames(String[] orderColNames) {
		this.orderColNames = orderColNames;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnComponent#getColNames()
	 */
	public String[] getColNames() {
		return colNames;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnComponent#setColNames(java.lang.String[])
	 */
	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnComponent#getColNamesAsStr()
	 */
	public String getColNamesAsStr() {
		return colNamesAsStr;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnComponent#setColNamesAsStr(java.lang.String)
	 */
	public void setColNamesAsStr(String colNamesAsStr) {
		this.colNamesAsStr = colNamesAsStr;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnComponent#getColNamesWithTableNm()
	 */
	public String getColNamesWithTableNm() {
		return colNamesWithTableNm;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnComponent#setColNamesWithTableNm(java.lang.String)
	 */
	public void setColNamesWithTableNm(String colNamesWithTableNm) {
		this.colNamesWithTableNm = colNamesWithTableNm;
	}
	
	/**
	 * @return the orderColumns
	 */
	public IColumn[] getOrderColumns() {
		return orderColumns;
	}

	/**
	 * @param orderColumns the orderColumns to set
	 */
	public void setOrderColumns(IColumn[] orderColumns) {
		this.orderColumns = orderColumns;
	}

	/**
	 * @return the orderColumnNoIds
	 */
	public IColumn[] getOrderColumnNoIds() {
		return orderColumnNoIds;
	}

	/**
	 * @param orderColumnNoIds the orderColumnNoIds to set
	 */
	public void setOrderColumnNoIds(IColumn[] orderColumnNoIds) {
		this.orderColumnNoIds = orderColumnNoIds;
	}

	/**
	 * @return the columnTypeMap
	 */
	public Map<String, Integer> getColumnTypeMap() {
		return columnTypeMap;
	}

	/**
	 * @param columnTypeMap the columnTypeMap to set
	 */
	public void setColumnTypeMap(Map<String, Integer> columnTypeMap) {
		this.columnTypeMap = columnTypeMap;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder info = new StringBuilder("AllColumnsComponent info: ");
		
		//columns
		info.append("columns: ").append(Arrays.toString(columns)).append("\r\n");

		//colNames
		info.append("colNames: ").append(Arrays.toString(colNames)).append("\r\n");
		
		//orderColumnNoIds
		info.append("orderColumnNoIds: ").append(Arrays.toString(orderColumnNoIds)).append("\r\n");

		//orderColNames
		info.append("orderColNames: ").append(Arrays.toString(orderColNames)).append("\r\n");
		
		//colNamesAsStr
		info.append("colNamesAsStr: ").append(colNamesAsStr).append("\r\n");
		
		//colNamesWithTableNm
		info.append("colNamesWithTableNm: ").append(colNamesWithTableNm).append("\r\n");

		//columnTypeMap
		info.append("columnTypeMap: ").append(columnTypeMap).append("\r\n");

		//返回结果
		return info.toString();
	}

	

}
