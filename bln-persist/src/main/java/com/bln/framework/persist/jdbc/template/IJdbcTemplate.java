package com.bln.framework.persist.jdbc.template;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.bln.framework.persist.jdbc.template.storeprocedure.IStoreProcedureParameters;
import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureResult;
import com.bln.framework.persist.tablestru.component.column.IColumn;
public interface IJdbcTemplate <T>{

	/**
	 * 查询方法,按指定的列名返回结果集
	 * @param conn 当前数据库连接
	 * @param sql SQL语句
	 * @param params 参数对象
	 * @param columns 需要查询的字段
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public List<T> query(Connection conn, String sql, List<Object> params, IColumn[] columns) throws SQLException, IOException;

	/**
	 * 查询方法，返回一个值。适用于只返回一行一个字段的SQL。注意SQL语句的动态SQL中的问号必须同params一一对应。
	 * @param conn 当前数据库连接
	 * @param sql 动态SELECT语句
	 * @param params 条件参数
	 * @return Object 返回指定的类型
	 * @throws SQLException 
	 */
	public abstract String queryForString(Connection conn, String sql, List<Object> params)
			throws SQLException;

	/**
	 * 执行更新、插入或删除操作
	 * @param conn 当前数据库连接
	 * @param sql
	 * @param params 参数数组
	 * @return 更新成功的条数
	 * @throws SQLException 
	 * @throws GeneralException 
	 */
	public abstract int update(Connection conn, String sql, List<Object> params) throws SQLException;

	/**
	 * 执行更新、插入或删除操作
	 * @param sql
	 * @param params 参数数组
	 * @param generatedCols 自动生成的字段
	 * @param generatedVals 自动生成的值
	 * @return 返回更新成功的条数。
	 * @throws SQLException 
	 */
	public int update(Connection conn, String sql, List<Object> params, int[] generatedCols, Object[] generatedVals) throws SQLException;
	
	/**
	 * 没有参数的更新语句执行批量数据库更新操作，包括update、insert和delete操作
	 * @param conn 数据库连接
	 * @param sqls 要执行的SQL语句数组
	 * @return 每条SQL语句执行所影响的行数
	 * @throws SQLException
	 */
	public abstract int[] updateBatch(Connection conn, String[] sqls) throws SQLException;

	/**
	 * 带参数的更新语句执行批量数据库更新操作，包括update、insert和delete操作
	 * @param conn 数据库连接
	 * @param sql 要执行的SQL语句
	 * @param paramsList 不同的参数
	 * @return 每条SQL语句执行所影响的行数
	 * @throws SQLException
	 */
	public int[] updateBatch(Connection conn, String sql, List<List<Object>> paramsList) throws SQLException;
	
	/**
	 * 执行存储过程
	 * @param conn 数据库连接
	 * @param spCommand 存储过程命令
	 * @param spParameters 存储过程参数，需要和spCommand一致
	 * @return 返回存储过程结果
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public StoreProcedureResult<T> executeStoreProcedure(Connection conn, String spCommand, IStoreProcedureParameters spParameters) throws SQLException, IOException;

	public abstract StoreProcedureResult<T> executeStoreProcedureSimple(Connection conn,
			String spCommand, IStoreProcedureParameters spParameters) throws SQLException, IOException;

}