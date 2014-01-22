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
 * �־ò�İ�������
 */
public class PersistUtils {
	
	/**
	 * �򵥲�ѯ
	 * @param session Session����
	 * @param sql Ҫִ�е�SQL
	 * @return ��¼����
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<IRow> querySimple(ISession session, String sql) throws SQLException, IOException{
		return querySimple(session, sql, null);
	}
	
	/**
	 * �򵥲�ѯ
	 * @param session Session����
	 * @param sql Ҫִ�е�SQL
	 * @param params SQL�еĶ�̬����
	 * @return ��¼����
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
