/**
 * @author gengw
 * Created on May 8, 2012
 */
package com.bln.framework.persist.session.executor.row.table.update.listener;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.tablestru.ITableStru;

/**
 * �����ִ�����ļ������ӿ�
 */
public interface ITableUpdateExecutorListener {
	
	/**
	 * �����¼֮ǰ���������¼�
	 * @param tableStru ��ṹ
	 * @param row Ҫ����ļ�¼����
	 * @return ��¼����
	 */
	public IRow beforeRowInsert(ITableStru tableStru, IRow row);
	
	/**
	 * �����¼������������¼�
	 * @param tableStru ��ṹ
	 * @param row Ҫ����ļ�¼����
	 * @return ��¼����
	 */
	public IRow rowInserted(ITableStru tableStru, IRow row);
	
	/**
	 * ���¼�¼֮ǰ���������¼�
	 * @param tableStru ��ṹ
	 * @param row Ҫ���µļ�¼����
	 * @return ��¼����
	 */
	public IRow beforeRowUpdate(ITableStru tableStru, IRow row);
	
	/**
	 * ���¼�¼������������¼�
	 * @param tableStru ��ṹ
	 * @param row Ҫ���µļ�¼����
	 * @return ��¼����
	 */
	public IRow rowUpdated(ITableStru tableStru, IRow row);

	/**
	 * ������������֮ǰ���������¼�
	 * @param tableStru ��ṹ
	 * @param row Ҫ���µ�ֵ
	 * @param conClause ��Ҫ���µļ�¼����
	 * @return Ҫ���µ�ֵ
	 */
	public IRow beforeUpdateByCondition(ITableStru tableStru, IRow row, IConditionClause conClause);
	
	/**
	 * ������������������������¼�
	 * @param tableStru ��ṹ
	 * @param row Ҫ���µ�ֵ
	 * @param conClause ��Ҫ���µļ�¼����
	 * @param updatedCount �Ѹ��¼�¼������
	 * @return Ҫ���µ�ֵ
	 */
	public IRow updatedByCondition(ITableStru tableStru, IRow row, IConditionClause conClause, int updatedCount);
	
	/**
	 * ɾ����¼֮ǰ���������¼�
	 * @param tableStru ��ṹ
	 * @param row Ҫɾ���ļ�¼����
	 * @return ��¼����
	 */
	public IRow beforeRowDelete(ITableStru tableStru, IRow row);
	
	/**
	 * ɾ����¼������������¼�
	 * @param tableStru ��ṹ
	 * @param row Ҫ���µļ�¼����
	 * @return ��¼����
	 */
	public IRow rowDeleted(ITableStru tableStru, IRow row);
	
	/**
	 * ��������ɾ����¼֮ǰ���������¼�
	 * @param tableStru ��ṹ
	 * @param conClause ��Ҫɾ���ļ�¼������
	 * @return ɾ����¼������
	 */
	public IConditionClause beforeDeleteByCondition(ITableStru tableStru, IConditionClause conClause);
	
	/**
	 * ��������ɾ����¼������������¼�
	 * @param tableStru ��ṹ
	 * @param conClause ��Ҫɾ���ļ�¼������
	 * @param deletedCount ��ɾ����¼������
	 */
	public void deletedByCondition(ITableStru tableStru, IConditionClause conClause, int deletedCount);

	
}
