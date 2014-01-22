package com.bln.framework.biz.module.entity.listener;

import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

public interface IEntityListener {

	/**
	 * ��ѯ֮ǰ�������¼�
	 * @param conClause ��ѯ����
	 * @param pagination ��ҳ����
	 * @param orderby ������Ϣ
	 */
	public abstract void beforeQueryEvt(IConditionClause conClause,
			IPagination pagination, String orderby);

	/**
	 * ��ѯ֮�������¼�
	 * @param rows ��ѯ���ļ�¼
	 * @return ����֮��ļ�¼
	 */
	public abstract List<IRow> afterQueryEvt(List<IRow> rows);

	/**
	 * �����¼֮ǰ�������¼�
	 * @param row ��¼
	 * @return ����֮��ļ�¼
	 */
	public abstract IRow beforeInsertEvt(IRow row);

	/**
	 * �����¼֮�������¼�
	 * @param row ��¼
	 * @return ����֮��ļ�¼
	 */
	public abstract IRow afterInsertEvt(IRow row);

	/**
	 * ���¼�¼֮ǰ�������¼�
	 * @param row ��¼
	 * @return ����֮��ļ�¼
	 */
	public abstract IRow beforeUpdateEvt(IRow row);

	/**
	 * ���¼�¼֮�������¼�
	 * @param row ��¼
	 * @return ����֮��ļ�¼
	 */
	public abstract IRow afterUpdateEvt(IRow row);

	/**
	 * ɾ����¼֮ǰ�������¼�
	 * @param row ��¼
	 * @return ����֮��ļ�¼
	 */
	public abstract IRow beforeDeleteEvt(IRow row);

	/**
	 * ɾ����¼֮�������¼�
	 * @param row ��¼
	 * @return ����֮��ļ�¼
	 */
	public abstract IRow afterDeleteEvt(IRow row);

}