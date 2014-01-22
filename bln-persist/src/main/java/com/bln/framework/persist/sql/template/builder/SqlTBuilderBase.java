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
 * ģ�幹��������
 */
public abstract class SqlTBuilderBase extends BaseObj implements ISqlTBuilder{
	
	/**
	 * sqltģ��
	 */
	protected ISQLTemplateBuildable sqlt = null;
	
	
	/**
	 * SQL��乹����
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
