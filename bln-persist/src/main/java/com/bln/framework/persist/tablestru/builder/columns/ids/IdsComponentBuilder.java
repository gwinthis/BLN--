/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.builder.columns.ids;

import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.builder.ColumnsBuilderBase;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;

/**
 * ��ṹ���������������
 */
public class IdsComponentBuilder extends ColumnsBuilderBase{

	/**
	 * ���캯��
	 */
	public IdsComponentBuilder(){
		super();
		this.componentName = ITableStru.COMPONENT_IDS_NAME;
		this.configName = ITableStruConfig.NODE_ID_NM;
	}
	
}
