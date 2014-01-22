/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.sql.template.builder.sql;

import com.bln.framework.base.BaseObj;
import com.bln.framework.persist.sql.dialect.ISQLDialect;
import com.bln.framework.persist.sql.entity.ISqlEntity;
import com.bln.framework.persist.valgenerator.lib.IColValGeneratorLib;

/**
 * SQLBuilder基类
 */
public abstract class SqlBuilderBase<T extends ISqlEntity<T>> extends BaseObj implements ISqlBuilder<T>{

	/**
	 * 要生成的SQL类型
	 */
	protected String sqlType = null;
	
	/**
	 * SQL语句方言
	 */
	protected ISQLDialect sqlDialect = null;

	/**
	 * 生成器库
	 */
	protected IColValGeneratorLib valGeneratorLib = null;
	
	/**
	 * @return the sqlDialect
	 */
	public ISQLDialect getSqlDialect() {
		return sqlDialect;
	}

	/**
	 * @param sqlDialect the sqlDialect to set
	 */
	public void setSqlDialect(ISQLDialect sqlDialect) {
		this.sqlDialect = sqlDialect;
	}

	/**
	 * @return the sqlType
	 */
	public String getSqlType() {
		return sqlType;
	}

	/**
	 * @param sqlType the sqlType to set
	 */
	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}
	
	/**
	 * @return the valGeneratorLib
	 */
	public IColValGeneratorLib getValGeneratorLib() {
		return valGeneratorLib;
	}

	/**
	 * @param valGeneratorLib the valGeneratorLib to set
	 */
	public void setValGeneratorLib(IColValGeneratorLib valGeneratorLib) {
		this.valGeneratorLib = valGeneratorLib;
	}
}
