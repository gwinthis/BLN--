package com.bln.framework.persist.session.executor.row.table.query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.session.executor.row.table.ITableExecutor;


public interface ITableQueryExecutor extends ITableExecutor{


	/**
	 * ��ѯ��¼
	 * @param conClause ������Ϣ
	 * @param pagination ��ҳ��Ϣ
	 * @param orderby ������Ϣ
	 * @return ���������ļ�¼
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public List<IRow> query(IConditionClause conClause, IPagination pagination, String orderby)
			throws SQLException, IOException;

	/**
	 * ��ѯ֮��������Ӧ��¼
	 * @param conClause �������
	 * @param orderby �����Ӿ�
	 * @return ��ѯ���ļ�¼
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<IRow> queryForUpdate(IConditionClause conClause, String orderby)
			throws SQLException, IOException;

	/**
	 * �����Ƿ���ڷ��������ļ�¼
	 * @param conClause �������
	 * @return �Ƿ����
	 * @throws SQLException
	 * @throws IOException
	 */
	public boolean isExists(IConditionClause conClause) throws SQLException, IOException;

	/**
	 * ������������ļ�¼��
	 * @param conClause �������
	 * @return ����
	 * @throws SQLException
	 * @throws IOException
	 */
	public int count(IConditionClause conClause) throws SQLException, IOException;
	
	
	/**
	 * clone��ITableSession����
	 * @return
	 */
	public ITableQueryExecutor clone();

	/**
	 * ��ѯ���������ĵ�һ�м�¼
	 * @param conClause ������Ϣ
	 * @param pagination ��ҳ��Ϣ
	 * @param orderby ������Ϣ
	 * @return ���������ĵ�һ�м�¼
	 * @throws SQLException
	 * @throws IOException
	 */
	public IRow queryFirstRow(IConditionClause conClause, IPagination pagination, String orderby)
			throws SQLException, IOException;
}