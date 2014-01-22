/**
 * @author gengw
 * Created on May 25, 2012
 */
package com.bln.framework.persistutil.executebuilder.stru.xml.builder;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.exception.UnsupportedException;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;
import com.bln.framework.persist.tablestru.config.entity.ITableStruConfigEntity;
import com.bln.framework.persist.tablestru.config.entity.TableStruConfigEntity;
import com.bln.framework.persist.tablestru.config.xml.ITableStruXmlConfig;
import com.bln.framework.persist.tablestru.config.xml.TableStruXmlConfig;
import com.bln.framework.persistutil.executebuilder.ExecuteBuilderBase;
import com.bln.framework.persistutil.task.ITaskConfig;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;

/**
 * XML��ṹ�ĵ�������
 */
public abstract class TableStruXmlBuilderBase extends ExecuteBuilderBase{
	
	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(TableStruXmlBuilderBase.class);

	
	/**
	 * ���캯��
	 */
	public TableStruXmlBuilderBase(){
		this.executeDesc = "build table structure file!";
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.executebuilder.IExecuteBuilder#build()
	 */
	public void build() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
		
		//��ȡ�û�
		String owner = taskConfig.getDbConnection().getAttr(ITaskConfig.ATTR_USER).toUpperCase();
		
		//��ȡschema
		String schemaName = taskConfig.getSchema().getAttr(ITaskConfig.ATTR_NAME);
		
		//��ȡҪ���ɵı���
		String tableNames = taskConfig.getTable().getAttr(ITaskConfig.ATTR_NAME);
		
		//��ȡ�ֶ�
		List<IRow> columns = this.columns(owner, tableNames);

		//��ȡ������
		List<IRow> references = this.references(owner, tableNames);
		
		//��ȡ�汾���ֶ�����
		ITaskConfigEntity versionEntity = taskConfig.getVersionColumnName();
		String versionColumnName = versionEntity.getAttr(ITaskConfig.ATTR_NAME);
		
		//��ȡ��ṹ�洢λ��
		ITaskConfigEntity tableStruUrl = taskConfig.getTableStruUrl();

		String fileUrl = tableStruUrl.getAttr(ITaskConfig.ATTR_NAME);
		if(!fileUrl.endsWith("/")){
			fileUrl = fileUrl + "/";
		}
		
		//������ṹ����
		for ( IRow table : this.tables){
			String tableName = table.getValue("TABLE_NAME");
			ITableStruXmlConfig tableStru = this.assembleTableStru(schemaName, tableName, columns, references, versionColumnName);
			byte[] bytes = tableStru.writeToBytes();
			
			String allFileUrl = fileUrl + tableName + ".xml";
			File file = new File(allFileUrl);
			FileUtils.writeByteArrayToFile(file, bytes);
			
			log.info("build table " + allFileUrl + " sucessufully!");
		}
	}
	
	/**
	 * װ���ṹ
	 * @param schemaName ģʽ����
	 * @param tableName ����
	 * @param columns �ֶ���
	 * @param references ������
	 * @param versionColumnName �汾���ֶ�����
	 * @return ��ṹ����
	 */ 
	protected ITableStruXmlConfig assembleTableStru(String schemaName, String tableName, List<IRow> columns, List<IRow> references, String versionColumnName){
		
		//�����ṹ����
		ITableStruXmlConfig tableStru = new TableStruXmlConfig();
		
		//��ʼ���ṹ
		tableStru.initFile();
		tableStru.initTable(tableName, schemaName);
		
		//�����ֶ�
		List<ITableStruConfigEntity> ids = new ArrayList<ITableStruConfigEntity>();
		ITableStruConfigEntity versionColumn = null;
		
		List<ITableStruConfigEntity> columnEntitys = new ArrayList<ITableStruConfigEntity>();
		
		for(String columnTableName = columns.get(0).getValue("TABLE_NAME"); tableName.equals(columnTableName); columnTableName = columns.size() == 0 ? null : columns.get(0).getValue("TABLE_NAME")){
			
			IRow column = columns.get(0);
			
			//�����ֶ�ʵ��
			ITableStruConfigEntity columnEntity = createColumnEntity(column);
			columnEntitys.add(columnEntity);
			
			//���������ֶ�
			if("P".equals(column.getValue("CONSTRAINT_TYPE"))){
				ids.add(columnEntity);
			}
			
			//���ð汾���ֶ�
			if( versionColumn == null && columnEntity.getAttr(ITableStruConfig.ATTR_NAME).equals(versionColumnName)){
				versionColumn = columnEntity;
			}
			
			//ɾ��ʵ��
			columns.remove(0);
		}
		
		//��������
		if(ids.size() > 0){
			this.processIDs(tableName, tableStru, ids);
		}
		
		//����汾��
		if(versionColumn != null){
			this.processVersionColumn(tableStru, versionColumn);
		}
		
		//�������
		if(references != null && references.size() > 0){
			this.processFKs(tableName, tableStru, references);
		}
		
		//�����ֶ�
		tableStru.setColumns(columnEntitys);
		
		//���ر�ṹ
		return tableStru;
	}
	
