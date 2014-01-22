/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.sql.template.builder;

import com.bln.framework.base.BaseObj;
import com.bln.framework.persist.sql.entity.ISqlEntity;
import com.bln.framework.persist.sql.template.ISQLTemplateBuildable;
import com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder;

/**
 * 模板构造器基类
 */
public abstract class SqlTBuilderBase extends BaseObj implements ISqlTBuilder{
	
	/**
	 * sqlt模板
	 */
	protected ISQLTemplateBuildable sqlt = null;
	
	
	/**
	 * SQL语句构造器
	 */
	protected ISqlBuilder<? extends ISqlEntity<?>>[] sqlBuilders = null;


	/**
	 * @param sqlt the sqlt to set
	 */
	public void setSqlt(ISQLTemplateBuildable sqlt) {
		this.sqlt = sqlt;
	}


	/**
	 * @param sqlBuilders the sqlBuilders to set
	 */
	public void setSqlBuilders(ISqlBuilder<? extends ISqlEntity<?>>[] sqlBuilders) {
		this.sqlBuilders = sqlBuilders;
	}

}
