package com.bln.framework.persist.sql.template.builder.sql;

import com.bln.framework.builder.IBuilder1Step;
import com.bln.framework.persist.sql.entity.ISqlEntity;
import com.bln.framework.persist.tablestru.ITableStru;

/**
 * SQL语句构造器
 */
public interface ISqlBuilder<T extends ISqlEntity<T>> extends IBuilder1Step<T, ITableStru>{

	/**
	 * 生成SQL语句
	 * @return
	 */
	public abstract T build(ITableStru tableStru);

	/**
	 * @return the sqlType
	 */
	public String getSqlType();

}