package com.bln.framework.biz.module.entity.listener;

import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

public interface IEntityListener {

	/**
	 * 查询之前发生的事件
	 * @param conClause 查询条件
	 * @param pagination 分页对象
	 * @param orderby 排序信息
	 */
	public abstract void beforeQueryEvt(IConditionClause conClause,
			IPagination pagination, String orderby);

	/**
	 * 查询之后发生的事件
	 * @param rows 查询出的记录
	 * @return 处理之后的记录
	 */
	public abstract List<IRow> afterQueryEvt(List<IRow> rows);

	/**
	 * 插入记录之前发生的事件
	 * @param row 记录
	 * @return 处理之后的记录
	 */
	public abstract IRow beforeInsertEvt(IRow row);

	/**
	 * 插入记录之后发生的事件
	 * @param row 记录
	 * @return 处理之后的记录
	 */
	public abstract IRow afterInsertEvt(IRow row);

	/**
	 * 更新记录之前发生的事件
	 * @param row 记录
	 * @return 处理之后的记录
	 */
	public abstract IRow beforeUpdateEvt(IRow row);

	/**
	 * 更新记录之后发生的事件
	 * @param row 记录
	 * @return 处理之后的记录
	 */
	public abstract IRow afterUpdateEvt(IRow row);

	/**
	 * 删除记录之前发生的事件
	 * @param row 记录
	 * @return 处理之后的记录
	 */
	public abstract IRow beforeDeleteEvt(IRow row);

	/**
	 * 删除记录之后发生的事件
	 * @param row 记录
	 * @return 处理之后的记录
	 */
	public abstract IRow afterDeleteEvt(IRow row);

}