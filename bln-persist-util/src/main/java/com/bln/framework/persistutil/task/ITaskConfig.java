package com.bln.framework.persistutil.task;

import java.util.List;

import com.bln.framework.filestru.IFileStru;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;

public interface ITaskConfig extends IFileStru{
	
	/**
	 * task�ڵ�����
	 */
	public static final String NODE_TASK = "task";

	
	/**
	 * variable�ڵ�����
	 */
	public static final String NODE_VIRIABLE = "variable";
	
	/**
	 * dbconnection�ڵ�����
	 */
	public static final String NODE_DBCONNECTION = "dbconnection";
	
	/**
	 * dbms�ڵ�����
	 */
	public static final String NODE_DBMS = "dbms";

	/**
	 * schema�ڵ�����
	 */
	public static final String NODE_SCHEMA = "schema";
	
	/**
	 * table�ڵ�����
	 */
	public static final String NODE_TABLE = "table";
	
	/**
	 * tablestruurl�ڵ�����
	 */
	public static final String NODE_TABLESTRU_URL = "tablestru_url";

	/**
	 * versioncolumnname�ڵ�����
	 */
	public static final String NODE_VERSION_COLUMN_NAME = "version_column_name";

	/**
	 * auto_create_seqgenerator�ڵ�����
	 */
	public static final String NODE_AUTO_CREATE_SEQGENERATOR = "auto_create_seqgenerator";
	
	/**
	 * auto_create_versiongenerator�ڵ�����
	 */
	public static final String NODE_AUTO_CREATE_VERSIONGENERATOR = "auto_create_versiongenerator";
	
	/**
	 * execute�ڵ�����
	 */
	public static final String NODE_EXECUTE = "execute";

	/**
	 * driverlass��������
	 */
	public static final String ATTR_DRIVERCLASS = "driverlass";
	
	/**
	 * name��������
	 */
	public static final String ATTR_NAME = "name";
	
	/**
	 * value��������
	 */
	public static final String ATTR_VALUE = "value";
	
	/**
	 * connectionurl��������
	 */
	public static final String ATTR_CONNECTIONURL = "connection_url";
	
	/**
	 * user��������
	 */
	public static final String ATTR_USER = "user";

	/**
	 * password��������
	 */
	public static final String ATTR_PASSWORD = "password";


	
	/**
	 * ��ȡ����
	 * @return
	 */
	public List<ITaskConfigEntity> getViriables();

	/**
	 * ��ȡ���ݿ�������Ϣ
	 * @return
	 */
	public ITaskConfigEntity getDbConnection();

	/**
	 * ��ȡ������Ϣ
	 * @return
	 */
	public ITaskConfigEntity getTable();

	/**
	 * ��ȡִ������
	 * @return
	 */
	public List<ITaskConfigEntity> getExecutes();

	/**
	 * ��ȡ�汾������
	 * @return
	 */
	public ITaskConfigEntity getVersionColumnName();

	/**
	 * ��ȡ�����ļ��洢λ��
	 * @return
	 */
	public ITaskConfigEntity getTableStruUrl();

	/**
	 * ��ȡ���ݿ�����
	 * @return
	 */
	public ITaskConfigEntity getDBMS();

	/**
	 * ��ȡschema��Ϣ
	 * @return
	 */
	public ITaskConfigEntity getSchema();

	public abstract ITaskConfigEntity getIsAutoCreateVersionGeneratorConfig();

	public abstract ITaskConfigEntity getIsAutoCreateSeqGeneratorConfig();

	public abstract boolean isAutoCreateVersionGenerator();

	public abstract boolean isAutoCreateSeqGenerator();
	
}
