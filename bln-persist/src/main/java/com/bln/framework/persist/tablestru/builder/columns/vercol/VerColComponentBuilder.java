/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.builder.columns.vercol;

import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.builder.ColumnBuilderBase;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;

/**
 * ��ṹ����İ汾�Ź�����
 */
public class VerColComponentBuilder extends ColumnBuilderBase{

	/**
	 * ���캯��
	 */
	public VerColComponentBuilder(){
		super();
		this.componentName = ITableStru.COMPONENT_VERCOL_NAME;
		this.configName = ITableStruConfig.NODE_VERCOL_NM;
	}
	
}
