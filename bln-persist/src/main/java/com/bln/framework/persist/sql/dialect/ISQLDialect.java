package com.bln.framework.persist.sql.dialect;

import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;

public interface ISQLDialect {

	/**
	 * �Ƿ����SQL
	 * @param tableName ����
	 * @param condition ����
	 * @return SQL���
	 */
	public abstract String isExistsSql(String schemaName, String tableName, String condition);

	/**
	 * ��ȡ�����кŵ�SQL
	 * @param seqNm ������
	 * @return SQL���
	 */
	public abstract String newSeqNoSql(String seqNm);

	/**
	 * ��ѯ֮������������SQL
	 * @param selectSql
	 * @return
	 */
	public abstract String queryForUpdateSql(String selectSql);

	/**
	 * ��¼���ϼ�SQL
	 * @param tableName
	 * @param condition
	 * @param idName 
	 * @return
	 */
	public String countSql(String schemaName, String tableName, String condition, String idName);

	/**
	 * ��ͼ�ļ�¼���ϼ�
	 * @param schemaName
	 * @param view
	 * @return
	 */
	public String countViewSql(String schemaName, String view);
	
	/**
	 * ������ݿ�ʱ��
	 * @return
	 */
	public String getDbDateSql();

	/**
	 * insert���
	 * @param tableName
	 * @param colNames
	 * @param colValues
	 * @return
	 */
	public String insertSql(String schemaName, String tableName, String[] colNames, String colValues);

	/**
	 * update���
	 * @param tableName
	 * @param colNames
	 * @param whereCal
	 * @return
	 */
	public String updateSql(String schemaName, String tableName, String[] colNames, String verColName, String verClause, String whereCal);

	/**
	 * delete���
	 * @param tableName
	 * @param whereCal
	 * @return
	 */
	public String deleteSql(String schemaName, String tableName, String whereCal);

	/**
	 * select���
	 * @param tableName
	 * @param colNames
	 * @param whereCal
	 * @return
	 */
	public String selectSql(String schemaName, String tableName, String [] colNames, String whereCal, String groupby, String orderby);

	/**
	 * ��ҳ����
	 * @param selectSqlEntity Ҫ�����selectSQL
	 * @param pagination ��ҳ��Ϣ
	 */
	public void pagination(ISelectSqlEntity selectSqlEntity, IPagination pagination);

	
	
}