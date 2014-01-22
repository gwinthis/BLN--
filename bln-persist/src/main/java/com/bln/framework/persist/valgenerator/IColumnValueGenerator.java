package com.bln.framework.persist.valgenerator;

import java.sql.Connection;
import java.sql.SQLException;

import com.bln.framework.persist.tablestru.param.IParam;

public interface IColumnValueGenerator {

	/**
	 * 序列器类型的生成器
	 */
	public static final String GENERATOR_TYPE_SEQ = "sequence";

	/**
	 * 数值类型版本号的生成器
	 */
	public static final String GENERATOR_TYPE_NUMERIC_VERSION = "numeric_version";

	
	/**
	 * 时间戳类型版本号的生成器
	 */
	public static final String GENERATOR_TYPE_TIMESTAMP_VERSION = "timestamp_version";

	/**
	 * 在SQL语句中通过函数生成值
	 * @param params 参数
	 * @param currVal TODO
	 * @return 子句
	 */
	public abstract String getValClause(IParam[] params, Object currVal);

	/**
	 * 获得自动生成的值
	 * @param conn 数据库连接
	 * @param params 参数数组
	 * @param currVal TODO
	 * @return 生成的值
	 * @throws SQLException
	 */
	public Object getVal(Connection conn, IParam[] params, Object currVal) throws SQLException;

	/**
	 * @return the surportColValWithDbFun
	 */
	public boolean isSurportColValWithDbFun();


}