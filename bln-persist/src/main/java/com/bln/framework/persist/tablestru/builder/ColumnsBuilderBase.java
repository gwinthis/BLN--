/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.builder;

import java.util.List;

import com.bln.framework.persist.tablestru.component.column.Column;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;
import com.bln.framework.persist.tablestru.config.entity.ITableStruConfigEntity;
import com.bln.framework.util.asserter.Assert;

/**
 * 表结构对象的字段构造器
 */

public abstract class ColumnsBuilderBase extends ComponentBuilderBase<IColumn[]> implements ITableComponentBuilder<IColumn[]>{

	/**
	 * 要解析的节点名称
	 */
	protected String configName = "";
	
	/* (non-Javadoc)
	 * @see com.bln.framework.builder.IBuilder1Step#build(java.lang.Object)
	 */
	public IColumn[] build(ITableStruConfig tableStruConfig) {
		
		//1.校验参数
		Assert.paramIsNotNull(tableStruConfig, "tableStruConfig");
		
		//2.生成所有字段
		
		//如果没有字段直接返回
		List<ITableStruConfigEntity> colConfigs = tableStruConfig.getConfigsOfTable(configName);
		if(colConfigs == null || colConfigs.size() <= 0){
			return null;
		}
		
		//2.1初始字段
		int n = colConfigs.size();
		IColumn[] columms = new IColumn[n];
		
		//2.2构造每个字段
		for ( int i = 0; i < n; i++ ){
			
			//获取配置信息
			ITableStruConfigEntity config = colConfigs.get(i);
			
			//解析字段对象
			columms[i] = new Column();
			parseColumn(config, columms[i]);
		}

		//3.返回字段组件
		return columms;
	}	
}
