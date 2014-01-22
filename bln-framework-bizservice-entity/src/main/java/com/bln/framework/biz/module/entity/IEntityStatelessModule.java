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
	 * 查询实体
	 * @param condition 条件
	 * @param pagination 分页信息
	 * @param orderby 排序信息
	 * @return 返回结果集
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public List<IRow> query(IRow condition, IPagination pagination, String orderby)
			throws SQLException, IOException, ParseException;

	/**
	 * 根据主键查询记录
	 * @param condition 包含主键的查询条件
	 * @return 符合条件的记录
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public IRow queryById(IRow condition) throws ParseException, SQLException,
			IOException;

	/**
	 * 根据主键获取主表记录，并查询相应的子表记录。
	 * @param condition 查询条件
	 * @param childTableNames 需要查询的子表的数组
	 * @return Map<String, List<IRow>> 表名和对应记录的映射
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Map<String, List<IRow>> queryByIdWithChilds(IRow condition, String[] childTableNames)
			throws SQLException, IOException, ParseException;

	/**
	 * 根据记录，查询该记录的对应子表记录
	 * @param row 记录
	 * @param childTableNames 需要查询的子表
	 * @return Map<String, List<IRow>> 表名和对应记录的映射
	 * @throws SQLException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Map<String, List<IRow>> queryChilds(IRow row, String[] childTableNames) throws SQLException,
			IOException, ParseException;

	/**
	 * 新增记录方法
	 * @param row 要新增的记录
	 * @return 新增后的记录
	 * @throws SQLException
	 * @throws IOException
	 * @throws InvalidRowException 
	 */
	public IRow insert(IRow row) throws SQLException, IOException, InvalidRowException;

	/**
	 * 更新记录
	 * @param row 要更新的记录
	 * @return 更新之后的记录
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws PersistStaleEntityException 
	 * @throws InvalidRowException 
	 * @throws Throwable
	 */
	public IRow update(IRow row) throws PersistStaleEntityException,
			SQLException, IOException, InvalidRowException;

	/**
	 * 删除记录
	 * @param row 要删除的记录
	 * @param deleteChildRowOnDelete 删除主记录时是否删除子表记录
	 * @return 要删除的记录
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
	 * @param rows 主表记录集合
	 * @param childRowsMap 子表记录Map
	 * @param deleteChildRowOnDelete 删除主记录时是否删除子表记录
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
	 * 新建多条记录
	 * @param rows 要新建的记录
	 * @return 已新建的记录
	 * @throws PersistStaleEntityException
	 * @throws InvalidRowException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<IRow> insertRows(List<IRow> rows) throws
			InvalidRowException, SQLException, IOException;

	/**
	 * 更新多条记录
	 * @param rows 要更新的记录
	 * @return 已新建的记录
	 * @throws PersistStaleEntityException
	 * @throws InvalidRowException
	 * @throws SQLException
	 * @throws IOException
	 */
	public List<IRow> updateRows(List<IRow> rows) throws PersistStaleEntityException,
			InvalidRowException, SQLException, IOException;

	/**
	 * 删除多条记录
	 * @param rows 要删除的记录
	 * @param deleteChildRowOnDelete 删除主记录时是否删除子表记录
	 * @return 要删除的记录
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
	 * 获取第一个当前记录下不为空的子表名称
	 * @param parentRow 主表记录
	 * @return 如果为空，表示不存在子表记录；不为空，第一个不为空的子表
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public String firstChildNameExistsRows(IRow parentRow) throws ParseException,
			SQLException, IOException;

	/**
	 * 删除当前记录下的子表记录
	 * @param parentRow 父表记录
	 * @throws ParseException
	 * @throws SQLException
	 * @throws IOException
	 */
	public void deleteChildRows(IRow parentRow) throws ParseException,
			SQLException, IOException;

}