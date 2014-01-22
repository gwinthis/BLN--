/**
 * @author gengw
 * Created on May 25, 2012
 */
package com.bln.framework.persistutil.executebuilder.stru.xml.builder.oracle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.util.factory.PersistUtils;
import com.bln.framework.persistutil.executebuilder.stru.xml.builder.TableStruXmlBuilderBase;

/**
 * Oracle的XML表结构文档生成器
 */
public class TableStruXmlBuilderOracle extends TableStruXmlBuilderBase{
	
	/**
	 * 获得表的所有字段
	 * @param session
	 * @param tableEntity
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected List<IRow> columns(String owner, String tableNames) throws SQLException, IOException{
		
		String sql = "SELECT A.TABLE_NAME, A.COLUMN_NAME, (SELECT D.CONSTRAINT_TYPE FROM ALL_CONSTRAINTS D, ALL_CONS_COLUMNS E WHERE C.OWNER = D.OWNER AND D.OWNER = E.OWNER AND C.TABLE_NAME = D.TABLE_NAME AND D.TABLE_NAME = E.TABLE_NAME AND B.COLUMN_NAME = E.COLUMN_NAME AND D.CONSTRAINT_NAME = E.CONSTRAINT_NAME AND D.CONSTRAINT_TYPE = 'P' AND ROWNUM = 1 ) AS CONSTRAINT_TYPE, A.DATA_TYPE, A.DATA_LENGTH, A.DATA_PRECISION, A.DATA_SCALE, A.NULLABLE, A.DATA_DEFAULT, B.COMMENTS, C.COMMENTS AS TAB_COMMENTS FROM ALL_TAB_COLUMNS A, ALL_COL_COMMENTS B, ALL_TAB_COMMENTS C WHERE A.OWNER = B.OWNER AND B.OWNER = C.OWNER AND A.TABLE_NAME = B.TABLE_NAME AND B.TABLE_NAME = C.TABLE_NAME AND A.COLUMN_NAME = B.COLUMN_NAME AND A.OWNER = '" + owner + "'";
		
		if(!StringUtils.isEmpty(tableNames) && !"*".equals(tableNames)){
			tableNames = tableNames.replace(",", "','");
			tableNames = "'" + tableNames + "'";
			sql += " AND A.TABLE_NAME in (" + tableNames + ")";
		}
		
		sql += " ORDER BY A.TABLE_NAME, A.COLUMN_ID, CONSTRAINT_TYPE";
		
		
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
		
		String sql = "SELECT CHILD.TABLE_NAME CHILD_TABLENAME, CP.COLUMN_NAME CHILD_COLUMN, PARENT.TABLE_NAME PARENT_TABLENAME, PC.COLUMN_NAME PARENT_COLUMN, CP.POSITION, CHILD.CONSTRAINT_NAME CONSTRAINT_NAME FROM USER_CONSTRAINTS CHILD, USER_CONSTRAINTS PARENT, USER_CONS_COLUMNS CP, USER_CONS_COLUMNS PC WHERE CHILD.CONSTRAINT_TYPE = 'R' AND CHILD.R_CONSTRAINT_NAME = PARENT.CONSTRAINT_NAME AND CHILD.CONSTRAINT_NAME = CP.CONSTRAINT_NAME AND PARENT.CONSTRAINT_NAME = PC.CONSTRAINT_NAME AND CP.POSITION = PC.POSITION AND CHILD.OWNER = '" + owner + "'";
		if(!StringUtils.isEmpty(tableNames) && !"*".equals(tableNames)){
			tableNames = tableNames.replace(",", "','");
			tableNames = "'" + tableNames + "'";
			sql += " AND PARENT.TABLE_NAME in (" + tableNames + ")";
		}
		
		sql += " ORDER BY PARENT.TABLE_NAME, CHILD.TABLE_NAME, CHILD.CONSTRAINT_NAME, CP.POSITION";
		List<IRow> references = PersistUtils.querySimple(session, sql);
		
		return references;
	}


}
