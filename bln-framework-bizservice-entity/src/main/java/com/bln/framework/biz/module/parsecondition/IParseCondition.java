package com.bln.framework.biz.module.parsecondition;

import java.text.ParseException;
import java.util.Map;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.ConditionClause;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.tablestru.ITableStru;

public interface IParseCondition {

	/**
	 * ���ݱ���������������
	 * @param condition ����
	 * @param tableStru ��Ҫ��ѯ�ı�Ľṹ
	 * @param conField2ColMap �����ֶζ�Ӧ���ֶε�ӳ��
	 * @param tableAlias �����
	 * @return ��������
	 * @throws ParseException
	 */
	public abstract IConditionClause parseQueryCondition(IRow condition, ITableStru tableStru,
			Map<String, String> conField2ColMap, String tableAlias)
			throws ParseException;

	/**
	 * ������������
	 * @param condition ��ѯ����
	 * @param tableStru ��Ҫ��ѯ�ı�Ľṹ
	 * @return ����������������������
	 * @throws Throwable
	 */
	public IConditionClause parseAllIdsCondition(IRow condition, ITableStru tableStru) throws ParseException;

	/**
	 * �����ӱ�����
	 * @param condition ��ѯ����
	 * @param tableStru ����Ľṹ
	 * @param childTableName �ӱ����
	 * @return �ӱ�����
	 * @throws Throwable
	 */
	public ConditionClause parseChildReferCondition(IRow condition, ITableStru tableStru,
			String childTableName)throws ParseException;

}