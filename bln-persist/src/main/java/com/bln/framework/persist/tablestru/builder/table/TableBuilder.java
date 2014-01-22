/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.builder.table;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;

import com.bln.framework.base.BaseObj;
import com.bln.framework.builder.exception.BuildException;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.TableStru;
import com.bln.framework.persist.tablestru.builder.ITableComponentBuilder;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;
import com.bln.framework.persist.tablestru.exception.TableAssembleException;
import com.bln.framework.util.asserter.Assert;

/**
 * 表结构对象构造器
 */
public class TableBuilder extends BaseObj implements ITableBuilder{
	
	/**
	 * 配置对象
	 */
	ITableStruConfig tableStruConfig = null;
	
	/**
	 * 表结构组件构造器
	 */
	ITableComponentBuilder<?>[] componentBuilders = null;

	/**
	 * @return the componentBuilders
	 */
	public ITableComponentBuilder<?>[] getComponentBuilders() {
		return componentBuilders;
	}

	/**
	 * @param componentBuilders the componentBuilders to set
	 */
	public void setComponentBuilders(ITableComponentBuilder<?>[] componentBuilders) {
		this.componentBuilders = componentBuilders;
	}
	
	/**
	 * 加载表结构配置对象
	 * @param configUrl 配置文件的url
	 * @return 表结构配置对象
	 */
	protected void loadConfig(String configUrl)throws Throwable{
		
		byte[] bytes = IOUtils.toByteArray(this.getClass().getResourceAsStream(configUrl));
		tableStruConfig.readFromData(bytes);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.builder.table.ITableStruBuilder#build(com.bln.framework.persist.tablestru.config.ITableStruConfig)
	 */
	public ITableStru build(String configUrl){

		//1.校验前提条件
		Assert.paramIsNotNull(configUrl, "configUrl");
		
		//2.加载配置对象
		try {
			this.loadConfig(configUrl);
		} catch (Throwable e1) {
			BuildException be = new BuildException(" when load configUrl " + configUrl + ", occurs error!");
			be.initCause(e1);
			throw be;
		}
		
		//3.构造表结构对象
		ITableStru tableStru = new TableStru();
		
		//3.1定义表名
		tableStru.setTableName(tableStruConfig.getTableNm());
		
		//定义模式名
		tableStru.setSchemaName(tableStruConfig.getSchemaNm());
		
		//3.2定义表结构组件
		try {
			assembleComponents(tableStruConfig, tableStru);
		} catch (Throwable e) {
			BuildException be = new BuildException();
			be.initCause(e);
			throw be;
		}
		
		//4.处理特殊字段
		parseAllSpecialColumns(tableStru);
			
		//4.返回表结构对象
		return tableStru;
		
	}
	
	/**
	 * 装配表结构组件
	 * @param tableStru 表结构对象
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void assembleComponents(ITableStruConfig tableStruConfig, ITableStru tableStru){
		
		//如果没有组件生成器，直接返回
		if(componentBuilders == null || componentBuilders.length <= 0){
			return;
		}
		
		//装配对象
		for ( ITableComponentBuilder<?> builder : componentBuilders){
			
			//生成组件
			Object tableComponent =  builder.build(tableStruConfig);
			
			//获取组件名称
			String componentName = builder.getComponentName();
			
			try {
				//装配组件
				if(tableComponent != null){
					BeanUtils.setProperty(tableStru, componentName, tableComponent);
				}
			} catch (Throwable e) {
				TableAssembleException tae = new TableAssembleException(" when assemble " + componentName + " occurs error!");
				tae.initCause(e);
				throw tae;
				
			}
		}		
	}
	
	/**
	 * 解析特殊字段
	 * @param column 特殊字段
	 * @param allColumns 所有字段
	 */
	protected void parseAllSpecialColumns(ITableStru tableStru){
		
		//获得所有字段
		IColumn[] allColumns = tableStru.getAllColumns().getColumns();
		
		//处理主键
		IColumn[] ids = tableStru.getIds();
		if(ids != null && ids.length > 0){
			for ( IColumn id : ids){
				parseSpecialColumn(id, allColumns);
			}
		}
		
		//处理版本号
		IColumn versionColumn = tableStru.getVerCol();
		parseSpecialColumn(versionColumn, allColumns);
	}
	
	/**
	 * 解析特殊字段
	 * @param column 特殊字段
	 * @param allColumns 所有字段
	 */
	protected void parseSpecialColumn(IColumn column, IColumn[] allColumns){
		
		for ( int i = 0, n = allColumns.length; i < n; i++ ){
			IColumn theColumn = allColumns[i];
			
			if( column != null && column.getColName().equals(theColumn.getColName())){
				column.setColIndx(theColumn.getColIndx());
				column.setColType(theColumn.getColType());
				column.setDefaultVal(theColumn.getDefaultVal());
				column.setGenerator(theColumn.getGenerator());
			}
		}
	}

	/**
	 * @return the tableStruConfig
	 */
	public ITableStruConfig getTableStruConfig() {
		return tableStruConfig;
	}

	/**
	 * @param tableStruConfig the tableStruConfig to set
	 */
	public void setTableStruConfig(ITableStruConfig tableStruConfig) {
		this.tableStruConfig = tableStruConfig;
	}


}
