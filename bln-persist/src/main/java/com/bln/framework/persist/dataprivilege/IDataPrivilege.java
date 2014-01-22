package com.bln.framework.persist.dataprivilege;

import com.bln.framework.persist.dataprivilege.exception.DataPrivilegeException;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;

public interface IDataPrivilege {
	
	/**
	 * ��ȡ��ѯ����Ȩ��
	 * @return String Where����
	 */
	public abstract IConditionClause getDataPrivilege()throws DataPrivilegeException;

	/**
	 * ��ȡ��ѯ����Ȩ�ޣ�ʹ�ñ����
	 * @param tableAlias
	 * @return ConditionClause
	 * @throws GeneralException
	 */
	public IConditionClause getDataPrivilege(String tableAlias) throws DataPrivilegeException;

	/**
	 * ��ñ���
	 * @return String
	 */
	public String getTableName();
	
	/**
	 * ���ñ���
	 * @param tableName
	 * @return
	 */
	public void setTableName(String tableName);
}