package com.bln.framework.persistutil.task;

import java.util.List;

import org.dom4j.Element;

import com.bln.framework.filestru.xml.config.XMLFile4ConfigBase;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;
import com.bln.framework.persistutil.task.entity.TaskConfigEntity;

/**
 * ��ṹ������Ϣ
	<?xml version="1.0" encoding="utf-8"?>

	<task>
		
		<variable name = "cmsdaodir" value = "D:\e\�����ѳ�\ServerSrc\DMSDao" />
		<variable name = "cmsappdir" value = "D:\e\�����ѳ�\ServerSrc\DMSApp" />
		<variable name = "cmsentitydir" value = "D:\e\�����ѳ�\ServerSrc\DmsEntityModule" />
		
		<dbms/>
		<dbconnection/>
		<tablestruurl/>
		<versioncolumnname/>
		<auto_create_seqgenerator value = "true"/>
		<auto_create_versiongenerator value = "true"/>
		<table value = "T_YDOP_YDCNTR"/>
		<execute name = "buildTableStru"/>
		
	</task>
*
 */
public class TaskConfig extends XMLFile4ConfigBase<ITaskConfigEntity> implements ITaskConfig{

	/**
	 * task�ڵ�
	 * �ýڵ�Ϊ���ڵ�
	 */
	public Element task = null;
	
	/**
	 * ���캯��
	 */
	public TaskConfig(){
		super();
		this.encoding = "UTF-8";
	}
		
	/**
	 * ��ȡ����
	 * @return
	 */
	public List<ITaskConfigEntity> getViriables(){
		return this.getEntitysOfElement(task, NODE_VIRIABLE);
	}

	/**
	 * ��ȡ���ݿ�����
	 * @return
	 */
	public ITaskConfigEntity getDBMS(){
		return this.getEntityOfElement(task, NODE_DBMS);
	}
	
	/**
	 * ��ȡ���ݿ�������Ϣ
	 * @return
	 */
	public ITaskConfigEntity getDbConnection(){
		return this.getEntityOfElement(task, NODE_DBCONNECTION);
	}

	/**
	 * ��ȡschema��Ϣ
	 * @return
	 */
	public ITaskConfigEntity getSchema(){
		return this.getEntityOfElement(task, NODE_SCHEMA);
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @return
	 */
	public ITaskConfigEntity getTable(){
		return this.getEntityOfElement(task, NODE_TABLE);
	}
	
	/**
	 * ��ȡ�汾���ֶ���Ϣ
	 * @return
	 */
	public ITaskConfigEntity getVersionColumnName(){
		return this.getEntityOfElement(task, NODE_VERSION_COLUMN_NAME);
	}
	
	/**
	 * ��ȡ�Զ��������к���������Ϣ
	 * @return
	 */
	public ITaskConfigEntity getIsAutoCreateSeqGeneratorConfig(){
		return this.getEntityOfElement(task, NODE_AUTO_CREATE_SEQGENERATOR);
	}
	
	/**
	 * ���Զ����ɰ汾����������Ϣ
	 * @return
	 */
	public ITaskConfigEntity getIsAutoCreateVersionGeneratorConfig(){
		return this.getEntityOfElement(task, NODE_AUTO_CREATE_VERSIONGENERATOR);
	}

	/**
	 * �Ƿ��Զ��������к�������
	 * @return
	 */
	public boolean isAutoCreateSeqGenerator(){
		
		boolean isAuto = true;
		
		ITaskConfigEntity config = this.getIsAutoCreateSeqGeneratorConfig();
		if(config != null && "false".equals(config.getAttr(ITaskConfig.ATTR_VALUE))){
			isAuto = false;
		}
		
		return isAuto;
	}
	
	/**
	 * �Ƿ��Զ����ɰ汾��������
	 * @return
	 */
	public boolean isAutoCreateVersionGenerator(){
		
		boolean isAuto = true;
		
		ITaskConfigEntity config = this.getIsAutoCreateVersionGeneratorConfig();
		if(config != null && "false".equals(config.getAttr(ITaskConfig.ATTR_VALUE))){
			isAuto = false;
		}
		
		return isAuto;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.task.ITaskConfig#getTableStruUrl()
	 */
	public ITaskConfigEntity getTableStruUrl(){
		return this.getEntityOfElement(task, NODE_TABLESTRU_URL);
	}
	
	/**
	 * ��ȡִ������
	 * @return
	 */
	public List<ITaskConfigEntity> getExecutes(){
		return this.getEntitysOfElement(task, NODE_EXECUTE);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.xml.config.XMLFile4ConfigBase#newConfig(java.lang.Object)
	 */
	@Override
	protected ITaskConfigEntity newConfigEntity(Object oriElement) {
		return new TaskConfigEntity(oriElement);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.xml.config.XMLFile4ConfigBase#procSubConfig(com.bln.framework.config.entity.ConfigEntity, com.bln.framework.config.entity.ConfigEntity)
	 */
	@Override
	protected void procSubConfig(ITaskConfigEntity config, ITaskConfigEntity subConfig) {
		config.addSubConfigEntity(subConfig);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.FileStruBase#initStru()
	 */
	@Override
	protected void initStru() {
		this.task = this.document.getRootElement();		
	}

}
