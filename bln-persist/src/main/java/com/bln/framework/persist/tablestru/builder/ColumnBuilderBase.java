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
 * ��ṹ������ֶι�����
 */

public abstract class ColumnBuilderBase extends ComponentBuilderBase<IColumn> implements ITableComponentBuilder<IColumn>{

	/**
	 * Ҫ�����Ľڵ�����
	 */
	protected String configName = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.builder.IBuilder1Step#build(java.lang.Object)
	 */
	public IColumn build(ITableStruConfig tableStruConfig) {
		
		//1.У�����
		Assert.paramIsNotNull(tableStruConfig, "tableStruConfig");
		
		//2.�����ֶ�
		
		//���û���ֶ�ֱ�ӷ���
		ITableStruConfigEntity colConfig = tableStruConfig.getConfigOfTable(configName);
		if(colConfig == null){
			return null;
		}
		
		//2.1��ʼ�ֶ�
		IColumn column = new Column();
		
		//2.2�����ֶζ���
		parseColumn(colConfig, column);

		//3.�����ֶ����
		return column;
	}	
}
