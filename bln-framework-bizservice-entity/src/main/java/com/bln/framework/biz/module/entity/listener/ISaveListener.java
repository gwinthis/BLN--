/**
 * @author gengw
 * Created on May 16, 2012
 */
package com.bln.framework.biz.module.entity.listener;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import com.bln.framework.biz.module.entity.exception.RefenceConstraintException;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.exception.PersistStaleEntityException;

/**
 * 保存监听器
 */
public interface ISaveListener {

	/**
	 * 新建之前执行
	 * @param row
	 * @return
	 */
	public IRow beforeInsert(IRow row);

	/**
	 * 新建之后执行
	 * @param row
	 * @return
	 */
	public IRow afterInsert(IRow row);

	/**
	 * 删除之前执行
	 * @param row
	 * @param deleteChildRowOnDelete TODO
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws RefenceConstraintException 
	 * @throws PersistStaleEntityException 
	 */
	public IRow beforeDelete(IRow row, boolean deleteChildRowOnDelete) throws PersistStaleEntityException, RefenceConstraintException, SQLException, IOException, ParseException;

	/**
	 * 删除之后执行
	 * @param row
	 * @return
	 */
	public IRow afterDelete(IRow row);

	/**
	 * 更新之前执行
	 * @param row
	 * @return
	 */
	public IRow beforeUpdate(IRow row);

	/**
	 * 更新之后执行
	 * @param row
	 * @return
	 */
	public IRow afterUpdate(IRow row);

}
