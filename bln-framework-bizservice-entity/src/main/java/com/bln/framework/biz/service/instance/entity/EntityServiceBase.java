/**
 * @author gengw
 * created at 2013-10-08
 */
package com.bln.framework.biz.service.instance.entity;

import com.bln.framework.biz.module.entity.lib.IEntityLib;
import com.bln.framework.biz.service.instance.ServiceBase;

/**
 * 实体服务基类
 */
public abstract class EntityServiceBase extends ServiceBase{

	/**
	 * 实体库
	 */
	protected IEntityLib entityLib = null;

	/**
	 * 主表表名
	 */
	protected String tableName = null;

	/**
	 * 子表表名
	 */
	protected String[] childTableNames = null;
	
	public IEntityLib getEntityLib() {
		return entityLib;
	}

	public void setEntityLib(IEntityLib entityLib) {
		this.entityLib = entityLib;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the childTableNames
	 */
	public String[] getChildTableNames() {
		return childTableNames;
	}

	/**
	 * @param childTableNames the childTableNames to set
	 */
	public void setChildTableNames(String[] childTableNames) {
		this.childTableNames = childTableNames;
	}
}
