/**
 * @author gengw
 * Created on May 28, 2012
 */
package com.bln.framework.persist.util.factory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.ISession;
import com.bln.framework.persist.session.executor.row.view.IViewExecutor;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.persist.sql.entity.select.SelectSqlEntity;

/**
 * 持久层的帮助对象
 */
public class PersistUtils {
	
	/**
	 * 简单查询
	 * @param session Session对象
	 * @param sql 要执行的SQL
	 * @return 记录集和
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<IRow> querySimple(ISession session, String sql) throws SQLException, IOException{
		return querySimple(session, sql, null);
	}
	
	/**
	 * 简单查询
	 * @param session Session对象
	 * @param sql 要执行的SQL
	 * @param params SQL中的动态参数
	 * @return 记录集和
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<IRow> querySimple(ISession session, String sql, List<Object> params) throws SQLException, IOException{
		
		ISelectSqlEntity selectSqlEntity = new SelectSqlEntity();
		selectSqlEntity.setSqlInfo(sql);
		selectSqlEntity.setParams(params);
		
		IViewExecutor viewExecutor = session.getViewExecutor();
		List<IRow> rows = viewExecutor.query(selectSqlEntity, null);
		
		return rows;
		
	}

}
