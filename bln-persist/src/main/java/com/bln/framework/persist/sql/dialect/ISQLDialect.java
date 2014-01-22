package com.bln.framework.persist.sql.dialect;

import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;

public interface ISQLDialect {

	/**
	 * 是否存在SQL
	 * @param tableName 表名
	 * @param condition 条件
	 * @return SQL语句
	 */
	public abstract String isExistsSql(String schemaName, String tableName, String condition);

	/**
	 * 获取新序列号的SQL
	 * @param seqNm 序列器
	 * @return SQL语句
	 */
	public abstract String newSeqNoSql(String seqNm);

	/**
	 * 查询之后立即锁定的SQL
	 * @param selectSql
	 * @return
	 */
	public abstract String queryForUpdateSql(String selectSql);

	/**
	 * 记录数合计SQL
	 * @param tableName
	 * @param condition
	 * @param idName 
	 * @return
	 */
	public String countSql(String schemaName, String tableName, String condition, String idName);

	/**
	 * 视图的记录数合计
	 * @param schemaName
	 * @param view
	 * @return
	 */
	public String countViewSql(String schemaName, String view);
	
	/**
	 * 获得数据库时间
	 * @return
	 */
	public String getDbDateSql();

	/**
	 * insert语句
	 * @param tableName
	 * @param colNames
	 * @param colValues
	 * @return
	 */
	public String insertSql(String schemaName, String tableName, String[] colNames, String colValues);

	/**
	 * update语句
	 * @param tableName
	 * @param colNames
	 * @param whereCal
	 * @return
	 */
	public String updateSql(String schemaName, String tableName, String[] colNames, String verColName, String verClause, String whereCal);

	/**
	 * delete语句
	 * @param tableName
	 * @param whereCal
	 * @return
	 */
	public String deleteSql(String schemaName, String tableName, String whereCal);

	/**
	 * select语句
	 * @param tableName
	 * @param colNames
	 * @param whereCal
	 * @return
	 */
	public String selectSql(String schemaName, String tableName, String [] colNames, String whereCal, String groupby, String orderby);

	/**
	 * 分页处理
	 * @param selectSqlEntity 要处理的selectSQL
	 * @param pagination 分页信息
	 */
	public void pagination(ISelectSqlEntity selectSqlEntity, IPagination pagination);

	
	
}