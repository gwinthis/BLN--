package com.bln.framework.persist.jdbc.template;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.bln.framework.persist.jdbc.template.storeprocedure.IStoreProcedureParameters;
import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureResult;
import com.bln.framework.persist.tablestru.component.column.IColumn;
public interface IJdbcTemplate <T>{

	/**
	 * ��ѯ����,��ָ�����������ؽ����
	 * @param conn ��ǰ���ݿ�����
	 * @param sql SQL���
	 * @param params ��������
	 * @param columns ��Ҫ��ѯ���ֶ�
	 * @return
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public List<T> query(Connection conn, String sql, List<Object> params, IColumn[] columns) throws SQLException, IOException;

	/**
	 * ��ѯ����������һ��ֵ��������ֻ����һ��һ���ֶε�SQL��ע��SQL���Ķ�̬SQL�е��ʺű���ͬparamsһһ��Ӧ��
	 * @param conn ��ǰ���ݿ�����
	 * @param sql ��̬SELECT���
	 * @param params ��������
	 * @return Object ����ָ��������
	 * @throws SQLException 
	 */
	public abstract String queryForString(Connection conn, String sql, List<Object> params)
			throws SQLException;

	/**
	 * ִ�и��¡������ɾ������
	 * @param conn ��ǰ���ݿ�����
	 * @param sql
	 * @param params ��������
	 * @return ���³ɹ�������
	 * @throws SQLException 
	 * @throws GeneralException 
	 */
	public abstract int update(Connection conn, String sql, List<Object> params) throws SQLException;

	/**
	 * ִ�и��¡������ɾ������
	 * @param sql
	 * @param params ��������
	 * @param generatedCols �Զ����ɵ��ֶ�
	 * @param generatedVals �Զ����ɵ�ֵ
	 * @return ���ظ��³ɹ���������
	 * @throws SQLException 
	 */
	public int update(Connection conn, String sql, List<Object> params, int[] generatedCols, Object[] generatedVals) throws SQLException;
	
	/**
	 * û�в����ĸ������ִ���������ݿ���²���������update��insert��delete����
	 * @param conn ���ݿ�����
	 * @param sqls Ҫִ�е�SQL�������
	 * @return ÿ��SQL���ִ����Ӱ�������
	 * @throws SQLException
	 */
	public abstract int[] updateBatch(Connection conn, String[] sqls) throws SQLException;

	/**
	 * �������ĸ������ִ���������ݿ���²���������update��insert��delete����
	 * @param conn ���ݿ�����
	 * @param sql Ҫִ�е�SQL���
	 * @param paramsList ��ͬ�Ĳ���
	 * @return ÿ��SQL���ִ����Ӱ�������
	 * @throws SQLException
	 */
	public int[] updateBatch(Connection conn, String sql, List<List<Object>> paramsList) throws SQLException;
	
	/**
	 * ִ�д洢����
	 * @param conn ���ݿ�����
	 * @param spCommand �洢��������
	 * @param spParameters �洢���̲�������Ҫ��spCommandһ��
	 * @return ���ش洢���̽��
	 * @throws SQLException
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public StoreProcedureResult<T> executeStoreProcedure(Connection conn, String spCommand, IStoreProcedureParameters spParameters) throws SQLException, IOException;

	public abstract StoreProcedureResult<T> executeStoreProcedureSimple(Connection conn,
			String spCommand, IStoreProcedureParameters spParameters) throws SQLException, IOException;

}