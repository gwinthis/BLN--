package com.bln.framework.persist.session.executor.material.condition;

import java.util.List;

public interface IConditionClause {

	/**
	 * 条件语句和参数值一起设定
	 * @param con
	 * @param instance
	 * @throws GeneralException 
	 */
	public abstract IConditionClause addConValue(String con, Object obj);

	/**
	 * 设置条件语句
	 * @param str 条件语句
	 */
	public abstract IConditionClause addCondition(String str);

	/**
	 * 从左侧添加条件语句
	 * @param str 条件语句
	 */
	public abstract IConditionClause addConditionAtHead(String con, String opr);

	/**
	 * 设置值对象
	 * @param Object 对应条件语句中的参数
	 */
	public abstract IConditionClause addValue(Object obj);

	/**
	 * 添加多个值对象
	 * @param Object 对应条件语句中的参数
	 */
	public abstract IConditionClause addValues(Object[] objs);

	/**
	 * 添加多个值对象
	 * @param Object 对应条件语句中的参数
	 */
	public abstract IConditionClause addValues(List<Object> objs);

	/**
	 * 在原有的基础上添加新的条件对象
	 * @param newCon 新的条件对象
	 * @param linkOpr 关联操作符
	 * @return 关联后的条件对象
	 * @throws Throwable
	 */
	public abstract IConditionClause addConClause(IConditionClause newConClause,
			String linkOpr);

	/**
	 * 获取条件语句
	 * @return
	 */
	public abstract String getCondtion();

	/**
	 * 获取参数List
	 * @return
	 */
	public abstract List<Object> getValues();

	/**
	 * 获取参数数组
	 * @return
	 */
	public abstract Object[] getValuesAsArray();

}