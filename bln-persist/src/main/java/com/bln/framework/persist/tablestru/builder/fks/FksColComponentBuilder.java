/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.builder.fks;

import java.util.List;

import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.builder.ComponentBuilderBase;
import com.bln.framework.persist.tablestru.builder.ITableComponentBuilder;
import com.bln.framework.persist.tablestru.component.fk.ForeignKey;
import com.bln.framework.persist.tablestru.component.fk.IForeignKey;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;
import com.bln.framework.persist.tablestru.config.entity.ITableStruConfigEntity;
import com.bln.framework.util.asserter.Assert;


/**
 * 表结构对象的字段构造器
 */

public class FksColComponentBuilder extends ComponentBuilderBase<IForeignKey[]> implements ITableComponentBuilder<IForeignKey[]>{
	
	/**
	 * 构造函数
	 */
	public FksColComponentBuilder(){
		super();
		this.componentName = ITableStru.COMPONENT_FKS_NAME;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.builder.IBuilder1Step#build(java.lang.Object)
	 */
	public IForeignKey[] build(ITableStruConfig tableStruConfig) {
		
		//1.校验参数
		Assert.paramIsNotNull(tableStruConfig, "tableStruConfig");
		
		//2.生成所有字段
		
		//如果没有字段直接返回
		List<ITableStruConfigEntity> fkConfigs = tableStruConfig.getFks();
		if(fkConfigs == null || fkConfigs.size() <= 0){
			return null;
		}
		
		//2.1初始字段
		int n = fkConfigs.size();
		IForeignKey[] fks = new IForeignKey[n];
		
		//2.2构造每个字段
		for ( int i = 0; i < n; i++ ){
			
			//获取配置信息
			ITableStruConfigEntity config = fkConfigs.get(i);
			
			//解析外键对象
			fks[i] = new ForeignKey();
			parseFk(config, fks[i]);
		}

		//3.返回字段组件
		return fks;
	}
}
