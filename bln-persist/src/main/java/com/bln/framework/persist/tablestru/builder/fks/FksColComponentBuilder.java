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
 * ��ṹ������ֶι�����
 */

public class FksColComponentBuilder extends ComponentBuilderBase<IForeignKey[]> implements ITableComponentBuilder<IForeignKey[]>{
	
	/**
	 * ���캯��
	 */
	public FksColComponentBuilder(){
		super();
		this.componentName = ITableStru.COMPONENT_FKS_NAME;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.builder.IBuilder1Step#build(java.lang.Object)
	 */
	public IForeignKey[] build(ITableStruConfig tableStruConfig) {
		
		//1.У�����
		Assert.paramIsNotNull(tableStruConfig, "tableStruConfig");
		
		//2.���������ֶ�
		
		//���û���ֶ�ֱ�ӷ���
		List<ITableStruConfigEntity> fkConfigs = tableStruConfig.getFks();
		if(fkConfigs == null || fkConfigs.size() <= 0){
			return null;
		}
		
		//2.1��ʼ�ֶ�
		int n = fkConfigs.size();
		IForeignKey[] fks = new IForeignKey[n];
		
		//2.2����ÿ���ֶ�
		for ( int i = 0; i < n; i++ ){
			
			//��ȡ������Ϣ
			ITableStruConfigEntity config = fkConfigs.get(i);
			
			//�����������
			fks[i] = new ForeignKey();
			parseFk(config, fks[i]);
		}

		//3.�����ֶ����
		return fks;
	}
}
