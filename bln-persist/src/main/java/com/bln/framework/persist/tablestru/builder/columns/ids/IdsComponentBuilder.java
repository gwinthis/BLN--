/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.builder.columns.ids;

import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.builder.ColumnsBuilderBase;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;

/**
 * 表结构对象的主键构造器
 */
public class IdsComponentBuilder extends ColumnsBuilderBase{

	/**
	 * 构造函数
	 */
	public IdsComponentBuilder(){
		super();
		this.componentName = ITableStru.COMPONENT_IDS_NAME;
		this.configName = ITableStruConfig.NODE_ID_NM;
	}
	
}
