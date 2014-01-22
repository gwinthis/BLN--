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
 * SqlTemplate的库对象
 */
public class EntityLib extends SimpleFactory<EntityStatelessModule> implements IEntityLib{

	/**
	 * 表结构库
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
	 * 设置所有实例属性
	 */
	protected void setInstanceProperty(){
		
		//如果设置完毕，或者库的属性没有设置完毕直接返回
		if(tableStruLib == null || tableStruLib.getAllInstance() == null || tableStruLib.getAllInstance().isEmpty()|| this.instanceMap == null || instanceMap.isEmpty()){
			
			return;
		}
		
		//设置所有实体的属性
		for ( Map.Entry<String, EntityStatelessModule> instanceEntry: instanceMap.entrySet()){
			
			//获取表名
			String tableName = instanceEntry.getKey();
			
			//设置实体属性
			EntityStatelessModule entity = instanceEntry.getValue();
			
			//设置实体库
			entity.setEntityLib(this);
			
			//设置表结构
			ITableStru tableStru = tableStruLib.getNotNullInstance(tableName);
			entity.setTableStru(tableStru);
		}
	}
}
