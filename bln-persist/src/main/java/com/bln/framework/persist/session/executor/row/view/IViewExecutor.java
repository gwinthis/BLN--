package com.bln.framework.persist.session.executor.row.view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.ISessionExecutor;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;

public interface IViewExecutor extends ISessionExecutor<IRow>{

	/**
	 * ����SQL����ѯ����
	 * @param selectSqlEntity ��ѯSQLʵ��
	 * @return ��ѯ���Ľ��
	 * @throws SQLException
	 * @throws IOException
	 */
	public abstract List<IRow> query(ISelectSqlEntity selectSqlEntity,
			IPagination pagination) throws SQLException, IOException;

	/**
	 * clone��ISessionExecutor����
	 * @return
	 */
	public IViewExecutor clone();

	/**
	 * ��ѯ������еĵ�һ�м�¼
	 * @param selectSqlEntity select sqlʵ��
	 * @param pagination ��ҳ��Ϣ
	 * @return ��һ����¼
	 * @throws SQLException
	 * @throws IOException
	 */
	public IRow queryFirstRow(ISelectSqlEntity selectSqlEntity, IPagination pagination) throws SQLException,
			IOException;
}