	/**
	 * �����ֶ�ʵ��
	 * @param column
	 * @return
	 */
	protected ITableStruConfigEntity createColumnEntity(IRow column){
		
		ITableStruConfigEntity columnEntity = new TableStruConfigEntity(column);
		
		//�ֶνڵ�
		columnEntity.setType(ITableStruConfig.NODE_COLUMN_NM);
		
		//�ֶ�����
		columnEntity.setAttr(ITableStruConfig.ATTR_NAME, column.getValue("COLUMN_NAME"));
		
		//�ֶ�����
		String type = getColumnType(column);
		columnEntity.setAttr(ITableStruConfig.ATTR_TYPE, type);
		
		//����(�ֽ�)
		String length = column.getValue("DATA_LENGTH");
		columnEntity.setAttr(ITableStruConfig.ATTR_LENGTH, length);

		//С����󾫶�
		String scale = column.getValue("DATA_SCALE");
		if(!StringUtils.isEmpty(scale) && !"0".equals(scale)){
			columnEntity.setAttr(ITableStruConfig.ATTR_SCALE, scale);
		}
		
		//�Ƿ�ǿ�
		boolean mandtory = "0".equals(column.getValue("NULLABLE"));
		if(mandtory){
			columnEntity.setAttr(ITableStruConfig.ATTR_IS_MANDATORY, Boolean.toString(mandtory));
		}
		
		//�Ƿ�identy
		String isIdentyStr = column.getValue("IS_IDENTY");
		
		boolean isIdenty = !StringUtils.isEmpty(isIdentyStr) && "1".equals(isIdentyStr);
		if(isIdenty){
			columnEntity.setAttr(ITableStruConfig.ATTR_IS_IDENTY, Boolean.toString(isIdenty));
		}

		//Ĭ��ֵ
		String defaultValue = column.getValue("DATA_DEFAULT");
		if(!StringUtils.isEmpty(defaultValue)){
			defaultValue = this.doProcessDefaultValue(column, defaultValue);
			columnEntity.setAttr(ITableStruConfig.ATTR_DEFAULT_VALUE, defaultValue);
		}
		
		//�����ֶ�ʵ��
		return columnEntity;
		
	}
	
	/**
	 * ����Ĭ��ֵ
	 * @param column
	 * @param defaultValue
	 * @return
	 */
	protected String doProcessDefaultValue(IRow column, String defaultValue){
		if(defaultValue.startsWith("'")){
			defaultValue = defaultValue.substring(1);
		}
		
		int length = defaultValue.length();
		String lastChar = defaultValue.substring( length - 2, length - 1);
		if(StringUtils.isWhitespace(lastChar) || "'".equals(lastChar)){
			defaultValue = defaultValue.substring(0, defaultValue.length() - 1);
		}
		
		return defaultValue;
	
	}
	
