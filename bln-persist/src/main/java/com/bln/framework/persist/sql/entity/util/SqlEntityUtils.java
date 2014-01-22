/**
 * @author gengw
 * Created on Jun 15, 2012
 */
package com.bln.framework.persist.sql.entity.util;

import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.lib.ITableStruLib;

/**
 * SQLʵ�幤��
 */
public class SqlEntityUtils implements ISqlEntityUtils {
	
	/**
	 * ��ṹ��
	 */
	protected ITableStruLib tableStruLib = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.util.ISqlEntityUtils#selectSql(java.lang.String, java.lang.String)
	 */
	public String selectSql(String displayTable, String fromAfter){
		
		//����SQL������
		StringBuilder sqlInfo = new StringBuilder("SELECT ");
		
		//�ֶ�
		ITableStru tableStru = tableStruLib.getNotNullInstance(displayTable);
		String columnNames = tableStru.getAllColumns().getColNamesWithTableNm();
		
		sqlInfo.append(columnNames);
		
		//from�����Ϣ
		sqlInfo.append(" FROM ").append(fromAfter);
		
		//����SQL������
		return sqlInfo.toString();
	}

	/**
	 * @return the tableStruLib
	 */
	public ITableStruLib getTableStruLib() {
		return tableStruLib;
	}

	/**
	 * @param tableStruLib the tableStruLib to set
	 */
	public void setTableStruLib(ITableStruLib tableStruLib) {
		this.tableStruLib = tableStruLib;
	}

}
