/**
 * @author gengw
 * Created on May 25, 2012
 */
package com.bln.framework.persistutil.executebuilder.stru.xml.builder.mssql2008;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.util.factory.PersistUtils;
import com.bln.framework.persistutil.executebuilder.stru.xml.builder.TableStruXmlBuilderBase;

/**
 * MSSQL2008的XML表结构文档生成器
 */
public class TableStruXmlBuilderMSSQL2008 extends TableStruXmlBuilderBase{

	/**
	 * 处理默认值
	 * @param column
	 * @param defaultValue
	 * @return
	 */
	protected String doProcessDefaultValue(IRow column, String defaultValue){
		
		//格式：((0)),需去掉两个括号
		defaultValue = defaultValue.substring(2);
		defaultValue = defaultValue.substring(0, defaultValue.length() - 2);
		return defaultValue;
	
	}
	
	/**
	 * 获得表的所有字段
	 * @param session
	 * @param tableEntity
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected List<IRow> columns(String owner, String tableNames) throws SQLException, IOException{
		
		String sql = "SELECT D.NAME AS TABLE_NAME, A.COLORDER AS COLORDER, A.NAME AS COLUMN_NAME,B.NAME AS DATA_TYPE,A.LENGTH AS DATA_LENGTH, COLUMNPROPERTY(A.ID,A.NAME,'PRECISION') AS DATA_PRECISION, ISNULL(COLUMNPROPERTY(A.ID,A.NAME,'SCALE'),0) AS DATA_SCALE,A.ISNULLABLE AS NULLABLE, ISNULL(E.TEXT,'') AS DATA_DEFAULT,COLUMNPROPERTY( A.ID, A.NAME,'ISIDENTITY') AS IS_IDENTY,CASE WHEN EXISTS(SELECT 1 FROM SYSOBJECTS WHERE XTYPE='PK' AND NAME IN (SELECT NAME FROM SYSINDEXES WHERE INDID IN(SELECT INDID FROM SYSINDEXKEYS WHERE ID = A.ID AND COLID=A.COLID))) THEN 'P' ELSE '' END AS CONSTRAINT_TYPE FROM SYSCOLUMNS A LEFT JOIN SYSTYPES B ON A.XTYPE = B.XUSERTYPE INNER JOIN SYSOBJECTS D ON A.ID=D.ID AND D.XTYPE='U' AND D.NAME <> 'DTPROPERTIES' LEFT JOIN SYSCOMMENTS E ON A.CDEFAULT=E.ID WHERE 1 = 1";
		if(!StringUtils.isEmpty(tableNames) && !"*".equals(tableNames)){
			tableNames = tableNames.replace(",", "','");
			tableNames = "'" + tableNames + "'";
			sql += " AND D.NAME in (" + tableNames + ")";
		}
		
		sql += " ORDER BY TABLE_NAME, COLORDER, CONSTRAINT_TYPE";
		
		List<IRow> columns = PersistUtils.querySimple(session, sql);
		
		return columns;
	}
	
	/**
	 * 
	 * 获得表的关联性
	 * @param session
	 * @param tableEntity
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected List<IRow> references(String owner, String tableNames) throws SQLException, IOException{
		
		String sql = "SELECT A.NAME AS CONSTRAINT_NAME, OBJECT_NAME(B.PARENT_OBJECT_ID) AS CHILD_TABLENAME, D.NAME AS CHILD_COLUMN, OBJECT_NAME(B.REFERENCED_OBJECT_ID) AS PARENT_TABLENAME, C.NAME AS PARENT_COLUMN FROM SYS.FOREIGN_KEYS A INNER JOIN SYS.FOREIGN_KEY_COLUMNS B ON A.OBJECT_ID=B.CONSTRAINT_OBJECT_ID INNER JOIN SYS.COLUMNS C ON B.PARENT_OBJECT_ID=C.OBJECT_ID AND B.PARENT_COLUMN_ID=C.COLUMN_ID  INNER JOIN SYS.COLUMNS D ON B.REFERENCED_OBJECT_ID=D.OBJECT_ID AND B.REFERENCED_COLUMN_ID=D.COLUMN_ID WHERE 1 = 1";
		if(!StringUtils.isEmpty(tableNames) && !"*".equals(tableNames)){
			
			StringBuilder tableCon = new StringBuilder("(");
			String[] tableNameArray = tableNames.split(",");
			for ( int i = 0, n = tableNameArray.length; i < n; i++){
				if( i != 0){
					tableCon.append(" OR ");
				}
				tableCon.append("OBJECT_NAME(B.REFERENCED_OBJECT_ID)='").append(tableNameArray[i]).append("'");
			}
			tableCon.append(")");
			
			sql += " AND " + tableCon.toString();
		}
		
		sql += " ORDER BY PARENT_TABLENAME, CHILD_TABLENAME, CONSTRAINT_NAME";
		List<IRow> references = PersistUtils.querySimple(session, sql);
		
		return references;
	}
}
