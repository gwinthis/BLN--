/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.sql.template.builder.sql.delete;

import com.bln.framework.persist.sql.entity.delete.DeleteSqlEntity;
import com.bln.framework.persist.sql.entity.delete.IDeleteSqlEntity;
import com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder;
import com.bln.framework.persist.sql.template.builder.sql.SqlBuilderBase;
import com.bln.framework.persist.tablestru.ITableStru;

/**
 * DELETE��乹����
 */
public class DeleteBuilder extends SqlBuilderBase<IDeleteSqlEntity> implements ISqlBuilder<IDeleteSqlEntity>{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public IDeleteSqlEntity build(ITableStru tableStru) {
		
		//����delete���
		String tableName = tableStru.getTableName();
		
		String sql = this.sqlDialect.deleteSql(tableStru.getSchemaName(), tableName, null);
		
		IDeleteSqlEntity deleteSqlEntity = new DeleteSqlEntity();
		deleteSqlEntity.setSqlInfo(sql);
		deleteSqlEntity.setTableName(new String[]{tableName});
		
		//����SQL
		return deleteSqlEntity;
	}
}
