/**
 * @author gengw
 * Created on Apr 24, 2012
 */
package com.bln.framework.persist.session.executor.material.query;

import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

/**
 * ��ѯ������Ϣ
 */
public class QueryInfo implements IQueryInfo {
	
	/**
	 * Ҫ��ѯ���ֶ���
	 */
	protected String cols = null;
	
	/**
	 * �������
	 */
	protected String groupby = null;
	
	/**
	 * �������
	 */
	protected String orderby = null;
	
	/**
	 * ��Ҫ��ѯ�ı������
	 */
	protected String[] tables = null;
	
	/**
	 * �������
	 */
	protected IConditionClause conClause = null;
	
	/**
	 * ��ҳ��Ϣ
	 */
	protected IPagination pagination = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#getCols()
	 */
	public String getCols() {
		return cols;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#setCols(java.lang.String)
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#getGroupby()
	 */
	public String getGroupby() {
		return groupby;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#setGroupby(java.lang.String)
	 */
	public void setGroupby(String groupby) {
		this.groupby = groupby;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#getOrderby()
	 */
	public String getOrderby() {
		return orderby;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#setOrderby(java.lang.String)
	 */
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#getTables()
	 */
	public String[] getTables() {
		return tables;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#setTables(java.lang.String[])
	 */
	public void setTables(String[] tables) {
		this.tables = tables;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#getConClause()
	 */
	public IConditionClause getConClause() {
		return conClause;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#setConClause(com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public void setConClause(IConditionClause conClause) {
		this.conClause = conClause;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#getPagination()
	 */
	public IPagination getPagination() {
		return pagination;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.material.query.IQueryInfo#setPagination(com.bln.framework.persist.session.material.pagination.IPagination)
	 */
	public void setPagination(IPagination pagination) {
		this.pagination = pagination;
	}

}
