/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.sql.template.builder.sql.update;

import com.bln.framework.persist.sql.entity.update.IUpdateSqlEntity;
import com.bln.framework.persist.sql.entity.update.UpdateSqlEntity;
import com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder;
import com.bln.framework.persist.sql.template.builder.sql.SqlBuilderBase;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.component.column.generator.IGenerator;
import com.bln.framework.persist.valgenerator.IColumnValueGenerator;

/**
 * UPDATE语句构造器
 */
public class UpdateBuilder extends SqlBuilderBase<IUpdateSqlEntity> implements ISqlBuilder<IUpdateSqlEntity>{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public IUpdateSqlEntity build(ITableStru tableStru) {
		
		//1.获取版本号字段
		IColumn verCol = tableStru.getVerCol();
		String verColName = null;
		String verClause = "?";
		
		//2.获取版本号更新表达式
		if(verCol != null){
			verColName = verCol.getColName();
			IGenerator verGenertor = verCol.getGenerator();
			if(verGenertor != null){
				
				//获取字段值生成器
				IColumnValueGenerator verValueGenerator = valGeneratorLib.getInstance(verGenertor.getType());
				
				if(verValueGenerator.isSurportColValWithDbFun()){
					verClause = verValueGenerator.getValClause(verGenertor.getParams(), "1");
				}
			}			
		}
						
		//3.生成update语句
		String[] colNames = tableStru.getAllColumns().getColNames();
		String sql = this.sqlDialect.updateSql(tableStru.getSchemaName(), tableStru.getTableName(), colNames, verColName, verClause, null);
		
		//5.赋值UpdateSqlEntity
		IUpdateSqlEntity updateSqlEntity = new UpdateSqlEntity();
		updateSqlEntity.setTableName(new String[]{tableStru.getTableName()});
		
		updateSqlEntity.setSqlInfo(sql);
		updateSqlEntity.setVersionColumnClause(verClause);
		
		//6.返回UpdateSqlEntity
		return updateSqlEntity;
	}

}
