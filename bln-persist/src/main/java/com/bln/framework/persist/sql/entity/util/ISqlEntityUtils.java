package com.bln.framework.persist.sql.entity.util;

public interface ISqlEntityUtils {

	/**
	 * �����ѯSQL
	 * @param displayTable Ҫ��ѯ���ֶ����ڵı���
	 * @param fromAfter from�����Ϣ
	 * @return SELECT SQL 
	 */
	public abstract String selectSql(String displayTable, String fromAfter);

}