/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.component.column;

import com.bln.framework.persist.jdbc.EJdbcType;
import com.bln.framework.persist.tablestru.component.column.generator.IGenerator;

/**
 * 表的字段类型
 */
public class Column implements IColumn, Comparable<Object> {
	
	/**
	 * 字段名称
	 */
	protected String colName = null;
	
	/**
	 * 字段顺序号-从1开始
	 */
	protected int colIndx = 0;
	
	/**
	 * 字段类型
	 */
	protected int colType = 0;

	/**
	 * 字段生成器
	 */
	protected IGenerator generator = null;

	/**
	 * 默认值
	 */
	protected String defaultVal = null;
	
	/**
	 * 是否identy字段
	 */
	protected boolean isIdenty = false;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.column.IColumn#getColName()
	 */
	public String getColName() {
		return colName;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.column.IColumn#setColName(java.lang.String)
	 */
	public void setColName(String colName) {
		this.colName = colName;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.column.IColumn#getColType()
	 */
	public int getColType() {
		return colType;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.column.IColumn#setColType(int)
	 */
	public void setColType(int colType) {
		this.colType = colType;
	}


	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.column.id.IColumn4ID#getIdGenerator()
	 */
	public IGenerator getGenerator() {
		return generator;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.column.id.IColumn4ID#setIdGenerator(com.bln.framework.persist.tablemodule.column.id.IDGenerator)
	 */
	public void setGenerator(IGenerator generator) {
		this.generator = generator;
	}
	
	/**
	 * @return the defaultVal
	 */
	public String getDefaultVal() {
		return defaultVal;
	}

	/**
	 * @param defaultVal the defaultVal to set
	 */
	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	/**
	 * @return the colIndx
	 */
	public int getColIndx() {
		return colIndx;
	}

	/**
	 * @param colIndx the colIndx to set
	 */
	public void setColIndx(int colIndx) {
		this.colIndx = colIndx;
	}
	

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.component.column.IColumn#isIdenty()
	 */
	public boolean isIdenty() {
		return isIdenty;
	}

	public void setIdenty(boolean isIdenty) {
		this.isIdenty = isIdenty;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder info = new StringBuilder("Column ");
		
		//tableName
		info.append(colName).append(" info : \r\n");
		
		//colType
		info.append("colType: ").append(EJdbcType.toString(colType)).append("\r\n");
		
		//generator
		info.append("generator: ").append(generator).append("\r\n");

		//isIdenty
		info.append("isIdenty: ").append(isIdenty).append("\r\n");
		
		//返回结果
		return info.toString();		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colName == null) ? 0 : colName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		final Column other = (Column) obj;
		if (colName == null) {
			if (other.colName != null)
				return false;
		} else if (!colName.equals(other.colName))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		if( o == null){
			return 1;
		}
		
		if(o instanceof String){
			return this.getColName().compareTo((String)o);
		}else if( o instanceof IColumn){
			return this.getColName().compareTo(((IColumn)o).getColName());
		}
		
		return 1;
	}

}
