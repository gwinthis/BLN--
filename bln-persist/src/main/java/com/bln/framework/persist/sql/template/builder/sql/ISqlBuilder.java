package com.bln.framework.persist.sql.template.builder.sql;

import com.bln.framework.builder.IBuilder1Step;
import com.bln.framework.persist.sql.entity.ISqlEntity;
import com.bln.framework.persist.tablestru.ITableStru;

/**
 * SQL��乹����
 */
public interface ISqlBuilder<T extends ISqlEntity<T>> extends IBuilder1Step<T, ITableStru>{

	/**
	 * ����SQL���
	 * @return
	 */
	public abstract T build(ITableStru tableStru);

	/**
	 * @return the sqlType
	 */
	public String getSqlType();

}