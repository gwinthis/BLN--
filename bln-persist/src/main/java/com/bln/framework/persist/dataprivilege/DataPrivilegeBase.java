/**
 * @author gengw
 * Created on Mar 8, 2013
 */
package com.bln.framework.persist.dataprivilege;

import com.bln.framework.persist.dataprivilege.exception.DataPrivilegeException;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;

/**
 * 数据权限基类
 */
public abstract class DataPrivilegeBase implements IDataPrivilege{

	/**
	 * 表名
	 */
	protected String tableName = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.dataprivilege.IDataPrivilege#getDataPrivilege()
	 */
	public IConditionClause getDataPrivilege()throws DataPrivilegeException{
		return getDataPrivilege(tableName);
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
