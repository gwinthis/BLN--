package com.bln.framework.biz.module.parsecondition;

import java.text.ParseException;
import java.util.Map;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.ConditionClause;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.tablestru.ITableStru;

public interface IParseCondition {

	/**
	 * 根据表名解析条件对象
	 * @param condition 条件
	 * @param tableStru 需要查询的表的结构
	 * @param conField2ColMap 条件字段对应表字段的映射
	 * @param tableAlias 表别名
	 * @return 条件对象
	 * @throws ParseException
	 */
	public abstract IConditionClause parseQueryCondition(IRow condition, ITableStru tableStru,
			Map<String, String> conField2ColMap, String tableAlias)
			throws ParseException;

	/**
	 * 解析主键条件
	 * @param condition 查询条件
	 * @param tableStru 需要查询的表的结构
	 * @return 包含所有主键的条件对象
	 * @throws Throwable
	 */
	public IConditionClause parseAllIdsCondition(IRow condition, ITableStru tableStru) throws ParseException;

	/**
	 * 解析子表条件
	 * @param condition 查询条件
	 * @param tableStru 主表的结构
	 * @param childTableName 子表表名
	 * @return 子表条件
	 * @throws Throwable
	 */
	public ConditionClause parseChildReferCondition(IRow condition, ITableStru tableStru,
			String childTableName)throws ParseException;

}