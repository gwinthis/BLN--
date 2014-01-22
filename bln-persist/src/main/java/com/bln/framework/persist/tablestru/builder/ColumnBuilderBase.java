/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.builder;

import com.bln.framework.persist.tablestru.component.column.Column;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;
import com.bln.framework.persist.tablestru.config.entity.ITableStruConfigEntity;
import com.bln.framework.util.asserter.Assert;

/**
 * 表结构对象的字段构造器
 */

public abstract class ColumnBuilderBase extends ComponentBuilderBase<IColumn> implements ITableComponentBuilder<IColumn>{

	/**
	 * 要解析的节点名称
	 */
	protected String configName = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.builder.IBuilder1Step#build(java.lang.Object)
	 */
	public IColumn build(ITableStruConfig tableStruConfig) {
		
		//1.校验参数
		Assert.paramIsNotNull(tableStruConfig, "tableStruConfig");
		
		//2.生成字段
		
		//如果没有字段直接返回
		ITableStruConfigEntity colConfig = tableStruConfig.getConfigOfTable(configName);
		if(colConfig == null){
			return null;
		}
		
		//2.1初始字段
		IColumn column = new Column();
		
		//2.2解析字段对象
		parseColumn(colConfig, column);

		//3.返回字段组件
		return column;
	}	
}
