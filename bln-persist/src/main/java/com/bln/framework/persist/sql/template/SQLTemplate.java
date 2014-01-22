/**
 * @author Gengw
 * Creatd at 2008-05-09
 */
package com.bln.framework.persist.sql.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.jdbc.converter.util.IConverterUtil;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.sql.dialect.ISQLDialect;
import com.bln.framework.persist.sql.entity.delete.IDeleteSqlEntity;
import com.bln.framework.persist.sql.entity.insert.IInsertSqlEntity;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.persist.sql.entity.select.SelectSqlEntity;
import com.bln.framework.persist.sql.entity.update.IUpdateSqlEntity;
import com.bln.framework.persist.sql.entity.update.UpdateSqlEntity;
import com.bln.framework.persist.sql.template.exception.SqlBuildException;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.util.asserter.Assert;

/**
 * SQL语句模板类
 */
public class SQLTemplate implements ISQLTemplate, ISQLTemplateBuildable{
	
	/**
	 * 转换器工具
	 */
	protected IConverterUtil converterUtil = null;
	
	/**
	 * SQL方言
	 */
	protected ISQLDialect sqlDialect = null;
	
	/**
	 * 当前表名
	 */
	protected String tableName = null;
	
	/**
	 * 当前schema名称
	 */
	protected String schemaName = null;
	
	/**
	 * 当前表名作为数组
	 */
	protected String[] tableNameAsArray = new String[1];
	
	/**
	 * 当前表的结构
	 */
	protected ITableStru tableStru = null;
	
	/**
	 * 整表字段的INSERT SQL
	 */
	protected IInsertSqlEntity insertSql = null;
	
	/**
	 * 整表字段的DELETE SQL
	 */
	protected IDeleteSqlEntity deleteSql = null;
	
	/**
	 *  整表字段的UPDATE SQL
	 */
	protected IUpdateSqlEntity updateSql = null;
		
	/**
	 * 整表字段的SELECT SQL
	 */
	protected ISelectSqlEntity selectSql = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplate#isExistsSql(com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public ISelectSqlEntity isExistsSql(IConditionClause conClause){
		
		//1.初始selectSql
		ISelectSqlEntity selectSqlEntity = new SelectSqlEntity();
		
		//定义表名
		selectSqlEntity.setTableName(tableNameAsArray);
				
		//2.构造SQL语句
		
		//2.1跟据条件，构造SQL语句
		String condition = null;
		List<Object> params = null;
		if(conClause != null){
			condition = conClause.getCondtion();
			params = conClause.getValues();
		}
				
		//2.2获取SQL
		String sql = this.sqlDialect.isExistsSql(schemaName, tableName, condition);
		
		//2.3设置SQL
		selectSqlEntity.setSqlInfo(sql);
		
		//3.添加参数
		selectSqlEntity.setParams(params);
		
		//4.返回selectSqlEntity
		return selectSqlEntity;
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplate#countSql(com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public ISelectSqlEntity countSql(IConditionClause conClause){
		
		//1.初始selectSql
		ISelectSqlEntity selectSqlEntity = new SelectSqlEntity();
		
		//定义表名
		selectSqlEntity.setTableName(tableNameAsArray);
				
		//2.构造SQL语句
		
		//2.1跟据条件，构造SQL语句
		String condition = null;
		List<Object> params = null;
		if(conClause != null){
			condition = conClause.getCondtion();
			params = conClause.getValues();
		}
		
		
		//2.2获取主键名称
		String idName = null;
		IColumn[] idColumns = tableStru.getIds();
		if(idColumns != null && idColumns.length > 0){
			idName = idColumns[0].getColName();
		}
		
		//2.3获取SQL
		String sql = this.sqlDialect.countSql(schemaName, tableName, condition, idName);
		
		//2.4设置SQL
		selectSqlEntity.setSqlInfo(sql);
		
		//3.添加参数
		selectSqlEntity.setParams(params);
		
