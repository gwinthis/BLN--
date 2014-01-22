/**
 * @author gengw
 * Created at 2011-12-14
 */
package com.bln.framework.persist.tablestru.component.fk;

import java.util.Arrays;

import com.bln.framework.base.BaseObj;
import com.bln.framework.util.pair.IPair;

/**
 * 外键类
 */
public class ForeignKey extends BaseObj implements IForeignKey {
	
	/**
	 * 外键名称
	 */
	String name = null;
	
	/**
	 * 关联表表名
	 */
	String refTableName = null;
	
	/**
	 * 关联字段名称
	 */
	IPair<String, String>[] refColNames = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.fk.IForeignKey#getRefTableName()
	 */
	public String getRefTableName() {
		return refTableName;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.fk.IForeignKey#setRefTableName(java.lang.String)
	 */
	public void setRefTableName(String refTableName) {
		this.refTableName = refTableName;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.fk.IForeignKey#getRefColNames()
	 */
	public IPair<String, String>[] getRefColNames() {
		return refColNames;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.fk.IForeignKey#setRefColNames(com.bln.framework.util.pair.IPair)
	 */
	public void setRefColNames(IPair<String, String>[] refColNames) {
		this.refColNames = refColNames;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder info = new StringBuilder("ForeignKey ");
		
		//name
		info.append(name).append(" info : \r\n");

		//refTableName
		info.append("refTableName: ").append(refTableName).append("\r\n");

		//refColNames
		info.append("refColNames: ").append(Arrays.toString(refColNames)).append("\r\n");
			
		//返回结果
		return info.toString();		
	}
	
}