	/**
	 * ��������
	 * @param tableStru
	 * @param column
	 */
	protected void processIDs(String tableName, ITableStruXmlConfig tableStru, List<ITableStruConfigEntity> columns){
		
		// 1.����������
		boolean isAutoCreateSeqGen = this.taskConfig.isAutoCreateSeqGenerator();
		if(isAutoCreateSeqGen){
			
			//Ϊ��һ������������������������ֶ�Ϊidenty����ô����Ҫ������������
			ITableStruConfigEntity firstIdColumn = columns.get(0);
			if(!"true".equals(firstIdColumn.getAttr(ITableStruXmlConfig.ATTR_IS_IDENTY))){
				ITableStruConfigEntity generator = new TableStruConfigEntity(null);
				
				//���ýڵ�����
				generator.setType(ITableStruConfig.NODE_GENERATOR_NM);
				
				//��������������
				generator.setAttr(ITableStruConfig.ATTR_TYPE, "sequence");
				
				//�������
				ITableStruConfigEntity param = new TableStruConfigEntity(null);
				
				param.setType(ITableStruConfig.NODE_PARM_NM);
				param.setAttr(ITableStruConfig.ATTR_NAME, "seqname");
				param.setAttr(ITableStruConfig.ATTR_VALUE, "SEQ_" + tableName);
				
				//�������ж������
				generator.addSubConfigEntity(param);
				
				//���������
				firstIdColumn.addSubConfigEntity(generator);			
			}			
		}

		//2.����������ʶ
		List<ITableStruConfigEntity> idColumns = new ArrayList<ITableStruConfigEntity>(columns.size());
		for (ITableStruConfigEntity id : columns){
			ITableStruConfigEntity idColumn = new TableStruConfigEntity(null);
			
			idColumn.setType(ITableStruConfig.NODE_ID_NM);
			idColumn.setAttrMap(id.getAttrMap());
			
			idColumns.add(idColumn);
		}
		
		tableStru.setIDS(idColumns);
		
	}
	
	/**
	 * ����汾���ֶ�
	 * @param tableStru
	 * @param column
	 */
	protected void processVersionColumn(ITableStruXmlConfig tableStru, ITableStruConfigEntity column){
		
		//1.����������
		boolean isAutoCreateVersionGen = this.taskConfig.isAutoCreateVersionGenerator();
		if(isAutoCreateVersionGen){
			ITableStruConfigEntity generator = new TableStruConfigEntity(null);
			
			//���ýڵ�����
			generator.setType(ITableStruConfig.NODE_GENERATOR_NM);
			
			String columnType = column.getAttr(ITableStruConfig.ATTR_TYPE);
			//��������������
			if("TIMESTAMP".equals(columnType)){
				
				//���幹��������
				generator.setAttr(ITableStruConfig.ATTR_TYPE, "timestamp_version");
				
			}else if("DECIMAL".equals(columnType)||"BIGINT".equals(columnType) || "INTEGER".equals(columnType)||"INT".equals(columnType)){
				
				//���幹��������
				generator.setAttr(ITableStruConfig.ATTR_TYPE, "numeric_version");
				
				//�������
				ITableStruConfigEntity param = new TableStruConfigEntity(null);
				
				param.setType(ITableStruConfig.NODE_PARM_NM);
				param.setAttr(ITableStruConfig.ATTR_NAME, "column");
				param.setAttr(ITableStruConfig.ATTR_VALUE, column.getAttr(ITableStruConfig.ATTR_NAME));
				
				generator.addSubConfigEntity(param);
				
			}else{
				throw new UnsupportedException("it does not support column type " + columnType + " for generator!");
			}
			
			column.addSubConfigEntity(generator);			
		}
		
		//2.���ɰ汾�ű�ʶ
		ITableStruConfigEntity versionColumn = new TableStruConfigEntity(null);
		versionColumn.setType(ITableStruConfig.NODE_VERCOL_NM);
		versionColumn.setAttrMap(column.getAttrMap());
		
		tableStru.setVerCol(versionColumn);
		
	}
	
