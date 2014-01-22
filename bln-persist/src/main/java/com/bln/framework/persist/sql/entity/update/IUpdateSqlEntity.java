package com.bln.framework.persist.sql.entity.update;

import com.bln.framework.persist.sql.entity.ISqlEntity;

public interface IUpdateSqlEntity extends ISqlEntity<IUpdateSqlEntity>{

	/**
	 * @return the versionColumnClause
	 */
	public String getVersionColumnClause();

	/**
	 * @param versionColumnClause the versionColumnClause to set
	 */
	public void setVersionColumnClause(String versionColumnClause);

}