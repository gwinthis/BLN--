/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.sql.template.builder.sql.insert;

import java.util.ArrayList;
import java.util.List;

import com.bln.framework.persist.sql.entity.insert.IInsertSqlEntity;
import com.bln.framework.persist.sql.entity.insert.InsertSqlEntity;
import com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder;
import com.bln.framework.persist.sql.template.builder.sql.SqlBuilderBase;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.component.column.generator.IGenerator;
import com.bln.framework.persist.valgenerator.IColumnValueGenerator;

/**
 * Insert语句构造器
 */
public class InsertBuilder extends SqlBuilderBase<IInsertSqlEntity> implements ISqlBuilder<IInsertSqlEntity>{

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public IInsertSqlEntity build(ITableStru tableStru) {
		
		//字段数组
		IColumn[] columns = tableStru.getAllColumns().getColumns();
				
		//需要数据库自动生成的字段
		int fieldCount = columns.length;
		List<Integer> dbGenColIdxs = new ArrayList<Integer>(fieldCount);
		List<IColumn> dbGenColumns = new ArrayList<IColumn>(fieldCount);
		List<IColumn> notDbGenColumns = new ArrayList<IColumn>(fieldCount);
		
		//需要插入的字段名称和字段值
		List<String> colNames = new ArrayList<String>(fieldCount);
		StringBuilder colValues = new StringBuilder();

		boolean firstAppend = true;
		for (int i = 0; i < fieldCount; i++){
			IColumn column = columns[i];
			
			//identy字段不需要insert，不过需要获取自动生成
			if(column.isIdenty()){
				
				dbGenColIdxs.add(i + 1);
				dbGenColumns.add(column);
				
				continue;
			}
			
			//添加字段
			colNames.add(columns[i].getColName());
			
			//添加逗号
			if(!firstAppend){
				colValues.append(", ");
			}else{
				firstAppend = false;
			}
						
			//构造字段值
			String colVal = "?";
			
			//定义自动生成的字段
			IGenerator genertor = column.getGenerator();
			if(genertor != null){
				
				//获取字段值生成器
				IColumnValueGenerator colValueGenerator = valGeneratorLib.getInstance(genertor.getType());
				
				if(!colValueGenerator.isSurportColValWithDbFun()){
					notDbGenColumns.add(column);
				}else{
					colVal = colValueGenerator.getValClause(genertor.getParams(), null);
					dbGenColIdxs.add(i + 1);
					dbGenColumns.add(column);					
				}
				
			}
			
			colValues.append(colVal);
		}
		
		//生成insert语句
		String sql = this.sqlDialect.insertSql(tableStru.getSchemaName(), tableStru.getTableName(), colNames.toArray(new String[]{}), colValues.toString());
		
		//赋值SQL实体
		IInsertSqlEntity insertSqlEntity = new InsertSqlEntity();
		insertSqlEntity.setTableName(new String[]{tableStru.getTableName()});
		
		insertSqlEntity.setSqlInfo(sql);
		
		//定义自动生成的字段
		insertSqlEntity.setNotDbGenColumns(notDbGenColumns);
		
		//定义数据库生成的值
		if(dbGenColIdxs != null && dbGenColIdxs.size() > 0){
			
			insertSqlEntity.setDbGenColumns(dbGenColumns);
			
			int[] dbGenColIdxAsInt = new int[dbGenColIdxs.size()];
			for (int i = 0, n = dbGenColIdxs.size(); i < n; i++){
				dbGenColIdxAsInt[i] = dbGenColIdxs.get(i);
			}
			insertSqlEntity.setDbGenColIdxs(dbGenColIdxAsInt);
			
		}
		
		//返回SQL实体
		return insertSqlEntity;
	}
}
