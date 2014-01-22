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
 * DELETE语句构造器
 */
public class DeleteBuilder extends SqlBuilderBase<IDeleteSqlEntity> implements ISqlBuilder<IDeleteSqlEntity>{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public IDeleteSqlEntity build(ITableStru tableStru) {
		
		//生成delete语句
		String tableName = tableStru.getTableName();
		
		String sql = this.sqlDialect.deleteSql(tableStru.getSchemaName(), tableName, null);
		
		IDeleteSqlEntity deleteSqlEntity = new DeleteSqlEntity();
		deleteSqlEntity.setSqlInfo(sql);
		deleteSqlEntity.setTableName(new String[]{tableName});
		
		//返回SQL
		return deleteSqlEntity;
	}
}
