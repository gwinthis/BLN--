/**
 * @author Gengw
 * Creatd at 2008-05-09
 */
package com.bln.framework.persist.sql.dialect.oracle;

import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.sql.dialect.ISQLDialect;
import com.bln.framework.persist.sql.dialect.SQLDialectBase;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.util.asserter.Assert;

/**
 * ORACLE的SQL方言
 */
public class OralceDialect extends SQLDialectBase implements ISQLDialect {
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.dialect.ISQLDialect#isExistsSql(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String isExistsSql(String schemaName, String tableName, String condition){
		
		//SELECT 1 FROM DUAL WHERE EXSITS ( SELECT 1 FROM #tableName WHERE #whereCal )
		StringBuilder sbSQL = new StringBuilder("SELECT 1 FROM DUAL WHERE EXISTS ( SELECT 1 FROM ");
			
		//表名
		this.processTableName(sbSQL, schemaName, tableName);
				
		//条件语句
		if(condition != null && !"".equals(condition)){
			sbSQL.append(" WHERE ");
			sbSQL.append(condition);
		}
		
		sbSQL.append(")");
		
		return sbSQL.toString();
	}
		
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sqltemplate.sqldialect.ISqlDialect#newSeqNoSql(java.lang.String)
	 */
	public String newSeqNoSql(String seqNm){
		//SELECT SEQ_#tableName.NEXTVAL FROM DUAL
		if(seqNm == null || "".equals(seqNm)){
			return null;
		}
		
		StringBuilder sbSql = new StringBuilder("SELECT ");
		sbSql.append(seqNm);
		
		sbSql.append(".NEXTVAL");
		sbSql.append(" FROM DUAL");
		
		return sbSql.toString();
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
		StringBuilder sbSql = new StringBuilder(selectSql);
		
		//FOR UPDATE
		sbSql.append(" FOR UPDATE");
		
		return sbSql.toString();
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sqltemplate.sqldialect.ISQLDialect#getDbDate()
	 */
	public String getDbDateSql(){
		return "SELECT TO_CHAR(sysdate, 'yyyy-MM-dd hh24:mi:ss') FROM DUAL";
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
		
		StringBuilder pageSql = new StringBuilder("SELECT * FROM ( SELECT ROW_.*, ROWNUM ROWNUM_ FROM (");
		pageSql.append(sql);
		pageSql.append(") ROW_ WHERE ROWNUM <= ? ) WHERE ROWNUM_ > ?");
		
		//设置
		selectSqlEntity.setSqlInfo(pageSql.toString());
		
		//2.添加参数
		selectSqlEntity.addParams(new Integer(pagination.getEndRowNO()), new Integer(pagination.getStartRowNO()));
	}

}