		//4.返回selectSqlEntity
		return selectSqlEntity;
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sqltemplate.ISQLTemplate#queryForUpdateSql(java.lang.String, java.lang.String)
	 */
	public void queryForUpdateSql(ISelectSqlEntity selectSqlEntity){
		
		Assert.paramIsNotNull(selectSqlEntity, "selectSqlEntity");
		
		String sql = sqlDialect.queryForUpdateSql(selectSqlEntity.getSqlInfo());
		selectSqlEntity.setSqlInfo(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplate#queryWithOthTableSql(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String partColSelectSql(String[] cols, String condition, String groupby, String orderby, String othTables){
				
		//表名
		StringBuilder tables = new StringBuilder(tableName);
		
		//其他表名
		if(!StringUtils.isEmpty(othTables)){
			tables.append(", ").append(othTables);
		}
		
		//返回SQL语句
		return this.sqlDialect.selectSql(schemaName, tables.toString(), cols, condition, groupby, orderby);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplate#selectSql(com.bln.framework.persist.session.material.condition.IConditionClause, com.bln.framework.persist.session.material.pagination.IPagination, java.lang.String)
	 */
	public ISelectSqlEntity selectSql(IConditionClause conClause, IPagination pagination, String orderby){
		
		//1.初始selectSql
		ISelectSqlEntity selectSqlEntity = new SelectSqlEntity();
		
		//定义表名
		selectSqlEntity.setTableName(tableNameAsArray);
				
		//2.构造SQL语句
		
		//2.1跟据条件和排序信息，构造SQL语句
		String condition = null;
		List<Object> params = null;
		if(conClause != null){
			condition = conClause.getCondtion();
			params = conClause.getValues();
		}
		
		//拼装SQL语句
		String[] cols = tableStru.getAllColumns().getColNames();
		String sql = this.sqlDialect.selectSql(schemaName, tableName, cols, condition, null, orderby);
		
		selectSqlEntity.setSqlInfo(sql);
		
		//添加参数
		List<Object> allParams = new ArrayList<Object>();
		if(params != null){
			allParams.addAll(params);
		}
		
		selectSqlEntity.setParams(allParams);
		
		//2.2处理分页信息
		if(pagination != null){
			
			//添加分页信息
			sqlDialect.pagination(selectSqlEntity, pagination);
		}
		
		//3.返回selectSqlEntity
		return selectSqlEntity;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplate#insertSql(com.bln.framework.persist.row.IRow)
	 */
	public IInsertSqlEntity insertSql(IRow row){

		//1.校验参数
		Assert.paramIsNotNull(row, "row");
		
		List<String> colNamesInTime = row.getAllFields();
		List<String> colValsInTime = row.getAllFieldVals();
		
		IColumn[] columns = this.tableStru.getAllColumns().getColumns();
		
		int[] dbGenColIdxs = this.insertSql.getDbGenColIdxs();
		
		//2.构造Insert语句实体	
		IInsertSqlEntity insertSql = this.insertSql.clone();
		
		int fieldCount = columns.length;
		List<Object> params = new ArrayList<Object>(fieldCount - (dbGenColIdxs == null ? 0 : dbGenColIdxs.length));
		
		IColumn column = null;
		String val = null;

		try{
			for (int i = 0; i < fieldCount; i++){
				
				column = columns[i];

				//如果数据库没有自动生成，获取参数
				val = null;
				if(dbGenColIdxs == null || dbGenColIdxs.length <= 0 || Arrays.binarySearch(dbGenColIdxs, i + 1) < 0 ){
					int idx = colNamesInTime.indexOf(column.getColName());
					if(idx >= 0){
						val = colValsInTime.get(idx);
					}else{
						val = column.getDefaultVal();
					}
					Object param = this.converterUtil.convert(val, column.getColType());
					
					//添加参数
					params.add(param);					
				}
			}			
		}catch(Throwable e){
			SqlBuildException se = new SqlBuildException("when build insert sql occurs error: ");
			se.addContextValue("column", column);
			se.addContextValue("val", val);
			se.initCause(e);
			throw se;
		}
		
		
		insertSql.setParams(params);
		
		//3.返回SQL实体
		return insertSql;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplate#deleteSql(com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public IDeleteSqlEntity deleteSql(IConditionClause condition){
		
		//1.生成删除SQL
		IDeleteSqlEntity deleteSqlEntity = (IDeleteSqlEntity)this.deleteSql.clone();
		
		//2.构造SQL语句和参数
		if(condition != null){
			
			//2.1构造SQL语句
			StringBuilder sql = new StringBuilder(deleteSqlEntity.getSqlInfo());
			
			String con = condition.getCondtion();
			if(!StringUtils.isEmpty(con)){
				sql.append(" WHERE ").append(con);
			}
			
			//定义SQL语句
			deleteSqlEntity.setSqlInfo(sql.toString());
			
			//2.2构造参数
			List<Object> conParams = condition.getValues();
			if(conParams != null){
				deleteSqlEntity.setParams(conParams);
			}
		}
		
		//3.返回sqlEntity
		return deleteSqlEntity;
		
	}
	
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplate#updateSqlInTime(java.lang.String[], java.lang.String, boolean)
	 */
	public IUpdateSqlEntity updateSqlInTime(IRow row, IConditionClause condition){
		
		//1.校验参数
		Assert.paramIsNotNull(row, "row");
		
		//获取即时数据
		List<String> colNamesInTime = row.getAllFields();
		List<String> colValsInTime = row.getAllFieldVals();
		
		//获取表结构数据
		IColumn verColumn = this.tableStru.getVerCol();
		IColumn[] orderColumnNoIds = tableStru.getAllColumns().getOrderColumnNoIds();
		
		//2.拼装UPDATE语句
		List<Object> params = new ArrayList<Object>(colNamesInTime.size() + condition.getValues().size());
		
		//2.1构造更新字段
		
		//获取版本号字段
		String verColName = null;
		if(verColumn != null){
			verColName = verColumn.getColName();
		}
		
		String verValueClause = updateSql.getVersionColumnClause();
		
		//拼写更新字段
		List<String> colNames = new ArrayList<String>(colNamesInTime.size());
		for ( int i = 0, n = colNamesInTime.size(); i < n; i ++ ){
			
			String colName = colNamesInTime.get(i);
			
			//查找即时字段是否在表中存在
			int idx = Arrays.binarySearch ( orderColumnNoIds, colName );
			if( idx >= 0){
				
				colNames.add(colName);
								
				//填写参数值
				if(!colName.equals(verColName) || "?".equals(verValueClause)){
					String value = colValsInTime.get(i);
					Object param = this.converterUtil.convert(value, orderColumnNoIds[idx].getColType());
					params.add(param);
				}
				
			}
		}
		
		//把条件值添加到参数List中
		params.addAll(condition.getValues());
		
		//生成UPDATE语句
		String sql = sqlDialect.updateSql(schemaName, tableName, colNames.toArray(new String[]{}), verColName, verValueClause, condition.getCondtion());
		
		//3.赋值UpdateSqlEntity
		IUpdateSqlEntity updateSqlEntity = new UpdateSqlEntity();
		
		updateSqlEntity.setSqlInfo(sql);
		updateSqlEntity.setVersionColumnClause(updateSql.getVersionColumnClause());
		updateSqlEntity.setParams(params);
		
		//4.返回UPDATE语句
		return updateSqlEntity;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@SuppressWarnings("unchecked")
	public String toString(){
		StringBuilder info = new StringBuilder("SqlTemplate info: ");
		
		try {
			Map<String, String> spec = BeanUtils.describe(this);
			info.append(spec);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return info.toString();
	}
	
	/**
	 * @return the sqlDialect
	 */
	public ISQLDialect getSqlDialect() {
		return sqlDialect;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setSqlDialect(com.bln.framework.persist.sql.template.sqldialect.ISQLDialect)
	 */
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setSqlDialect(com.bln.framework.persist.sql.dialect.ISQLDialect)
	 */
	public void setSqlDialect(ISQLDialect sqlDialect) {
		this.sqlDialect = sqlDialect;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setTableName(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setTableName(java.lang.String)
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
		tableNameAsArray[0] = tableName;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setInsertSql(com.bln.framework.persist.sql.entity.insert.IInsertSqlEntity)
	 */
	public void setInsertSql(IInsertSqlEntity insertSql) {
		this.insertSql = insertSql;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setDeleteSql(com.bln.framework.persist.sql.entity.delete.IDeleteSqlEntity)
	 */
	public void setDeleteSql(IDeleteSqlEntity deleteSql) {
		this.deleteSql = deleteSql;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setUpdateSql(com.bln.framework.persist.sql.entity.update.IUpdateSqlEntity)
	 */
	public void setUpdateSql(IUpdateSqlEntity updateSql) {
		this.updateSql = updateSql;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setSelectSql(com.bln.framework.persist.sql.entity.select.ISelectSqlEntity)
	 */
	public void setSelectSql(ISelectSqlEntity selectSql) {
		this.selectSql = selectSql;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setTableStru(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public void setTableStru(ITableStru tableStru) {
		this.tableStru = tableStru;
		this.schemaName = tableStru.getSchemaName();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#setConverterUtil(com.bln.framework.persist.jdbc.converter.util.IConverterUtil)
	 */
	public void setConverterUtil(IConverterUtil converterUtil) {
		this.converterUtil = converterUtil;
	}

	/**
	 * @return the insertSql
	 */
	public IInsertSqlEntity getInsertSql() {
		return insertSql;
	}

	/**
	 * @return the updateSql
	 */
	public IUpdateSqlEntity getUpdateSql() {
		return updateSql;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.ISQLTemplateBuildable#buildToSqlt()
	 */
	public ISQLTemplate buildToSqlt() {
		return this;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public ISQLTemplateBuildable clone(){
		
		ISQLTemplateBuildable sqlt = new SQLTemplate();
		
		sqlt.setConverterUtil(converterUtil);
		sqlt.setSqlDialect(sqlDialect);
		
		return sqlt;
	}

	
}