	/**
	 * �������
	 * @param tableStru
	 * @param column
	 */
	protected void processFKs(String tableName, ITableStruXmlConfig tableStru, List<IRow> refs){
		
		//1.���������ʶ
		List<ITableStruConfigEntity> fkColumns = new ArrayList<ITableStruConfigEntity>(refs.size());
		ITableStruConfigEntity fkColumn = null;
		for(String columnTableName = refs.size() == 0 ? null : refs.get(0).getValue("PARENT_TABLENAME"), fkName = ""; tableName.equals(columnTableName); columnTableName = refs.size() == 0 ? null : refs.get(0).getValue("PARENT_TABLENAME")){
			
			IRow fk = refs.get(0);
						
			//���ɽڵ�
			if(!fkName.equals(fk.getValue("CONSTRAINT_NAME"))){
				
				fkName = fk.getValue("CONSTRAINT_NAME");
				
				fkColumn = new TableStruConfigEntity(null);
				fkColumns.add(fkColumn);
				
				//���ýڵ�����
				fkColumn.setType(ITableStruConfig.NODE_CHILDREF_NM);
				
				//�����������
				fkColumn.setAttr(ITableStruConfig.ATTR_NAME, fkName);
				
				//�ӱ�����
				fkColumn.setAttr(ITableStruConfig.ATTR_CHILDTABLE, fk.getValue("CHILD_TABLENAME"));
			}
			
			//�����ֶ�
			TableStruConfigEntity refColumn = new TableStruConfigEntity(null);

			refColumn.setType(ITableStruConfig.NODE_REF_NM);
			refColumn.setAttr(ITableStruConfig.ATTR_PCOLNAME, fk.getValue("PARENT_COLUMN"));
			refColumn.setAttr(ITableStruConfig.ATTR_CHILDCOLNAME, fk.getValue("CHILD_COLUMN"));
			
			fkColumn.addSubConfigEntity(refColumn);
			
			refs.remove(0);
		}
		
		tableStru.setIDS(fkColumns);
		
	}
	
	
	/**
	 * ����ֶ�����
	 * @param column �ֶ���Ϣ
	 * @return �ֶ�����
	 */
	protected String getColumnType (IRow column){
		
		
		String dataType = column.getValue("DATA_TYPE").toUpperCase();
		
		String columnType = null;
		
		if(this.in(dataType, "DECIMAL", "SMALLINT", "TINYINT", "INTEGER", "BIGINT", "FLOAT", "REAL", "DOUBLE", "BIT", "NUMERIC")){
			columnType = dataType;

		} else if("INT".equals(dataType)){
			columnType = "INTEGER";
			
		} else if(this.in(dataType,"NUMBER", "MONEY")){
			columnType = "DECIMAL";
			
		} else if(this.in(dataType, "VARCHAR2", "NVARCHAR", "VARBINARY", "TEXT", "VARCHAR", "CHAR", "CHARACTER")){
			columnType = "VARCHAR";
			
		} else if("DATE".equals(dataType)){
			columnType = "DATE";
		} else if("TIME".equals(dataType)){
			columnType = "TIME";
		} else if(dataType.startsWith("TIMESTAMP")){
			columnType = "TIMESTAMP";
		} else if("DATETIME".equals(dataType) ){
			columnType = "TIMESTAMP";
		} else if(dataType.startsWith("CLOB")){
			columnType = "CLOB";
		} else if(dataType.startsWith("BLOB")){
			columnType = "BLOB";
		}
		
		if(columnType == null){
			throw new UnsupportedException("it can't identify column type " + dataType + "!");
		}
		return columnType;
	}
	
	protected boolean in(String data, String... values){
		
		if(data == null || values == null || values.length <= 0){
			return false;
		}
		
		for ( String value : values){
			if(data.equals(value)){
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * ��ñ�������ֶ�
	 * @param session
	 * @param tableEntity
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected abstract List<IRow> columns(String owner, String tableNames) throws SQLException, IOException;
	
	/**
	 * 
	 * ��ñ�Ĺ�����
	 * @param session
	 * @param tableEntity
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected abstract List<IRow> references(String owner, String tableNames) throws SQLException, IOException;
}
