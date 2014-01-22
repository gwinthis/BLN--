/**
 * @author gengw
 * Created on May 25, 2012
 */
package com.bln.framework.persistutil.executebuilder.stru.xml.builder.db2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.row.Row;
import com.bln.framework.persist.util.factory.PersistUtils;
import com.bln.framework.persistutil.executebuilder.stru.xml.builder.TableStruXmlBuilderBase;

/**
 * DB2的XML表结构文档生成器
 */
public class TableStruXmlBuilderDB2 extends TableStruXmlBuilderBase{
	
	/**
	 * 获得表的所有字段
	 * @param session
	 * @param tableEntity
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected List<IRow> columns(String owner, String tableNames) throws SQLException, IOException{
		
		String sql = "SELECT TABNAME AS TABLE_NAME, COLNAME as COLUMN_NAME, (CASE WHEN KEYSEQ IS NOT NULL THEN 'P' ELSE NULL END) AS CONSTRAINT_TYPE, TYPENAME AS DATA_TYPE, \"LENGTH\" AS DATA_LENGTH, \"LENGTH\" AS DATA_PRECISION, SCALE AS DATA_SCALE, NULLS AS NULLABLE, \"DEFAULT\" AS DATA_DEFAULT , \"REMARKS\" AS COMMENTS FROM \"SYSCAT\".\"COLUMNS\" WHERE TABSCHEMA = '" + owner + "'";
		if(!StringUtils.isEmpty(tableNames) && !"*".equals(tableNames)){
			tableNames = tableNames.replace(",", "','");
			tableNames = "'" + tableNames + "'";
			sql += " AND TABNAME in (" + tableNames + ")";
		}
		
		sql += " ORDER BY TABLE_NAME, COLNO, CONSTRAINT_TYPE";
		
		
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
		
		String sql = "SELECT TABNAME AS CHILD_TABLENAME, LTRIM(RTRIM(FK_COLNAMES)) AS CHILD_COLUMN, REFTABNAME AS PARENT_TABLENAME, LTRIM(RTRIM(PK_COLNAMES)) AS PARENT_COLUMN, CONSTNAME AS CONSTRAINT_NAME, COLCOUNT FROM \"SYSCAT\".\"REFERENCES\" WHERE OWNER = '" + owner + "'";
		if(!StringUtils.isEmpty(tableNames) && !"*".equals(tableNames)){
			tableNames = tableNames.replace(",", "','");
			tableNames = "'" + tableNames + "'";
			sql += " AND REFTABNAME in (" + tableNames + ")";
		}
		
		sql += " ORDER BY PARENT_TABLENAME, CHILD_TABLENAME, CONSTRAINT_NAME";
		List<IRow> references = PersistUtils.querySimple(session, sql);
		
		//拆分外键
		List<IRow> result = new ArrayList<IRow>();
		for ( IRow ref : references){
			if("1".equals(ref.getValue("COLCOUNT"))){
				result.add(ref);
			}else{
				splitRef(ref, result);
			}
		}

		return result;
	}
	
	/**
	 * 拆分关联
	 * @param ref
	 * @param result
	 */
	protected void splitRef(IRow ref, List<IRow> result){
				
		String fkCols = ref.getValue("CHILD_COLUMN");
		String[] fks = StringUtils.split(fkCols);

		String pkCols = ref.getValue("PARENT_COLUMN");
		String[] pks = StringUtils.split(pkCols);

		for ( int i = 0, n = fks.length; i < n; i++){
			
			String fk = fks[i];
			String pk = pks[i];
			
			IRow row = new Row();
			row.importRow(ref);
			
			row.setValue("CHILD_COLUMN", fk);
			row.setValue("PARENT_COLUMN", pk);
			
			result.add(row);
		}
	}


}
