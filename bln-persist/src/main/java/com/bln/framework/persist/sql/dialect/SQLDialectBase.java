package com.bln.framework.persist.sql.dialect;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.util.asserter.Assert;

/**
 * SQL��䷽�Ի���
 */
public abstract class SQLDialectBase implements ISQLDialect{
	
	/**
	 * �Ƿ�ʹ�����÷�
	 */
	protected boolean usedEnclosing = false;
	
	/**
	 * ��ʼ���÷�
	 */
	protected String prefixChar = "\"";

	/**
	 * �������÷�
	 */
	protected String suffixChar = "\"";
	
	/**
	 * �Ƿ�ʹ��schemaName
	 */
	protected boolean usedSchemaName = false;
	
	/**
	 * ���캯��
	 */
	public SQLDialectBase() {
		super();
	}

	/**
	 * insert���
	 * @param tableName
	 * @param colNames
	 * @param colValues
	 * @return
	 */
	public String insertSql(String schemaName, String tableName, String colNames[], String colValues) {
		
		//" INSERT INTO #tableName (#COLUMN_NAME, #COLUMN_NAME, #COLUMN_NAME, .....) VALUES(?, ?, ?, .....)"
		StringBuilder sbSQL = new StringBuilder("INSERT INTO ");
		
		//����
		this.processTableName(sbSQL, schemaName, tableName);
		
		//����
		sbSQL.append("(");
		for ( int i = 0, n = colNames.length; i < n; i++){
			if(i != 0){
				sbSQL.append(", ");
			}
			
			this.processEnclosing(sbSQL, colNames[i]);
		}
	
		//��ֵ
		sbSQL.append(") VALUES(");
		sbSQL.append(colValues);
		sbSQL.append(")");
		
		return sbSQL.toString();
	}
		
	/**
	 * update���
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
		
		//����
		this.processTableName(sbSQL, schemaName, tableName);
		
		//����
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
		
		//�������
		if(whereCal != null && !"".equals(whereCal)){
			sbSQL.append(" WHERE ");
			sbSQL.append(whereCal);
		}
		return sbSQL.toString();
	}

	/**
	 * delete���
	 * @param tableName
	 * @param whereCal
	 * @return
	 */
	public String deleteSql(String schemaName, String tableName, String whereCal) {

		Assert.paramIsNotNull(tableName, "tableName");
		
		//DELETE FROM #tableName WHERE #whereCal
		StringBuilder sbSQL = new StringBuilder("DELETE FROM ");

		//����
		this.processTableName(sbSQL, schemaName, tableName);
				
		//�������
		if(whereCal != null && !"".equals(whereCal)){
			sbSQL.append(" WHERE ");
			sbSQL.append(whereCal);
		}
		
		return sbSQL.toString();
	
	}

	/**
	 * select���
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
		
		//����
		for ( int i = 0, n = colNames.length; i < n; i++){
			if(i != 0){
				sbSQL.append(", ");
			}
			
			this.processEnclosing(sbSQL, colNames[i]);
		}
				
		//����
		sbSQL.append(" FROM ");
		this.processTableName(sbSQL, schemaName, tableName);
		
		//ƴװSQL
		String sql = this.selectSql(sbSQL.toString(), whereCal, groupby, orderby);
		
		//����SQL
		return sql;
		
	}
	
	/**
	 * ��SELECT���Ļ����ϣ���������Ӿ�
	 * @param select �Ӿ�
	 * @param whereCal �����Ӿ�
	 * @param groupby �����Ӿ�
	 * @param orderby �����Ӿ�
	 * @return ƴװ���SELECT���
	 */
	protected String selectSql(String select, String whereCal, String groupby, String orderby) {
		
		StringBuilder sbSQL = new StringBuilder(select);
				
		//�������
		if(whereCal != null && !"".equals(whereCal)){
			sbSQL.append(" WHERE ");
			sbSQL.append(whereCal);
		}
		
		//�������
		if(!StringUtils.isEmpty(groupby)){
			sbSQL.append(" GROUP BY ");
			sbSQL.append(groupby);
		}	
		
		//�������
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
	 * ��¼���ϼ�SQL��Ĭ�ϴ�����schema name
	 * @param tableName
	 * @param condition
	 * @return
	 */
	public String countSql(String schemaName, String tableName, String condition, String idName){
		return this.countSql(schemaName, tableName, condition, idName, true);
	}
	
	/**
	 * ��¼���ϼ�SQL
	 * @param tableName
	 * @param condition
	 * @return
	 */
	protected String countSql(String schemaName, String tableName, String condition, String idName, boolean isProcessTableSchema){
		
		//SELECT COUNT(1) FROM WHERE condition )
		StringBuilder sbSQL = new StringBuilder("SELECT ");
		
		//����
		if(StringUtils.isEmpty(idName)){
			sbSQL.append("COUNT(1) ");
		}else{
			sbSQL.append("COUNT(");
			this.processEnclosing(sbSQL, idName);
			sbSQL.append(") ");
		}
		
		//����
		sbSQL.append("FROM ");
		if(isProcessTableSchema){
			this.processTableName(sbSQL, schemaName, tableName);
		}else{
			sbSQL.append(tableName);
		}
		
		//�������
		if(condition != null && !"".equals(condition)){
			sbSQL.append("WHERE ");
			sbSQL.append(condition);
		}
		return sbSQL.toString();
	}
	/**
	 * �������
	 * @param sbSQL sbSQL ���ڴ����SQL���
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
	 * ����enclosing
	 * @param sbSQL ���ڴ����SQL���
	 * @param key �ؼ���
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