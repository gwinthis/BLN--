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
 * �Ựִ��������
 * @param <T> ��¡��������
 * @param <E> ʵ������
 */
public abstract class SessionExecutorBase<T extends SessionExecutorBase<T, E>, E> implements ISessionExecutor<E> {
	
	/**
	 * ���ݿ�����
	 */
	protected Connection conn = null;
	
	/**
	 * JDBCģ����
	 */
	protected IJdbcTemplate<E> jdbcTemplate = null;

	/**
	 * ֵת����
	 */
	protected IConverterUtil converterUtil = null;
	
	/**
	 * ��������
	 */
	protected IColValGeneratorLib valGeneratorLib = null;

	/**
	 * SQL����
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
	 * �����ҳ
	 * @param selectSql Ҫִ�е�SQL
	 * @param pagination ��ҳ��Ϣ
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected void pagination(ISelectSqlEntity selectSql, IPagination pagination) throws SQLException,IOException {
				
		//1.��������������ҳ��
		String countSql = sqlDialect.countViewSql(null, new StringBuilder("(").append(selectSql.getSqlInfo()).append(") TEMP").toString());
		
		//ִ�м���������SQL
		String rowCount = jdbcTemplate.queryForString(conn, countSql, selectSql.getParams());
		
		//����������
		int rowcount = Integer.parseInt(rowCount);
		pagination.setRowCount(rowcount);
		
		//������ҳ��
		int pageCount = 0;
		
		//������ҳ��
		int rowsOfPage = pagination.getRowsOfPage();
		if (rowcount % rowsOfPage == 0) {
			pageCount = rowcount / rowsOfPage;
		} else {
			pageCount = (rowcount / rowsOfPage) + 1;
		}
		
		//������ҳ��
		pagination.setPageCount( pageCount );
		
		//2.��ӷ�ҳ
		this.sqlDialect.pagination(selectSql, pagination);
		
	}
	
	/**
	 * ������ʵ��
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
