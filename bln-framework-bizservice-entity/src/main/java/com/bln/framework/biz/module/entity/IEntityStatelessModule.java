package com.bln.framework.biz.module.entity;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.bln.framework.biz.module.entity.exception.InvalidRowException;
import com.bln.framework.biz.module.entity.exception.RefenceConstraintException;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

public interface IEntityStatelessModule {

	/**
	 * ��ѯʵ��
	 * @param condition ����
	 * @param pagination ��ҳ��Ϣ
	 * @param orderby ������Ϣ
	 * @return ���ؽ����
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public List<IRow> query(IRow condition, IPagination pagination, String orderby)
			throws SQLException, IOException, ParseException;

	/**
	 * ����������ѯ��¼
	 * @param condition ���������Ĳ�ѯ����
	 * @return ���������ļ�¼
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public IRow queryById(IRow condition) throws ParseException, SQLException,
			IOException;

	/**
	 * ����������ȡ�����¼������ѯ��Ӧ���ӱ��¼��
	 * @param condition ��ѯ����
	 * @param childTableNames ��Ҫ��ѯ���ӱ������
	 * @return Map<String, List<IRow>> �����Ͷ�Ӧ��¼��ӳ��
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Map<String, List<IRow>> queryByIdWithChilds(IRow condition, String[] childTableNames)
			throws SQLException, IOException, ParseException;

	/**
	 * ���ݼ�¼����ѯ�ü�¼�Ķ�Ӧ�ӱ��¼
	 * @param row ��¼
	 * @param childTableNames ��Ҫ��ѯ���ӱ�
	 * @return Map<String, List<IRow>> �����Ͷ�Ӧ��¼��ӳ��
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Map<String, List<IRow>> queryChilds(IRow row, String[] childTableNames) throws SQLException,
			IOException, ParseException;

	/**
	 * ������¼����
	 * @param row Ҫ�����ļ�¼
	 * @return ������ļ�¼
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidRowException 
	 */
	public IRow insert(IRow row) throws SQLException, IOException, InvalidRowException;

	/**
	 * ���¼�¼
	 * @param row Ҫ���µļ�¼
	 * @return ����֮��ļ�¼
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws PersistStaleEntityException 
	 * @throws InvalidRowException 
	 * @throws Throwable
	 */
	public IRow update(IRow row) throws PersistStaleEntityException,
			SQLException, IOException, InvalidRowException;

	/**
	 * ɾ����¼
	 * @param row Ҫɾ���ļ�¼
	 * @param deleteChildRowOnDelete ɾ������¼ʱ�Ƿ�ɾ���ӱ��¼
	 * @return Ҫɾ���ļ�¼
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws PersistStaleEntityException 
	 * @throws ParseException 
	 * @throws RefenceConstraintException 
	 * @throws Throwable
	 */
	public IRow delete(IRow row, boolean deleteChildRowOnDelete) throws PersistStaleEntityException,
			SQLException, IOException, ParseException,
			RefenceConstraintException;

	/**
	 * @param rows �����¼����
	 * @param childRowsMap �ӱ��¼Map
	 * @param deleteChildRowOnDelete ɾ������¼ʱ�Ƿ�ɾ���ӱ��¼
	 * @return
	 * @throws PersistStaleEntityException
	 * @throws RefenceConstraintException
	 * @throws InvalidRowException
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Map<String, List<IRow>> save(List<IRow> rows, Map<String, List<IRow>> childRowsMap, boolean deleteChildRowOnDelete)
			throws PersistStaleEntityException, RefenceConstraintException,
			InvalidRowException, SQLException, IOException, ParseException;

	/**
	 * �½�������¼
	 * @param rows Ҫ�½��ļ�¼
	 * @return ���½��ļ�¼
	 * @throws PersistStaleEntityException
	 * @throws InvalidRowException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<IRow> insertRows(List<IRow> rows) throws
			InvalidRowException, SQLException, IOException;

	/**
	 * ���¶�����¼
	 * @param rows Ҫ���µļ�¼
	 * @return ���½��ļ�¼
	 * @throws PersistStaleEntityException
	 * @throws InvalidRowException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<IRow> updateRows(List<IRow> rows) throws PersistStaleEntityException,
			InvalidRowException, SQLException, IOException;

	/**
	 * ɾ��������¼
	 * @param rows Ҫɾ���ļ�¼
	 * @param deleteChildRowOnDelete ɾ������¼ʱ�Ƿ�ɾ���ӱ��¼
	 * @return Ҫɾ���ļ�¼
	 * @throws PersistStaleEntityException
	 * @throws RefenceConstraintException
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<IRow> deleteRows(List<IRow> rows, boolean deleteChildRowOnDelete) throws PersistStaleEntityException,
			RefenceConstraintException, SQLException, IOException,
			ParseException;

	/**
	 * ��ȡ��һ����ǰ��¼�²�Ϊ�յ��ӱ�����
	 * @param parentRow �����¼
	 * @return ���Ϊ�գ���ʾ�������ӱ��¼����Ϊ�գ���һ����Ϊ�յ��ӱ�
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String firstChildNameExistsRows(IRow parentRow) throws ParseException,
			SQLException, IOException;

	/**
	 * ɾ����ǰ��¼�µ��ӱ��¼
	 * @param parentRow �����¼
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void deleteChildRows(IRow parentRow) throws ParseException,
			SQLException, IOException;

}