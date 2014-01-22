package com.bln.framework.persist.sql.template;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.sql.entity.delete.IDeleteSqlEntity;
import com.bln.framework.persist.sql.entity.insert.IInsertSqlEntity;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.persist.sql.entity.update.IUpdateSqlEntity;

public interface ISQLTemplate {
	
	/**
	 * 获取查询并且直接锁定相关记录的SQL
	 * @param selectSqlEntity 需要锁定的SELECT语句
	 */
	public void queryForUpdateSql(ISelectSqlEntity selectSqlEntity);

	/**
	 * 
	 * @param colNamesInTime
	 * @param condition
	 * @return
	 */
	/**
	 * 根据即时列名更新数据
	 * @param row 当前要更新的字段
	 * @param condition 条件
	 * @return
	 */
	public IUpdateSqlEntity updateSqlInTime(IRow row, IConditionClause condition);

	/**
	 * 关联其他表查询
	 * @param cols 要查询的字段
	 * @param condition 条件
	 * @param groupby 分组语句
	 * @param orderby 排序语句
	 * @param othTables 其他表的表名
	 * @return sql语句
	 */
	public String partColSelectSql(String[] cols, String condition, String groupby,
			String orderby, String othTables);

	/**
	 * 获得Insert Sql语句
	 * @param row 行对象
	 * @return InsertSql实体
	 */
	public IInsertSqlEntity insertSql(IRow row);


	/**
	 * 构造ISelectSqlEntity
	 * @param conClause 条件信息
	 * @param pagination 分页信息
	 * @param orderby 排序信息
	 * @return 返回满足条件的记录
	 */
	public ISelectSqlEntity selectSql(IConditionClause conClause, IPagination pagination, String orderby);

	/**
	 * @return the insertSql
	 */
	public IInsertSqlEntity getInsertSql();

	/**
	 * @return the updateSql
	 */
	public IUpdateSqlEntity getUpdateSql();

	/**
	 * 生成删除SQL语句实体
	 * @param condition 条件
	 * @return 删除SQL语句实体
	 */
	public IDeleteSqlEntity deleteSql(IConditionClause condition);

	/**
	 * 计算行数的SQL
	 * @param conClause 条件语句
	 * @return 计算行数的SQL
	 */
	public ISelectSqlEntity countSql(IConditionClause conClause);

	/**
	 * 是否存在符合条件的记录SQL
	 * @param conClause 条件语句
	 * @return 是否存在符合条件的记录SQL
	 */
	public ISelectSqlEntity isExistsSql(IConditionClause conClause);
	
}