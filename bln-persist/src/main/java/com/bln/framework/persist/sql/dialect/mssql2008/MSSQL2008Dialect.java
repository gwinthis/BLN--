/**
 * @author Gengw
 * Creatd at 2008-05-09
 */
package com.bln.framework.persist.sql.dialect.mssql2008;

import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.sql.dialect.ISQLDialect;
import com.bln.framework.persist.sql.dialect.SQLDialectBase;
import com.bln.framework.persist.sql.dialect.exception.NotSupportedSQLException;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.util.asserter.Assert;

/**
 * SQLServer的SQL方言
 */
public class MSSQL2008Dialect extends SQLDialectBase implements ISQLDialect {
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sqltemplate.sqldialect.ISqlDialect#isExistsSql(java.lang.String, java.lang.String)
	 */
	public String isExistsSql(String schemaName, String tableName, String condition){
		
		//SELECT 1 FROM DUAL WHERE EXSITS ( SELECT 1 FROM #tableName WHERE #whereCal )
		StringBuilder sbSQL = new StringBuilder("SELECT 1 FROM ");
			
		//表名
		sbSQL.append(tableName);
		
		//条件语句
		if(condition != null && !"".equals(condition)){
			sbSQL.append(" WHERE ");
			sbSQL.append(condition);
		}
		
		//sbSQL.append(")");
		
		return sbSQL.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sqltemplate.sqldialect.ISqlDialect#newSeqNoSql(java.lang.String)
	 */
	public String newSeqNoSql(String seqNm){
		
		throw new NotSupportedSQLException("newSeqNoSql for mssql2008 is not supported!");
		
//		//SELECT SEQ_#tableName.NEXTVAL FROM DUAL
//		if(seqNm == null || "".equals(seqNm)){
//			return null;
//		}
//		
//		StringBuilder sbSql = new StringBuilder(100).append("SELECT NEXT VALUE FOR ");
//		sbSql.append(seqNm);
//		
//		sbSql.append(" FROM SYSIBM.DUAL");
//		
//		return sbSql.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sqltemplate.sqldialect.ISqlDialect#queryForUpdateSql(java.lang.String)
	 */
	public String queryForUpdateSql(String selectSql){
		
		//#selectSql FOR UPDATE
		if(selectSql == null || "".equals(selectSql)){
			return null;
		}
		
		//SELECT
		StringBuilder sbSql = new StringBuilder(selectSql.length() + 50).append(selectSql);
		
		//FOR UPDATE
		sbSql.append(" FOR UPDATE");
		
		return sbSql.toString();
		
	}
	
	public String countViewSql(String schemaName, String view){
		
		view = view.toUpperCase();
		view = view.replace("SELECT ", "SELECT TOP 999999999999999999 ");
		
		return this.countSql(schemaName, view, null, null, false);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sqltemplate.sqldialect.ISQLDialect#getDbDate()
	 */
	public String getDbDateSql(){
		return "SELECT CONVERT(CHAR(19), GETDATE(), 120)";
	}

	/**
	 * 分页处理
	 * @param selectSqlEntity 要处理的selectSQL
	 * @param pagination 分页信息
	 */
	public void pagination( ISelectSqlEntity selectSqlEntity, IPagination pagination){
		
		//1.校验参数
		Assert.paramIsNotNull(selectSqlEntity, "selectSqlEntity");
		Assert.paramIsNotNull(pagination, "pagination");
		
		//1.处理SQL
		String sql = selectSqlEntity.getSqlInfo();
		sql = sql.toUpperCase().replace("SELECT ", "SELECT TOP 999999999999999999 ");
		
		StringBuilder pageSql = new StringBuilder(sql.length() + 100).append("SELECT * FROM ( SELECT ROW_.*, ");
		
		//, ROWNUMBER() OVER() AS ROWNUM FROM (");
		pageSql.append(this.getRowNumber(sql)).append(" FROM (");
		
		pageSql.append(sql);
		pageSql.append(") ROW_ ) TEMP WHERE ROWNUM_ <= ? AND ROWNUM_ > ?");
		
		//设置
		selectSqlEntity.setSqlInfo(pageSql.toString());
		
		//2.添加参数
		selectSqlEntity.addParams(new Integer(pagination.getEndRowNO()), new Integer(pagination.getStartRowNO()));

	}
	
	/**
	 * Render the <tt>rownumber() over ( .... ) as rownumber_,</tt> 
	 * bit, that goes in the select list
	 */
	private String getRowNumber(String sql) {
		StringBuilder rownumber = new StringBuilder(50).append("ROW_NUMBER() OVER(");

		int orderByIndex = sql.toLowerCase().indexOf("order by");

		if ( orderByIndex > 0 && !hasDistinct(sql) ) {
			rownumber.append( sql.substring(orderByIndex) );
		}

		rownumber.append(") AS ROWNUM_");

		return rownumber.toString();
	}

	private static boolean hasDistinct(String sql) {
		return sql.toLowerCase().indexOf("select distinct")>=0;
	}
}
