/**
 * @author gengw
 * Created on Apr 13, 2012
 */
package com.bln.framework.persist.session.executor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.bln.framework.persist.jdbc.converter.util.IConverterUtil;
import com.bln.framework.persist.jdbc.template.IJdbcTemplate;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.sql.dialect.ISQLDialect;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.persist.valgenerator.lib.IColValGeneratorLib;

/**
 * 会话执行器基类
 * @param <T> 克隆出的类型
 * @param <E> 实体类型
 */
public abstract class SessionExecutorBase<T extends SessionExecutorBase<T, E>, E> implements ISessionExecutor<E> {
	
	/**
	 * 数据库连接
	 */
	protected Connection conn = null;
	
	/**
	 * JDBC模板类
	 */
	protected IJdbcTemplate<E> jdbcTemplate = null;

	/**
	 * 值转换器
	 */
	protected IConverterUtil converterUtil = null;
	
	/**
	 * 生成器库
	 */
	protected IColValGeneratorLib valGeneratorLib = null;

	/**
	 * SQL方言
	 */
	protected ISQLDialect sqlDialect = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#setConn(java.sql.Connection)
	 */
	public void setConnection(Connection connection) {
		this.conn = connection;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.ISession#setJdbcTemplate(com.bln.framework.persist.jdbc.template.IJdbcTemplate)
	 */
	public void setJdbcTemplate(IJdbcTemplate<E> jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @param convertUtil the convertUtil to set
	 */
	public void setConverterUtil(IConverterUtil converterUtil) {
		this.converterUtil = converterUtil;
	}

	/**
	 * @param valGeneratorLib the valGeneratorLib to set
	 */
	public void setValGeneratorLib(IColValGeneratorLib valGeneratorLib) {
		this.valGeneratorLib = valGeneratorLib;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSessionBuildable#setSqlDialect(com.bln.framework.persist.sql.dialect.ISQLDialect)
	 */
	public void setSqlDialect(ISQLDialect sqlDialect) {
		this.sqlDialect = sqlDialect;
	}
		
	/**
	 * 处理分页
	 * @param selectSql 要执行的SQL
	 * @param pagination 分页信息
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected void pagination(ISelectSqlEntity selectSql, IPagination pagination) throws SQLException,IOException {
				
		//1.计算总行数和总页数
		String countSql = sqlDialect.countViewSql(null, new StringBuilder("(").append(selectSql.getSqlInfo()).append(") TEMP").toString());
		
		//执行计算总行数SQL
		String rowCount = jdbcTemplate.queryForString(conn, countSql, selectSql.getParams());
		
		//定义总行数
		int rowcount = Integer.parseInt(rowCount);
		pagination.setRowCount(rowcount);
		
		//定义总页数
		int pageCount = 0;
		
		//计算总页数
		int rowsOfPage = pagination.getRowsOfPage();
		if (rowcount % rowsOfPage == 0) {
			pageCount = rowcount / rowsOfPage;
		} else {
			pageCount = (rowcount / rowsOfPage) + 1;
		}
		
		//定义总页数
		pagination.setPageCount( pageCount );
		
		//2.添加分页
		this.sqlDialect.pagination(selectSql, pagination);
		
	}
	
	/**
	 * 生成新实例
	 * @return
	 */
	protected abstract T newInstance();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public T clone(){
		
		T session = newInstance();
		
		session.setConverterUtil(converterUtil);
		session.setJdbcTemplate(jdbcTemplate);
		session.setSqlDialect(sqlDialect);
		session.setValGeneratorLib(valGeneratorLib);
		
		return session;
	}
}
