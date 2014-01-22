package com.bln.framework.persist.sql.template;

import com.bln.framework.persist.jdbc.converter.util.IConverterUtil;
import com.bln.framework.persist.sql.dialect.ISQLDialect;
import com.bln.framework.persist.sql.entity.delete.IDeleteSqlEntity;
import com.bln.framework.persist.sql.entity.insert.IInsertSqlEntity;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.persist.sql.entity.update.IUpdateSqlEntity;
import com.bln.framework.persist.tablestru.ITableStru;

public interface ISQLTemplateBuildable {

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setSqlDialect(com.bln.framework.persist.sql.template.sqldialect.ISQLDialect)
	 */
	public abstract void setSqlDialect(ISQLDialect sqlDialect);

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setTableName(java.lang.String)
	 */
	public abstract void setTableName(String tableName);

	/**
	 * @param insertSql the insertSql to set
	 */
	public abstract void setInsertSql(IInsertSqlEntity insertSql);

	/**
	 * @param deleteSql the deleteSql to set
	 */
	public abstract void setDeleteSql(IDeleteSqlEntity deleteSql);

	/**
	 * @param updateSql the updateSql to set
	 */
	public abstract void setUpdateSql(IUpdateSqlEntity updateSql);

	/**
	 * @param selectSql the selectSql to set
	 */
	public abstract void setSelectSql(ISelectSqlEntity selectSql);

	/**
	 * @param tableStru the tableStru to set
	 */
	public abstract void setTableStru(ITableStru tableStru);

	/**
	 * @param converterUtil the converterUtil to set
	 */
	public abstract void setConverterUtil(IConverterUtil converterUtil);

	/**
	 * 转换成SQLT对象
	 * @return ISQLTemplate
	 */
	public ISQLTemplate buildToSqlt();
	
	public ISQLTemplateBuildable clone();

}