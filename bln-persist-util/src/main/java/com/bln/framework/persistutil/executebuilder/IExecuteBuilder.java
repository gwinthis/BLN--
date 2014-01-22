package com.bln.framework.persistutil.executebuilder;

import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.ISession;
import com.bln.framework.persistutil.task.ITaskConfig;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;

public interface IExecuteBuilder {
	
	/**
	 * 建立表结构文件
	 */
	public final static String EXECUTE_BUILDSTRU = "BUILDTABLESTRU";

	/**
	 * 建立表结构库
	 */
	public final static String EXECUTE_BUILDSTRULIB = "BUILDTABLESTRULIB";

	/**
	 * 解析任务
	 * @throws Throwable
	 */
	public abstract void build() throws Throwable;

	/**
	 * @return the taskConfig
	 */
	public abstract ITaskConfig getTaskConfig();

	/**
	 * @param taskConfig the taskConfig to set
	 */
	public abstract void setTaskConfig(ITaskConfig taskConfig);

	/**
	 * @return the session
	 */
	public abstract ISession getSession();

	/**
	 * @param session the session to set
	 */
	public abstract void setSession(ISession session);

	/**
	 * @return the tables
	 */
	public abstract List<IRow> getTables();

	/**
	 * @param tables the tables to set
	 */
	public abstract void setTables(List<IRow> tables);

	/**
	 * @return the executeDesc
	 */
	public String getExecuteDesc();

	/**
	 * @return the executeEnity
	 */
	public ITaskConfigEntity getExecuteEnity();

	/**
	 * @param executeEnity the executeEnity to set
	 */
	public void setExecuteEnity(ITaskConfigEntity executeEnity);

}