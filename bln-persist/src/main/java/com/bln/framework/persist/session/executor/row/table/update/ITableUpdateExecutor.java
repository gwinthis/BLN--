package com.bln.framework.persist.session.executor.row.table.update;

import java.sql.SQLException;


import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.row.table.ITableExecutor;

public interface ITableUpdateExecutor extends ITableExecutor{

	/**
	 * �����¼
	 * @param row �ж���
	 * @return ����֮����ж���
	 * @throws Throwable
	 */
	public abstract IRow insert(IRow row) throws SQLException;

	/**
	 * @param row
	 * @return
	 * @throws Throwable
	 */
	public abstract IRow update(IRow row) throws PersistStaleEntityException, SQLException;


	/**
	 * ɾ����¼
	 * @param row ��¼����
	 * @return ��¼����
	 * @throws Throwable
	 */
	public IRow delete(IRow row) throws PersistStaleEntityException, SQLException;

	/**
	 * ����������������ɾ�����м�¼
	 * @param conClause Ҫɾ����¼������
	 * @return ɾ��������
	 * @throws SQLException 
	 */
	public int deleteByCondition(IConditionClause conClause) throws SQLException;

	/**
	 * ������������������¶��м�¼
	 * @param row Ҫ���µ�����
	 * @param conClause Ҫ���¼�¼������
	 * @return ���µ�����
	 * @throws SQLException 
	 */
	public int updateByCondition(IRow row, IConditionClause conClause)
			throws SQLException;

	/**
	 * clone��ITableSession����
	 * @return
	 */
	public ITableUpdateExecutor clone();
}