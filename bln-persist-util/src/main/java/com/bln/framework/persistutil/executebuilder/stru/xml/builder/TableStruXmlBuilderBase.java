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
 * XML表结构文档生成器
 */
public abstract class TableStruXmlBuilderBase extends ExecuteBuilderBase{
	
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(TableStruXmlBuilderBase.class);

	
	/**
	 * 构造函数
	 */
	public TableStruXmlBuilderBase(){
		this.executeDesc = "build table structure file!";
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.executebuilder.IExecuteBuilder#build()
	 */
	public void build() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
		
		//获取用户
		String owner = taskConfig.getDbConnection().getAttr(ITaskConfig.ATTR_USER).toUpperCase();
		
		//获取schema
		String schemaName = taskConfig.getSchema().getAttr(ITaskConfig.ATTR_NAME);
		
		//获取要生成的表名
		String tableNames = taskConfig.getTable().getAttr(ITaskConfig.ATTR_NAME);
		
		//获取字段
		List<IRow> columns = this.columns(owner, tableNames);

		//获取关联项
		List<IRow> references = this.references(owner, tableNames);
		
		//获取版本号字段名称
		ITaskConfigEntity versionEntity = taskConfig.getVersionColumnName();
		String versionColumnName = versionEntity.getAttr(ITaskConfig.ATTR_NAME);
		
		//获取表结构存储位置
		ITaskConfigEntity tableStruUrl = taskConfig.getTableStruUrl();

		String fileUrl = tableStruUrl.getAttr(ITaskConfig.ATTR_NAME);
		if(!fileUrl.endsWith("/")){
			fileUrl = fileUrl + "/";
		}
		
		//创建表结构配置
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
	 * 装配表结构
	 * @param schemaName 模式名称
	 * @param tableName 表名
	 * @param columns 字段名
	 * @param references 关联项
	 * @param versionColumnName 版本号字段名称
	 * @return 表结构配置
	 */ 
	protected ITableStruXmlConfig assembleTableStru(String schemaName, String tableName, List<IRow> columns, List<IRow> references, String versionColumnName){
		
		//定义表结构配置
		ITableStruXmlConfig tableStru = new TableStruXmlConfig();
		
		//初始化结构
		tableStru.initFile();
		tableStru.initTable(tableName, schemaName);
		
		//设置字段
		List<ITableStruConfigEntity> ids = new ArrayList<ITableStruConfigEntity>();
		ITableStruConfigEntity versionColumn = null;
		
		List<ITableStruConfigEntity> columnEntitys = new ArrayList<ITableStruConfigEntity>();
		
		for(String columnTableName = columns.get(0).getValue("TABLE_NAME"); tableName.equals(columnTableName); columnTableName = columns.size() == 0 ? null : columns.get(0).getValue("TABLE_NAME")){
			
			IRow column = columns.get(0);
			
			//生成字段实体
			ITableStruConfigEntity columnEntity = createColumnEntity(column);
			columnEntitys.add(columnEntity);
			
			//设置主键字段
			if("P".equals(column.getValue("CONSTRAINT_TYPE"))){
				ids.add(columnEntity);
			}
			
			//设置版本号字段
			if( versionColumn == null && columnEntity.getAttr(ITableStruConfig.ATTR_NAME).equals(versionColumnName)){
				versionColumn = columnEntity;
			}
			
			//删除实体
			columns.remove(0);
		}
		
		//处理主键
		if(ids.size() > 0){
			this.processIDs(tableName, tableStru, ids);
		}
		
		//处理版本号
		if(versionColumn != null){
			this.processVersionColumn(tableStru, versionColumn);
		}
		
		//设置外键
		if(references != null && references.size() > 0){
			this.processFKs(tableName, tableStru, references);
		}
		
		//设置字段
		tableStru.setColumns(columnEntitys);
		
		//返回表结构
		return tableStru;
	}
	
	/**
	 * 生成字段实体
	 * @param column
	 * @return
	 */
	protected ITableStruConfigEntity createColumnEntity(IRow column){
		
		ITableStruConfigEntity columnEntity = new TableStruConfigEntity(column);
		
		//字段节点
		columnEntity.setType(ITableStruConfig.NODE_COLUMN_NM);
		
		//字段名称
		columnEntity.setAttr(ITableStruConfig.ATTR_NAME, column.getValue("COLUMN_NAME"));
		
		//字段类型
		String type = getColumnType(column);
		columnEntity.setAttr(ITableStruConfig.ATTR_TYPE, type);
		
		//长度(字节)
		String length = column.getValue("DATA_LENGTH");
		columnEntity.setAttr(ITableStruConfig.ATTR_LENGTH, length);

		//小数点后精度
		String scale = column.getValue("DATA_SCALE");
		if(!StringUtils.isEmpty(scale) && !"0".equals(scale)){
			columnEntity.setAttr(ITableStruConfig.ATTR_SCALE, scale);
		}
		
		//是否非空
		boolean mandtory = "0".equals(column.getValue("NULLABLE"));
		if(mandtory){
			columnEntity.setAttr(ITableStruConfig.ATTR_IS_MANDATORY, Boolean.toString(mandtory));
		}
		
		//是否identy
		String isIdentyStr = column.getValue("IS_IDENTY");
		
		boolean isIdenty = !StringUtils.isEmpty(isIdentyStr) && "1".equals(isIdentyStr);
		if(isIdenty){
			columnEntity.setAttr(ITableStruConfig.ATTR_IS_IDENTY, Boolean.toString(isIdenty));
		}

		//默认值
		String defaultValue = column.getValue("DATA_DEFAULT");
		if(!StringUtils.isEmpty(defaultValue)){
			defaultValue = this.doProcessDefaultValue(column, defaultValue);
			columnEntity.setAttr(ITableStruConfig.ATTR_DEFAULT_VALUE, defaultValue);
		}
		
		//返回字段实体
		return columnEntity;
		
	}
	
	/**
	 * 处理默认值
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
	 * 处理主键
	 * @param tableStru
	 * @param column
	 */
	protected void processIDs(String tableName, ITableStruXmlConfig tableStru, List<ITableStruConfigEntity> columns){
		
		// 1.定义生成器
		boolean isAutoCreateSeqGen = this.taskConfig.isAutoCreateSeqGenerator();
		if(isAutoCreateSeqGen){
			
			//为第一个主键定义生成器，如果该字段为identy，那么不需要定义生成器。
			ITableStruConfigEntity firstIdColumn = columns.get(0);
			if(!"true".equals(firstIdColumn.getAttr(ITableStruXmlConfig.ATTR_IS_IDENTY))){
				ITableStruConfigEntity generator = new TableStruConfigEntity(null);
				
				//设置节点类型
				generator.setType(ITableStruConfig.NODE_GENERATOR_NM);
				
				//设置生成器类型
				generator.setAttr(ITableStruConfig.ATTR_TYPE, "sequence");
				
				//定义参数
				ITableStruConfigEntity param = new TableStruConfigEntity(null);
				
				param.setType(ITableStruConfig.NODE_PARM_NM);
				param.setAttr(ITableStruConfig.ATTR_NAME, "seqname");
				param.setAttr(ITableStruConfig.ATTR_VALUE, "SEQ_" + tableName);
				
				//生成器中定义参数
				generator.addSubConfigEntity(param);
				
				//添加生成器
				firstIdColumn.addSubConfigEntity(generator);			
			}			
		}

		//2.生成主键标识
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
	 * 处理版本号字段
	 * @param tableStru
	 * @param column
	 */
	protected void processVersionColumn(ITableStruXmlConfig tableStru, ITableStruConfigEntity column){
		
		//1.定义生成器
		boolean isAutoCreateVersionGen = this.taskConfig.isAutoCreateVersionGenerator();
		if(isAutoCreateVersionGen){
			ITableStruConfigEntity generator = new TableStruConfigEntity(null);
			
			//设置节点类型
			generator.setType(ITableStruConfig.NODE_GENERATOR_NM);
			
			String columnType = column.getAttr(ITableStruConfig.ATTR_TYPE);
			//设置生成器类型
			if("TIMESTAMP".equals(columnType)){
				
				//定义构造器类型
				generator.setAttr(ITableStruConfig.ATTR_TYPE, "timestamp_version");
				
			}else if("DECIMAL".equals(columnType)||"BIGINT".equals(columnType) || "INTEGER".equals(columnType)||"INT".equals(columnType)){
				
				//定义构造器类型
				generator.setAttr(ITableStruConfig.ATTR_TYPE, "numeric_version");
				
				//定义参数
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
		
		//2.生成版本号标识
		ITableStruConfigEntity versionColumn = new TableStruConfigEntity(null);
		versionColumn.setType(ITableStruConfig.NODE_VERCOL_NM);
		versionColumn.setAttrMap(column.getAttrMap());
		
		tableStru.setVerCol(versionColumn);
		
	}
	
	/**
	 * 处理外键
	 * @param tableStru
	 * @param column
	 */
	protected void processFKs(String tableName, ITableStruXmlConfig tableStru, List<IRow> refs){
		
		//1.生成外键标识
		List<ITableStruConfigEntity> fkColumns = new ArrayList<ITableStruConfigEntity>(refs.size());
		ITableStruConfigEntity fkColumn = null;
		for(String columnTableName = refs.size() == 0 ? null : refs.get(0).getValue("PARENT_TABLENAME"), fkName = ""; tableName.equals(columnTableName); columnTableName = refs.size() == 0 ? null : refs.get(0).getValue("PARENT_TABLENAME")){
			
			IRow fk = refs.get(0);
						
			//生成节点
			if(!fkName.equals(fk.getValue("CONSTRAINT_NAME"))){
				
				fkName = fk.getValue("CONSTRAINT_NAME");
				
				fkColumn = new TableStruConfigEntity(null);
				fkColumns.add(fkColumn);
				
				//设置节点类型
				fkColumn.setType(ITableStruConfig.NODE_CHILDREF_NM);
				
				//设置外键名称
				fkColumn.setAttr(ITableStruConfig.ATTR_NAME, fkName);
				
				//子表名称
				fkColumn.setAttr(ITableStruConfig.ATTR_CHILDTABLE, fk.getValue("CHILD_TABLENAME"));
			}
			
			//关联字段
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
	 * 获得字段类型
	 * @param column 字段信息
	 * @return 字段类型
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
	 * 获得表的所有字段
	 * @param session
	 * @param tableEntity
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected abstract List<IRow> columns(String owner, String tableNames) throws SQLException, IOException;
	
	/**
	 * 
	 * 获得表的关联性
	 * @param session
	 * @param tableEntity
	 * @return
	 * @throws IOException 
	 * @throws SQLException 
	 */
	protected abstract List<IRow> references(String owner, String tableNames) throws SQLException, IOException;
}
