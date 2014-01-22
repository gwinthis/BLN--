/**
 * @author gengw
 * Created on May 28, 2012
 */
package com.bln.framework.persistutil.executebuilder;

import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.ISession;
import com.bln.framework.persistutil.task.ITaskConfig;
import com.bln.framework.persistutil.task.entity.ITaskConfigEntity;

/**
 * ִ�й���������
 */
public abstract class ExecuteBuilderBase implements IExecuteBuilder {
	
	/**
	 * ���������ļ�
	 */
	protected ITaskConfig taskConfig = null;

	/**
	 * ִ��ʵ��
	 */
	protected ITaskConfigEntity executeEnity = null;
	
	/**
	 * ���ݿ�Session
	 */
	protected ISession session = null;
	
	/**
	 * Ҫ����ı�
	 */
	protected List<IRow> tables = null;
	
	/**
	 * ִ������
	 */
	protected String executeDesc = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.executebuilder.IExecuteBuilder#getTaskConfig()
	 */
	public ITaskConfig getTaskConfig() {
		return taskConfig;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.executebuilder.IExecuteBuilder#setTaskConfig(com.bln.framework.persistutil.task.ITaskConfig)
	 */
	public void setTaskConfig(ITaskConfig taskConfig) {
		this.taskConfig = taskConfig;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.executebuilder.IExecuteBuilder#getSession()
	 */
	public ISession getSession() {
		return session;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.executebuilder.IExecuteBuilder#setSession(com.bln.framework.persist.session.ISession)
	 */
	public void setSession(ISession session) {
		this.session = session;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.executebuilder.IExecuteBuilder#getTables()
	 */
	public List<IRow> getTables() {
		return tables;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persistutil.executebuilder.IExecuteBuilder#setTables(java.util.List)
	 */
	public void setTables(List<IRow> tables) {
		this.tables = tables;
	}

	/**
	 * @return the executeDesc
	 */
	public String getExecuteDesc() {
		return executeDesc;
	}

	/**
	 * @param executeDesc the executeDesc to set
	 */
	public void setExecuteDesc(String executeDesc) {
		this.executeDesc = executeDesc;
		
	}

	/**
	 * @return the executeEnity
	 */
	public ITaskConfigEntity getExecuteEnity() {
		return executeEnity;
	}

	/**
	 * @param executeEnity the executeEnity to set
	 */
	public void setExecuteEnity(ITaskConfigEntity executeEnity) {
		this.executeEnity = executeEnity;
	}
	
	/**
	 * �������Ʋ��Ҳ���
	 * @param params
	 * @param name
	 * @return
	 */
	protected ITaskConfigEntity findParam(List<ITaskConfigEntity> params, String name){
		
		int i, n;
		ITaskConfigEntity param = null;
		for (i = 0, n = params.size(); i < n; i++){
			param = params.get(i);
			if(name.equals(param.getAttr(ITaskConfig.ATTR_NAME))){
				break;
			}
		}

		if(i == n){
			param = null;
		}
		
		return param;
	}
}
