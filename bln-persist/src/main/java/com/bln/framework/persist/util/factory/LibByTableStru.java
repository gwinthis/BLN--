/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.util.factory;

import java.util.HashMap;
import java.util.Map;

import com.bln.framework.builder.IBuilder1Step;
import com.bln.framework.factory.simple.SimpleFactory;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.lib.ITableStruLib;

/**
 * SqlTemplate的库对象
 */
public class LibByTableStru<T> extends SimpleFactory<T> implements ILibByTableStru<T> {

	/**
	 * 通过表结构生成实例的构造器
	 */
	IBuilder1Step<T, ITableStru> instanceBuilder = null;
	
	/**
	 * 表结构库对象
	 */
	ITableStruLib tableStruLib = null;

	/**
	 * @return the instanceBuilder
	 */
	public IBuilder1Step<T, ITableStru> getInstanceBuilder() {
		return instanceBuilder;
	}

	/**
	 * @param instanceBuilder the instanceBuilder to set
	 */
	public void setInstanceBuilder(IBuilder1Step<T, ITableStru> instanceBuilder) {
		this.instanceBuilder = instanceBuilder;
		readyGo();
	}

	/**
	 * @return the tableStruLib
	 */
	public ITableStruLib getTableStruLib() {
		return tableStruLib;
	}
	
	/**
	 * @param tableStruLib the tableStruLib to set
	 */
	public void setTableStruLib(ITableStruLib tableStruLib) {
		this.tableStruLib = tableStruLib;
		readyGo();
	}
	
	/**
	 * 如果属性已经准备好那么执行初始化方法
	 */
	protected void readyGo(){
		if(tableStruLib != null && instanceBuilder != null){
			this.initAllInstance();
		}
	}
	
	/**
	 * 初始化所有实例
	 */
	protected void initAllInstance(){
		
		//1.如果表结构库为空，直接返回
		if(tableStruLib == null || tableStruLib.getAllInstance() == null || tableStruLib.getAllInstance().isEmpty()){
			return;
		}
		
		//2.根据表结构库初始化sqlt的实例库
		this.instanceMap = new HashMap<String, T>();
		Map<String, ITableStru> tableStruMap = tableStruLib.getAllInstance();
		for (Map.Entry<String, ITableStru> entry : tableStruMap.entrySet()){
			
			//生成实例
			T instance = instanceBuilder.build(entry.getValue());
			
			//添加到实例库中
			instanceMap.put(entry.getKey(), instance);
		}
	}
}
