package com.bln.framework.persist.sql.template.builder;

import com.bln.framework.builder.IBuilder1Step;
import com.bln.framework.persist.sql.template.ISQLTemplate;
import com.bln.framework.persist.tablestru.ITableStru;

/**
 * SQLT构造器
 */
public interface ISqlTBuilder extends IBuilder1Step<ISQLTemplate, ITableStru>{

	/**
	 * 生成表结构对象
	 * @return
	 */
	//public abstract ISQLTemplate build(ITableStru tableStru);

}