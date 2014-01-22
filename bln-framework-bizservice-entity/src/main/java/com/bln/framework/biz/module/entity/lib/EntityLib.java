/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.biz.module.entity.lib;

import java.util.Map;

import com.bln.framework.biz.module.entity.EntityStatelessModule;

import com.bln.framework.factory.simple.SimpleFactory;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.lib.ITableStruLib;

/**
 * SqlTemplate�Ŀ����
 */
public class EntityLib extends SimpleFactory<EntityStatelessModule> implements IEntityLib{

	/**
	 * ��ṹ��
	 */
	protected ITableStruLib tableStruLib = null;

	/**
	 * @param tableStruLib the tableStruLib to set
	 */
	public void setTableStruLib(ITableStruLib tableStruLib) {
		this.tableStruLib = tableStruLib;
		setInstanceProperty();
	}

	/**
	 * @param objInstanceMap the objInstanceMap to set
	 */
	public void setInstanceMap(Map<String, EntityStatelessModule> instanceMap) {
		super.setInstanceMap(instanceMap);
		setInstanceProperty();
	}
	
	/**
	 * ��������ʵ������
	 */
	protected void setInstanceProperty(){
		
		//���������ϣ����߿������û���������ֱ�ӷ���
		if(tableStruLib == null || tableStruLib.getAllInstance() == null || tableStruLib.getAllInstance().isEmpty()|| this.instanceMap == null || instanceMap.isEmpty()){
			
			return;
		}
		
		//��������ʵ�������
		for ( Map.Entry<String, EntityStatelessModule> instanceEntry: instanceMap.entrySet()){
			
			//��ȡ����
			String tableName = instanceEntry.getKey();
			
			//����ʵ������
			EntityStatelessModule entity = instanceEntry.getValue();
			
			//����ʵ���
			entity.setEntityLib(this);
			
			//���ñ�ṹ
			ITableStru tableStru = tableStruLib.getNotNullInstance(tableName);
			entity.setTableStru(tableStru);
		}
	}
}
