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
 * UPDATE��乹����
 */
public class UpdateBuilder extends SqlBuilderBase<IUpdateSqlEntity> implements ISqlBuilder<IUpdateSqlEntity>{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public IUpdateSqlEntity build(ITableStru tableStru) {
		
		//1.��ȡ�汾���ֶ�
		IColumn verCol = tableStru.getVerCol();
		String verColName = null;
		String verClause = "?";
		
		//2.��ȡ�汾�Ÿ��±��ʽ
		if(verCol != null){
			verColName = verCol.getColName();
			IGenerator verGenertor = verCol.getGenerator();
			if(verGenertor != null){
				
				//��ȡ�ֶ�ֵ������
				IColumnValueGenerator verValueGenerator = valGeneratorLib.getInstance(verGenertor.getType());
				
				if(verValueGenerator.isSurportColValWithDbFun()){
					verClause = verValueGenerator.getValClause(verGenertor.getParams(), "1");
				}
			}			
		}
						
		//3.����update���
		String[] colNames = tableStru.getAllColumns().getColNames();
		String sql = this.sqlDialect.updateSql(tableStru.getSchemaName(), tableStru.getTableName(), colNames, verColName, verClause, null);
		
		//5.��ֵUpdateSqlEntity
		IUpdateSqlEntity updateSqlEntity = new UpdateSqlEntity();
		updateSqlEntity.setTableName(new String[]{tableStru.getTableName()});
		
		updateSqlEntity.setSqlInfo(sql);
		updateSqlEntity.setVersionColumnClause(verClause);
		
		//6.����UpdateSqlEntity
		return updateSqlEntity;
	}

}
