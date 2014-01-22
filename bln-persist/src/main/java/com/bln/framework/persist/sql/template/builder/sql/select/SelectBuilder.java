/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.sql.template.builder.sql.select;

import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;
import com.bln.framework.persist.sql.entity.select.SelectSqlEntity;
import com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder;
import com.bln.framework.persist.sql.template.builder.sql.SqlBuilderBase;
import com.bln.framework.persist.tablestru.ITableStru;

/**
 * SELECT��乹����
 */
public class SelectBuilder extends SqlBuilderBase<ISelectSqlEntity> implements ISqlBuilder<ISelectSqlEntity>{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public ISelectSqlEntity build(ITableStru tableStru) {
		
		//�ֶ���
		String[] colNames = tableStru.getAllColumns().getColNames();
		
		//���select���
		String sql = this.sqlDialect.selectSql(tableStru.getSchemaName(), tableStru.getTableName(), colNames, null, null, null);
		ISelectSqlEntity selectSqlEntity = new SelectSqlEntity();
		selectSqlEntity.setTableName(new String[]{tableStru.getTableName()});
		
		//��ֵSelectEntity
		selectSqlEntity.setSqlInfo(sql);
		
		//����SelectEntity���
		return selectSqlEntity;
	
	}
}
