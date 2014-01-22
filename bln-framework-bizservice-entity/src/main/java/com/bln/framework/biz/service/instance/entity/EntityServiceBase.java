/**
 * @author gengw
 * created at 2013-10-08
 */
package com.bln.framework.biz.service.instance.entity;

import com.bln.framework.biz.module.entity.lib.IEntityLib;
import com.bln.framework.biz.service.instance.ServiceBase;

/**
 * ʵ��������
 */
public abstract class EntityServiceBase extends ServiceBase{

	/**
	 * ʵ���
	 */
	protected IEntityLib entityLib = null;

	/**
	 * �������
	 */
	protected String tableName = null;

	/**
	 * �ӱ����
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
