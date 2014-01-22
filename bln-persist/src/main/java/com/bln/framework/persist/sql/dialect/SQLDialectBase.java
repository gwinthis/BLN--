package com.bln.framework.persist.sql.dialect;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.util.asserter.Assert;

/**
 * SQL语句方言基类
 */
public abstract class SQLDialectBase implements ISQLDialect{
	
	/**
	 * 是否使用引用符
	 */
	protected boolean usedEnclosing = false;
	
	/**
	 * 起始引用符
	 */
	protected String prefixChar = "\"";

	/**
	 * 结束引用符
	 */
	protected String suffixChar = "\"";
	
	/**
	 * 是否使用schemaName
	 */
	protected boolean usedSchemaName = false;
	
	/**
	 * 构造函数
	 */
	public SQLDialectBase() {
		super();
	}

	/**
	 * insert语句
	 * @param tableName
	 * @param colNames
	 * @param colValues
	 * @return
	 */
	public String insertSql(String schemaName, String tableName, String colNames[], String colValues) {
		
		//" INSERT INTO #tableName (#COLUMN_NAME, #COLUMN_NAME, #COLUMN_NAME, .....) VALUES(?, ?, ?, .....)"
		StringBuilder sbSQL = new StringBuilder("INSERT INTO ");
		
		//表名
		this.processTableName(sbSQL, schemaName, tableName);
		
		//列名
		sbSQL.append("(");
		for ( int i = 0, n = colNames.length; i < n; i++){
			if(i != 0){
				sbSQL.append(", ");
			}
			
			this.processEnclosing(sbSQL, colNames[i]);
		}
	
		//列值
		sbSQL.append(") VALUES(");
		sbSQL.append(colValues);
		sbSQL.append(")");
		
		return sbSQL.toString();
	}
		
	/**
	 * update语句
	 * @param tableName
	 * @param colNames
	 * @param whereCal
	 * @return
	 */
	public String updateSql(String schemaName, String tableName, String colNames[], String verColName, String verClause, String whereCal) {

		Assert.paramIsNotNull(tableName, "tableName");
		Assert.paramIsNotNull(colNames, "colNames");

		//UPDATE TABLENAME SET #COLUMN_NAME = ?, #COLUMN_NAME = ?, #COLUMN_NAME = ?.... WHERE #whereCal
		StringBuilder sbSQL = new StringBuilder("UPDATE ");
		
		//表名
		this.processTableName(sbSQL, schemaName, tableName);
		
		//列名
		sbSQL.append(" SET ");
		String colName = null;
		for ( int i = 0, n = colNames.length; i < n; i++){
			if(i != 0){
				sbSQL.append(", ");
			}
			
			colName = colNames[i];
			this.processEnclosing(sbSQL, colName);
			String valClause = "?";
			if(colName.equals(verColName)){
				valClause = verClause;
			}
			
			sbSQL.append(" = ").append(valClause);
		}
		
		//条件语句
		if(whereCal != null && !"".equals(whereCal)){
			sbSQL.append(" WHERE ");
			sbSQL.append(whereCal);
		}
		return sbSQL.toString();
	}

	/**
	 * delete语句
	 * @param tableName
	 * @param whereCal
	 * @return
	 */
	public String deleteSql(String schemaName, String tableName, String whereCal) {

		Assert.paramIsNotNull(tableName, "tableName");
		
		//DELETE FROM #tableName WHERE #whereCal
		StringBuilder sbSQL = new StringBuilder("DELETE FROM ");

		//表名
		this.processTableName(sbSQL, schemaName, tableName);
				
		//条件语句
		if(whereCal != null && !"".equals(whereCal)){
			sbSQL.append(" WHERE ");
			sbSQL.append(whereCal);
		}
		
		return sbSQL.toString();
	
	}

	/**
	 * select语句
	 * @param tableName
	 * @param colNames
	 * @param whereCal
	 * @return
	 */
	public String selectSql(String schemaName, String tableName, String colNames[], String whereCal, String groupby, String orderby) {
		
		Assert.paramIsNotNull(tableName, "tableName");
		Assert.paramIsNotNull(colNames, "colNames");
		
		//SELECT #COLUNM_NAME FROM #tableName WHERE #whereCal
		StringBuilder sbSQL = new StringBuilder("SELECT ");
		
		//列名
		for ( int i = 0, n = colNames.length; i < n; i++){
			if(i != 0){
				sbSQL.append(", ");
			}
			
			this.processEnclosing(sbSQL, colNames[i]);
		}
				
		//表名
		sbSQL.append(" FROM ");
		this.processTableName(sbSQL, schemaName, tableName);
		
		//拼装SQL
		String sql = this.selectSql(sbSQL.toString(), whereCal, groupby, orderby);
		
		//返回SQL
		return sql;
		
	}
	
