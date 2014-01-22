package com.bln.framework.persist.session.executor.material.condition;

import java.util.List;

public interface IConditionClause {

	/**
	 * �������Ͳ���ֵһ���趨
	 * @param con
	 * @param instance
	 * @throws GeneralException 
	 */
	public abstract IConditionClause addConValue(String con, Object obj);

	/**
	 * �����������
	 * @param str �������
	 */
	public abstract IConditionClause addCondition(String str);

	/**
	 * ���������������
	 * @param str �������
	 */
	public abstract IConditionClause addConditionAtHead(String con, String opr);

	/**
	 * ����ֵ����
	 * @param Object ��Ӧ��������еĲ���
	 */
	public abstract IConditionClause addValue(Object obj);

	/**
	 * ��Ӷ��ֵ����
	 * @param Object ��Ӧ��������еĲ���
	 */
	public abstract IConditionClause addValues(Object[] objs);

	/**
	 * ��Ӷ��ֵ����
	 * @param Object ��Ӧ��������еĲ���
	 */
	public abstract IConditionClause addValues(List<Object> objs);

	/**
	 * ��ԭ�еĻ���������µ���������
	 * @param newCon �µ���������
	 * @param linkOpr ����������
	 * @return ���������������
	 * @throws Throwable
	 */
	public abstract IConditionClause addConClause(IConditionClause newConClause,
			String linkOpr);

	/**
	 * ��ȡ�������
	 * @return
	 */
	public abstract String getCondtion();

	/**
	 * ��ȡ����List
	 * @return
	 */
	public abstract List<Object> getValues();

	/**
	 * ��ȡ��������
	 * @return
	 */
	public abstract Object[] getValuesAsArray();

}