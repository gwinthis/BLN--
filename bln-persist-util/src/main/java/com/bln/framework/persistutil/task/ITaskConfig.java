package com.bln.framework.persistutil.task;

import java.util.List;

import com.bln.framework.filestru.IFileStru;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;

public interface ITaskConfig extends IFileStru{
	
	/**
	 * task节点名称
	 */
	public static final String NODE_TASK = "task";

	
	/**
	 * variable节点名称
	 */
	public static final String NODE_VIRIABLE = "variable";
	
	/**
	 * dbconnection节点名称
	 */
	public static final String NODE_DBCONNECTION = "dbconnection";
	
	/**
	 * dbms节点名称
	 */
	public static final String NODE_DBMS = "dbms";

	/**
	 * schema节点名称
	 */
	public static final String NODE_SCHEMA = "schema";
	
	/**
	 * table节点名称
	 */
	public static final String NODE_TABLE = "table";
	
	/**
	 * tablestruurl节点名称
	 */
	public static final String NODE_TABLESTRU_URL = "tablestru_url";

	/**
	 * versioncolumnname节点名称
	 */
	public static final String NODE_VERSION_COLUMN_NAME = "version_column_name";

	/**
	 * auto_create_seqgenerator节点名称
	 */
	public static final String NODE_AUTO_CREATE_SEQGENERATOR = "auto_create_seqgenerator";
	
	/**
	 * auto_create_versiongenerator节点名称
	 */
	public static final String NODE_AUTO_CREATE_VERSIONGENERATOR = "auto_create_versiongenerator";
	
	/**
	 * execute节点名称
	 */
	public static final String NODE_EXECUTE = "execute";

	/**
	 * driverlass属性名称
	 */
	public static final String ATTR_DRIVERCLASS = "driverlass";
	
	/**
	 * name属性名称
	 */
	public static final String ATTR_NAME = "name";
	
	/**
	 * value属性名称
	 */
	public static final String ATTR_VALUE = "value";
	
	/**
	 * connectionurl属性名称
	 */
	public static final String ATTR_CONNECTIONURL = "connection_url";
	
	/**
	 * user属性名称
	 */
	public static final String ATTR_USER = "user";

	/**
	 * password属性名称
	 */
	public static final String ATTR_PASSWORD = "password";


	
	/**
	 * 获取变量
	 * @return
	 */
	public List<ITaskConfigEntity> getViriables();

	/**
	 * 获取数据库连接信息
	 * @return
	 */
	public ITaskConfigEntity getDbConnection();

	/**
	 * 获取表名信息
	 * @return
	 */
	public ITaskConfigEntity getTable();

	/**
	 * 获取执行命令
	 * @return
	 */
	public List<ITaskConfigEntity> getExecutes();

	/**
	 * 获取版本号名称
	 * @return
	 */
	public ITaskConfigEntity getVersionColumnName();

	/**
	 * 获取配置文件存储位置
	 * @return
	 */
	public ITaskConfigEntity getTableStruUrl();

	/**
	 * 获取数据库类型
	 * @return
	 */
	public ITaskConfigEntity getDBMS();

	/**
	 * 获取schema信息
	 * @return
	 */
	public ITaskConfigEntity getSchema();

	public abstract ITaskConfigEntity getIsAutoCreateVersionGeneratorConfig();

	public abstract ITaskConfigEntity getIsAutoCreateSeqGeneratorConfig();

	public abstract boolean isAutoCreateVersionGenerator();

	public abstract boolean isAutoCreateSeqGenerator();
	
}
