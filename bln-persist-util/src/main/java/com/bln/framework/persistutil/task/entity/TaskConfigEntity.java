/**
 * @author gengw
 * Created on Apr 14, 2012
 */
package com.bln.framework.persistutil.task.entity;

import com.bln.framework.config.entity.ConfigEntity;

/**
 * �����ĵ�������ʵ��
 */
public class TaskConfigEntity extends ConfigEntity<ITaskConfigEntity> implements ITaskConfigEntity{

	/**
	 * @param oriParam
	 */
	public TaskConfigEntity(Object oriParam) {
		super(oriParam);
	}

}
