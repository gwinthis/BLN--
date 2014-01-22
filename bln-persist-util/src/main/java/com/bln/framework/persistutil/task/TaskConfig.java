package com.bln.framework.persistutil.task;

import java.util.List;

import org.dom4j.Element;

import com.bln.framework.filestru.xml.config.XMLFile4ConfigBase;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;
import com.bln.framework.persistutil.task.entity.TaskConfigEntity;

/**
 * 表结构配置信息
	<?xml version="1.0" encoding="utf-8"?>

	<task>
		
		<variable name = "cmsdaodir" value = "D:\e\九龙堆场\ServerSrc\DMSDao" />
		<variable name = "cmsappdir" value = "D:\e\九龙堆场\ServerSrc\DMSApp" />
		<variable name = "cmsentitydir" value = "D:\e\九龙堆场\ServerSrc\DmsEntityModule" />
		
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
	 * task节点
	 * 该节点为根节点
	 */
	public Element task = null;
	
	/**
	 * 构造函数
	 */
	public TaskConfig(){
		super();
		this.encoding = "UTF-8";
	}
		
	/**
	 * 获取变量
	 * @return
	 */
	public List<ITaskConfigEntity> getViriables(){
		return this.getEntitysOfElement(task, NODE_VIRIABLE);
	}

	/**
	 * 获取数据库类型
	 * @return
	 */
	public ITaskConfigEntity getDBMS(){
		return this.getEntityOfElement(task, NODE_DBMS);
	}
	
	/**
	 * 获取数据库连接信息
	 * @return
	 */
	public ITaskConfigEntity getDbConnection(){
		return this.getEntityOfElement(task, NODE_DBCONNECTION);
	}

	/**
	 * 获取schema信息
	 * @return
	 */
	public ITaskConfigEntity getSchema(){
		return this.getEntityOfElement(task, NODE_SCHEMA);
	}
	
	/**
	 * 获取表名信息
	 * @return
	 */
	public ITaskConfigEntity getTable(){
		return this.getEntityOfElement(task, NODE_TABLE);
	}
	
	/**
	 * 获取版本号字段信息
	 * @return
	 */
	public ITaskConfigEntity getVersionColumnName(){
		return this.getEntityOfElement(task, NODE_VERSION_COLUMN_NAME);
	}
	
	/**
	 * 获取自动生成序列号生成器信息
	 * @return
	 */
	public ITaskConfigEntity getIsAutoCreateSeqGeneratorConfig(){
		return this.getEntityOfElement(task, NODE_AUTO_CREATE_SEQGENERATOR);
	}
	
	/**
	 * 获自动生成版本号生成器信息
	 * @return
	 */
	public ITaskConfigEntity getIsAutoCreateVersionGeneratorConfig(){
		return this.getEntityOfElement(task, NODE_AUTO_CREATE_VERSIONGENERATOR);
	}

	/**
	 * 是否自动生成序列号生成器
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
	 * 是否自动生成版本号生成器
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
	 * 获取执行命令
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