	/**
	 * 在SELECT语句的基础上，添加其他子句
	 * @param select 子句
	 * @param whereCal 条件子句
	 * @param groupby 分组子句
	 * @param orderby 排序子句
	 * @return 拼装后的SELECT语句
	 */
	protected String selectSql(String select, String whereCal, String groupby, String orderby) {
		
		StringBuilder sbSQL = new StringBuilder(select);
				
		//条件语句
		if(whereCal != null && !"".equals(whereCal)){
			sbSQL.append(" WHERE ");
			sbSQL.append(whereCal);
		}
		
		//分组语句
		if(!StringUtils.isEmpty(groupby)){
			sbSQL.append(" GROUP BY ");
			sbSQL.append(groupby);
		}	
		
		//排序语句
		if(!StringUtils.isEmpty(orderby)){
			sbSQL.append(" ORDER BY ");
			sbSQL.append(orderby);
		}
		
		return sbSQL.toString();
	}
	
	public String countViewSql(String schemaName, String view){
		return this.countSql(schemaName, view, null, null, false);
	}
	
	/**
	 * 记录数合计SQL，默认处理表的schema name
	 * @param tableName
	 * @param condition
	 * @return
	 */
	public String countSql(String schemaName, String tableName, String condition, String idName){
		return this.countSql(schemaName, tableName, condition, idName, true);
	}
	
	/**
	 * 记录数合计SQL
	 * @param tableName
	 * @param condition
	 * @return
	 */
	protected String countSql(String schemaName, String tableName, String condition, String idName, boolean isProcessTableSchema){
		
		//SELECT COUNT(1) FROM WHERE condition )
		StringBuilder sbSQL = new StringBuilder("SELECT ");
		
		//计数
		if(StringUtils.isEmpty(idName)){
			sbSQL.append("COUNT(1) ");
		}else{
			sbSQL.append("COUNT(");
			this.processEnclosing(sbSQL, idName);
			sbSQL.append(") ");
		}
		
		//表名
		sbSQL.append("FROM ");
		if(isProcessTableSchema){
			this.processTableName(sbSQL, schemaName, tableName);
		}else{
			sbSQL.append(tableName);
		}
		
		//条件语句
		if(condition != null && !"".equals(condition)){
			sbSQL.append("WHERE ");
			sbSQL.append(condition);
		}
		return sbSQL.toString();
	}
	/**
	 * 处理表名
	 * @param sbSQL sbSQL 正在处理的SQL语句
	 * @param schemaName
	 * @param tableName
	 * @return
	 */
	protected StringBuilder processTableName(StringBuilder sbSQL, String schemaName, String tableName){
		if(this.usedSchemaName){			
			this.processEnclosing(sbSQL, schemaName).append(".");
		}
		this.processEnclosing(sbSQL, tableName);
		
		return sbSQL;
	}
	
	/**
	 * 处理enclosing
	 * @param sbSQL 正在处理的SQL语句
	 * @param key 关键字
	 * @return
	 */
	protected StringBuilder processEnclosing(StringBuilder sbSQL, String key){
		if(!usedEnclosing){
			sbSQL.append(key);
		}else{
			sbSQL.append(prefixChar).append(key).append(suffixChar);
		}
		
		return sbSQL;
	}

	/**
	 * @return the usedEnclosing
	 */
	public boolean isUsedEnclosing() {
		return usedEnclosing;
	}

	/**
	 * @param usedEnclosing the usedEnclosing to set
	 */
	public void setUsedEnclosing(boolean usedEnclosing) {
		this.usedEnclosing = usedEnclosing;
	}

	/**
	 * @return the usedSchemaName
	 */
	public boolean isUsedSchemaName() {
		return usedSchemaName;
	}

	/**
	 * @param usedSchemaName the usedSchemaName to set
	 */
	public void setUsedSchemaName(boolean usedSchemaName) {
		this.usedSchemaName = usedSchemaName;
	}

	/**
	 * @return the prefixChar
	 */
	public String getPrefixChar() {
		return prefixChar;
	}

	/**
	 * @param prefixChar the prefixChar to set
	 */
	public void setPrefixChar(String prefixChar) {
		this.prefixChar = prefixChar;
	}

	/**
	 * @return the suffixChar
	 */
	public String getSuffixChar() {
		return suffixChar;
	}

	/**
	 * @param suffixChar the suffixChar to set
	 */
	public void setSuffixChar(String suffixChar) {
		this.suffixChar = suffixChar;
	}

}