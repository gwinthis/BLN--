/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.sql.template.builder;

import org.apache.commons.beanutils.BeanUtils;

import com.bln.framework.persist.sql.entity.ISqlEntity;
import com.bln.framework.persist.sql.template.ISQLTemplate;
import com.bln.framework.persist.sql.template.ISQLTemplateBuildable;
import com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder;
import com.bln.framework.persist.sql.template.exception.SqlTBuildException;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.util.asserter.Assert;

/**
 * ģ�幹����
 */
public class SqlTBuilder extends SqlTBuilderBase{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.ISqlTBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public ISQLTemplate build(ITableStru tableStru) {
		
		//1.У�����
		Assert.paramIsNotNull(tableStru, "tableStru");
		
		//2.����SQLT
		ISQLTemplateBuildable sqlt = this.sqlt.clone();
		
		//2.1�������
		String tableNm = tableStru.getTableName();
		sqlt.setTableName(tableNm);
		
		//2.2�����ṹ
		sqlt.setTableStru(tableStru);
				
		//2.10����SQL���
		this.assembleSql(sqlt, tableStru);
		
		//3.����sqlt
		return sqlt.buildToSqlt();
	}
	
	/**
	 * װ��SQL
	 */
	protected void assembleSql(ISQLTemplateBuildable sqlt, ITableStru tableStru){
		
		if(sqlBuilders == null || sqlBuilders.length <= 0){
			return;
		}
		
		for ( ISqlBuilder<? extends ISqlEntity<?>> sqlBuilder : sqlBuilders){
			
			Object sqlEntity = sqlBuilder.build(tableStru);
			String sqlType = sqlBuilder.getSqlType();
			
			try {
				BeanUtils.setProperty(sqlt, sqlType, sqlEntity);
			} catch (Throwable e) {
				SqlTBuildException stbe = new SqlTBuildException("when sqlt is assembled sql occus error!");
				
				stbe.addContextValue("sqlt", sqlt);
				stbe.addContextValue("sqlType", sqlType);
				stbe.addContextValue("sql", sqlEntity);
				
				stbe.initCause(e);
				throw stbe;
			}
		}
	}

}
