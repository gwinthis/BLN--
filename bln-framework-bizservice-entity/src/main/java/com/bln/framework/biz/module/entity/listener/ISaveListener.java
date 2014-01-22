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
 * ���������
 */
public interface ISaveListener {

	/**
	 * �½�֮ǰִ��
	 * @param row
	 * @return
	 */
	public IRow beforeInsert(IRow row);

	/**
	 * �½�֮��ִ��
	 * @param row
	 * @return
	 */
	public IRow afterInsert(IRow row);

	/**
	 * ɾ��֮ǰִ��
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
	 * ɾ��֮��ִ��
	 * @param row
	 * @return
	 */
	public IRow afterDelete(IRow row);

	/**
	 * ����֮ǰִ��
	 * @param row
	 * @return
	 */
	public IRow beforeUpdate(IRow row);

	/**
	 * ����֮��ִ��
	 * @param row
	 * @return
	 */
	public IRow afterUpdate(IRow row);

}
