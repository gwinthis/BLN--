/**
 * @author gengw
 * Created on Jun 15, 2012
 */
package com.bln.framework.persist.sql.entity.util;

import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.lib.ITableStruLib;

/**
 * SQL实体工具
 */
public class SqlEntityUtils implements ISqlEntityUtils {
	
	/**
	 * 表结构库
	 */
	protected ITableStruLib tableStruLib = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.entity.util.ISqlEntityUtils#selectSql(java.lang.String, java.lang.String)
	 */
	public String selectSql(String displayTable, String fromAfter){
		
		//构造SQL字面量
		StringBuilder sqlInfo = new StringBuilder("SELECT ");
		
		//字段
		ITableStru tableStru = tableStruLib.getNotNullInstance(displayTable);
		String columnNames = tableStru.getAllColumns().getColNamesWithTableNm();
		
		sqlInfo.append(columnNames);
		
		//from后的信息
		sqlInfo.append(" FROM ").append(fromAfter);
		
		//返回SQL字面量
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
