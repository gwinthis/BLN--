package com.bln.framework.persist.sql.entity.update;

import com.bln.framework.persist.sql.entity.SqlEntity;

public class UpdateSqlEntity extends SqlEntity<IUpdateSqlEntity> implements IUpdateSqlEntity{

	/**
	 * °æ±¾ºÅ¸üÐÂÓï¾ä
	 */
	protected String versionColumnClause = null;

	/**
	 * @return the versionColumnClause
	 */
	public String getVersionColumnClause() {
		return versionColumnClause;
	}

	/**
	 * @param versionColumnClause the versionColumnClause to set
	 */
	public void setVersionColumnClause(String versionColumnClause) {
		this.versionColumnClause = versionColumnClause;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder info = new StringBuilder(super.toString());
		info.append("versionColumnClause : ").append(versionColumnClause).append("\r\n");
		
		return info.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public IUpdateSqlEntity clone(){
		
		IUpdateSqlEntity updateSqlEntity = super.clone();
		
		updateSqlEntity.setVersionColumnClause(versionColumnClause);
		return updateSqlEntity;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.SqlEntity#newInstance()
	 */
	@Override
	protected IUpdateSqlEntity newInstance() {
		return new UpdateSqlEntity();
	}
}
