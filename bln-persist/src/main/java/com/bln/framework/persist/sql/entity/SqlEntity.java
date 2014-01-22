package com.bln.framework.persist.sql.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bln.framework.base.BaseObj;

/**
 * SQL实体
 */
public abstract class SqlEntity<T extends ISqlEntity<T>> extends BaseObj implements ISqlEntity<T>{
	
	/**
	 * SQL语句字面量
	 */
	protected String sqlInfo = null;
	
	/**
	 * SQL语句所使用的参数
	 */
	protected List<Object> params = null;
	
	/**
	 * 所涉及的表名
	 */
	protected String[] tableNames = null;
	
	public SqlEntity(String sqlInfo, List<Object> params, String[] tableNames) {
		super();
		this.sqlInfo = sqlInfo;
		this.params = params;
		this.tableNames = tableNames;
	}

	public SqlEntity() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.IISqlEntity#getSqlInfo()
	 */
	public String getSqlInfo() {
		return sqlInfo;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.IISqlEntity#setSqlInfo(java.lang.String)
	 */
	public void setSqlInfo(String sqlInfo) {
		this.sqlInfo = sqlInfo;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.IISqlEntity#getParams()
	 */
	public List<Object> getParams() {
		return params;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.IISqlEntity#setParams(java.util.List)
	 */
	public void setParams(List<Object> params) {
		this.params = params;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.ISqlEntity#setParams(java.lang.Object[])
	 */
	public void addParams(Object...param){
		if(params == null){
			this.params = new ArrayList<Object>();
		}
		this.params.addAll(Arrays.asList(param));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder info = new StringBuilder(this.getClass().getName() + " info: ");
		
		info.append("sqlInfo : ").append(sqlInfo).append("\r\n");
		info.append("params : ").append(params).append("\r\n");
		
		return info.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public T clone() {
		
		T sqlEntity = newInstance();
		
		sqlEntity.setTableName(tableNames);
		sqlEntity.setSqlInfo(sqlInfo);
		sqlEntity.setParams(params);

		return sqlEntity;
	}
	
	/**
	 * 新建实例
	 * @return
	 */
	protected abstract T newInstance();

	/**
	 * @return the tableName
	 */
	public String[] getTableNames() {
		return tableNames;
	}

	/**
	 * @param tableNames the tableName to set
	 */
	public void setTableName(String[] tableNames) {
		this.tableNames = tableNames;
	}

}
