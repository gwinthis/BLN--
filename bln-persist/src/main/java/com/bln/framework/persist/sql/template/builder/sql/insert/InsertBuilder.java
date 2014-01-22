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
 * Insert��乹����
 */
public class InsertBuilder extends SqlBuilderBase<IInsertSqlEntity> implements ISqlBuilder<IInsertSqlEntity>{

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.sql.ISqlBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public IInsertSqlEntity build(ITableStru tableStru) {
		
		//�ֶ�����
		IColumn[] columns = tableStru.getAllColumns().getColumns();
				
		//��Ҫ���ݿ��Զ����ɵ��ֶ�
		int fieldCount = columns.length;
		List<Integer> dbGenColIdxs = new ArrayList<Integer>(fieldCount);
		List<IColumn> dbGenColumns = new ArrayList<IColumn>(fieldCount);
		List<IColumn> notDbGenColumns = new ArrayList<IColumn>(fieldCount);
		
		//��Ҫ������ֶ����ƺ��ֶ�ֵ
		List<String> colNames = new ArrayList<String>(fieldCount);
		StringBuilder colValues = new StringBuilder();

		boolean firstAppend = true;
		for (int i = 0; i < fieldCount; i++){
			IColumn column = columns[i];
			
			//identy�ֶβ���Ҫinsert��������Ҫ��ȡ�Զ�����
			if(column.isIdenty()){
				
				dbGenColIdxs.add(i + 1);
				dbGenColumns.add(column);
				
				continue;
			}
			
			//����ֶ�
			colNames.add(columns[i].getColName());
			
			//��Ӷ���
			if(!firstAppend){
				colValues.append(", ");
			}else{
				firstAppend = false;
			}
						
			//�����ֶ�ֵ
			String colVal = "?";
			
			//�����Զ����ɵ��ֶ�
			IGenerator genertor = column.getGenerator();
			if(genertor != null){
				
				//��ȡ�ֶ�ֵ������
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
		
		//����insert���
		String sql = this.sqlDialect.insertSql(tableStru.getSchemaName(), tableStru.getTableName(), colNames.toArray(new String[]{}), colValues.toString());
		
		//��ֵSQLʵ��
		IInsertSqlEntity insertSqlEntity = new InsertSqlEntity();
		insertSqlEntity.setTableName(new String[]{tableStru.getTableName()});
		
		insertSqlEntity.setSqlInfo(sql);
		
		//�����Զ����ɵ��ֶ�
		insertSqlEntity.setNotDbGenColumns(notDbGenColumns);
		
		//�������ݿ����ɵ�ֵ
		if(dbGenColIdxs != null && dbGenColIdxs.size() > 0){
			
			insertSqlEntity.setDbGenColumns(dbGenColumns);
			
			int[] dbGenColIdxAsInt = new int[dbGenColIdxs.size()];
			for (int i = 0, n = dbGenColIdxs.size(); i < n; i++){
				dbGenColIdxAsInt[i] = dbGenColIdxs.get(i);
			}
			insertSqlEntity.setDbGenColIdxs(dbGenColIdxAsInt);
			
		}
		
		//����SQLʵ��
		return insertSqlEntity;
	}
}
