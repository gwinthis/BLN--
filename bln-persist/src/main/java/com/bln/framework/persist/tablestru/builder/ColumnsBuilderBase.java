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
 * ��ṹ������ֶι�����
 */

public abstract class ColumnsBuilderBase extends ComponentBuilderBase<IColumn[]> implements ITableComponentBuilder<IColumn[]>{

	/**
	 * Ҫ�����Ľڵ�����
	 */
	protected String configName = "";
	
	/* (non-Javadoc)
	 * @see com.bln.framework.builder.IBuilder1Step#build(java.lang.Object)
	 */
	public IColumn[] build(ITableStruConfig tableStruConfig) {
		
		//1.У�����
		Assert.paramIsNotNull(tableStruConfig, "tableStruConfig");
		
		//2.���������ֶ�
		
		//���û���ֶ�ֱ�ӷ���
		List<ITableStruConfigEntity> colConfigs = tableStruConfig.getConfigsOfTable(configName);
		if(colConfigs == null || colConfigs.size() <= 0){
			return null;
		}
		
		//2.1��ʼ�ֶ�
		int n = colConfigs.size();
		IColumn[] columms = new IColumn[n];
		
		//2.2����ÿ���ֶ�
		for ( int i = 0; i < n; i++ ){
			
			//��ȡ������Ϣ
			ITableStruConfigEntity config = colConfigs.get(i);
			
			//�����ֶζ���
			columms[i] = new Column();
			parseColumn(config, columms[i]);
		}

		//3.�����ֶ����
		return columms;
	}	
}
