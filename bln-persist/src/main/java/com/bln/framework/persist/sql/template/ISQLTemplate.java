package com.bln.framework.persist.sql.template;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.sql.entity.delete.IDeleteSqlEntity;
import com.bln.framework.persist.sql.entity.insert.IInsertSqlEntity;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.persist.sql.entity.update.IUpdateSqlEntity;

public interface ISQLTemplate {
	
	/**
	 * ��ȡ��ѯ����ֱ��������ؼ�¼��SQL
	 * @param selectSqlEntity ��Ҫ������SELECT���
	 */
	public void queryForUpdateSql(ISelectSqlEntity selectSqlEntity);

	/**
	 * 
	 * @param colNamesInTime
	 * @param condition
	 * @return
	 */
	/**
	 * ���ݼ�ʱ������������
	 * @param row ��ǰҪ���µ��ֶ�
	 * @param condition ����
	 * @return
	 */
	public IUpdateSqlEntity updateSqlInTime(IRow row, IConditionClause condition);

	/**
	 * �����������ѯ
	 * @param cols Ҫ��ѯ���ֶ�
	 * @param condition ����
	 * @param groupby �������
	 * @param orderby �������
	 * @param othTables ������ı���
	 * @return sql���
	 */
	public String partColSelectSql(String[] cols, String condition, String groupby,
			String orderby, String othTables);

	/**
	 * ���Insert Sql���
	 * @param row �ж���
	 * @return InsertSqlʵ��
	 */
	public IInsertSqlEntity insertSql(IRow row);


	/**
	 * ����ISelectSqlEntity
	 * @param conClause ������Ϣ
	 * @param pagination ��ҳ��Ϣ
	 * @param orderby ������Ϣ
	 * @return �������������ļ�¼
	 */
	public ISelectSqlEntity selectSql(IConditionClause conClause, IPagination pagination, String orderby);

	/**
	 * @return the insertSql
	 */
	public IInsertSqlEntity getInsertSql();

	/**
	 * @return the updateSql
	 */
	public IUpdateSqlEntity getUpdateSql();

	/**
	 * ����ɾ��SQL���ʵ��
	 * @param condition ����
	 * @return ɾ��SQL���ʵ��
	 */
	public IDeleteSqlEntity deleteSql(IConditionClause condition);

	/**
	 * ����������SQL
	 * @param conClause �������
	 * @return ����������SQL
	 */
	public ISelectSqlEntity countSql(IConditionClause conClause);

	/**
	 * �Ƿ���ڷ��������ļ�¼SQL
	 * @param conClause �������
	 * @return �Ƿ���ڷ��������ļ�¼SQL
	 */
	public ISelectSqlEntity isExistsSql(IConditionClause conClause);
	
}