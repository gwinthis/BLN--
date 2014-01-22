/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.builder;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.base.BaseObj;
import com.bln.framework.builder.exception.BuildException;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.component.column.generator.Generator;
import com.bln.framework.persist.tablestru.component.column.generator.IGenerator;
import com.bln.framework.persist.tablestru.component.fk.IForeignKey;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;
import com.bln.framework.persist.tablestru.config.entity.ITableStruConfigEntity;
import com.bln.framework.persist.tablestru.param.IParam;
import com.bln.framework.persist.tablestru.param.Param;
import com.bln.framework.util.pair.IPair;
import com.bln.framework.util.pair.Pair;
import com.bln.framework.persist.jdbc.EJdbcType;

/**
 * �������������
 */
public abstract class ComponentBuilderBase<T> extends BaseObj implements ITableComponentBuilder<T>{

	/**
	 * ��ṹ�������
	 */
	protected String componentName = null;

	/**
	 * @return the componentName
	 */
	public String getComponentName() {
		return componentName;
	}

	/**
	 * @param componentName the componentName to set
	 */
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	
	/**
	 * ͨ��������Ϣ�����ֶζ���
	 * @param config ������Ϣ
	 * @param column �ֶ�
	 */
	protected void parseColumn(ITableStruConfigEntity config, IColumn column){

		//1.��������
		String name = config.getAttr(ITableStruConfig.ATTR_NAME);
		column.setColName(name);
		
		//2.��������
		try {
			
			String colTypeAsStr = config.getAttr(ITableStruConfig.ATTR_TYPE).toUpperCase();
			int colType = EJdbcType.valueOf(colTypeAsStr).typeValue;
			
			column.setColType(colType);
			
		} catch (Throwable e) {
			BuildException be = new BuildException("when it set colType of column " + name + ", occur expception!");
			be.initCause(e);
			throw be;
		}
		
		//3.����identy
		String isIdentyStr = config.getAttr(ITableStruConfig.ATTR_IS_IDENTY);
		boolean isIdenty = StringUtils.isEmpty(isIdentyStr) ? false : Boolean.parseBoolean(isIdentyStr);
		
		column.setIdenty(isIdenty);
		
		//4.����Ĭ��ֵ
		String defaultVal = config.getAttr(ITableStruConfig.ATTR_DEFAULT_VALUE);
		column.setDefaultVal(defaultVal);
		
		//5.����������
		List<ITableStruConfigEntity> subConfigs = config.getSubConfigEntitys();
		if(subConfigs != null && subConfigs.size() > 0){
			
			IGenerator generator = null;
			
			for (ITableStruConfigEntity subConfig : subConfigs){
				
				if(ITableStruConfig.NODE_GENERATOR_NM.equals(subConfig.getType())){
					generator = new Generator();
					this.parseGenerator(subConfig, generator);
					break;				
				}
			}
			
			if(generator != null){
				column.setGenerator(generator);
			}
		}
	}
	
	/**
	 * ͨ��������Ϣ��������������
	 * @param config ������Ϣ
	 * @param generator ������
	 */
	protected void parseGenerator(ITableStruConfigEntity config, IGenerator generator){
		
		//1.��������
		String type = config.getAttr(ITableStruConfig.ATTR_TYPE);
		generator.setType(type);
		
		//2.�������
		List<ITableStruConfigEntity> subConfigs = config.getSubConfigEntitys();
		if(subConfigs != null && subConfigs.size() > 0){
			
			int n = subConfigs.size();
			IParam[] params = new IParam[n];
			
			for ( int i = 0; i < n; i++){
				ITableStruConfigEntity subConfig = subConfigs.get(i);
				
				params[i] = new Param();
				parseParam(subConfig, params[i]);				
			}
			
			generator.setParams(params);
		}

	}
	
	/**
	 * ͨ��������Ϣ������������
	 * @param config ������Ϣ
	 * @param param ��������
	 */
	protected void parseParam(ITableStruConfigEntity config, IParam param){
		
		//1.��������
		param.setName(config.getAttr(ITableStruConfig.ATTR_NAME));
		
		//2.����ֵ
		param.setValue(config.getAttr(ITableStruConfig.ATTR_VALUE));
		
	}
	
	/**
	 * ͨ��������Ϣ�����ֶζ���
	 * @param config ������Ϣ
	 * @param fk �������
	 */
	@SuppressWarnings("unchecked")
	protected void parseFk(ITableStruConfigEntity config, IForeignKey fk){

		//1.��������
		String name = config.getAttr(ITableStruConfig.ATTR_NAME);
		fk.setName(name);
		
		//2.���������
		fk.setRefTableName(config.getAttr(ITableStruConfig.ATTR_CHILDTABLE));
		
		//3.���������
		List<ITableStruConfigEntity> subConfigs = config.getSubConfigEntitys();
		if(subConfigs != null && subConfigs.size() > 0){
			
			int n = subConfigs.size();
			IPair<String, String>[] refColNames = new IPair[n];
			for (int i  = 0; i < n; i++){
				
				//��ȡ������Ϣ
				ITableStruConfigEntity subConfig = subConfigs.get(i);
				
				//��ʼ��
				refColNames[i] = new Pair<String, String>();
				
				//������������ֶ�
				refColNames[i].setLeft(subConfig.getAttr(ITableStruConfig.ATTR_PCOLNAME));
				
				//���ù�����Ĺ����ֶ�
				refColNames[i].setRight(subConfig.getAttr(ITableStruConfig.ATTR_CHILDCOLNAME));
			}
			
			//���ù�����
			fk.setRefColNames(refColNames);
		}
	}
}
