package com.bln.framework.persist.valgenerator;

import java.sql.Connection;
import java.sql.SQLException;

import com.bln.framework.persist.tablestru.param.IParam;

public interface IColumnValueGenerator {

	/**
	 * ���������͵�������
	 */
	public static final String GENERATOR_TYPE_SEQ = "sequence";

	/**
	 * ��ֵ���Ͱ汾�ŵ�������
	 */
	public static final String GENERATOR_TYPE_NUMERIC_VERSION = "numeric_version";

	
	/**
	 * ʱ������Ͱ汾�ŵ�������
	 */
	public static final String GENERATOR_TYPE_TIMESTAMP_VERSION = "timestamp_version";

	/**
	 * ��SQL�����ͨ����������ֵ
	 * @param params ����
	 * @param currVal TODO
	 * @return �Ӿ�
	 */
	public abstract String getValClause(IParam[] params, Object currVal);

	/**
	 * ����Զ����ɵ�ֵ
	 * @param conn ���ݿ�����
	 * @param params ��������
	 * @param currVal TODO
	 * @return ���ɵ�ֵ
	 * @throws SQLException
	 */
	public Object getVal(Connection conn, IParam[] params, Object currVal) throws SQLException;

	/**
	 * @return the surportColValWithDbFun
	 */
	public boolean isSurportColValWithDbFun();


}