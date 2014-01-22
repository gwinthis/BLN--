package com.bln.framework.persist.dataprivilege;

import com.bln.framework.persist.dataprivilege.exception.DataPrivilegeException;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;

public interface IDataPrivilege {
	
	/**
	 * 获取查询数据权限
	 * @return String Where条件
	 */
	public abstract IConditionClause getDataPrivilege()throws DataPrivilegeException;

	/**
	 * 获取查询数据权限，使用表别名
	 * @param tableAlias
	 * @return ConditionClause
	 * @throws GeneralException
	 */
	public IConditionClause getDataPrivilege(String tableAlias) throws DataPrivilegeException;

	/**
	 * 获得表名
	 * @return String
	 */
	public String getTableName();
	
	/**
	 * 设置表名
	 * @param tableName
	 * @return
	 */
	public void setTableName(String tableName);
}