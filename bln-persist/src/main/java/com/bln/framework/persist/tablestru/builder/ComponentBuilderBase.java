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
 * 组件构造器基类
 */
public abstract class ComponentBuilderBase<T> extends BaseObj implements ITableComponentBuilder<T>{

	/**
	 * 表结构组件名称
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
	 * 通过配置信息解析字段对象
	 * @param config 配置信息
	 * @param column 字段
	 */
	protected void parseColumn(ITableStruConfigEntity config, IColumn column){

		//1.定义名称
		String name = config.getAttr(ITableStruConfig.ATTR_NAME);
		column.setColName(name);
		
		//2.定义类型
		try {
			
			String colTypeAsStr = config.getAttr(ITableStruConfig.ATTR_TYPE).toUpperCase();
			int colType = EJdbcType.valueOf(colTypeAsStr).typeValue;
			
			column.setColType(colType);
			
		} catch (Throwable e) {
			BuildException be = new BuildException("when it set colType of column " + name + ", occur expception!");
			be.initCause(e);
			throw be;
		}
		
		//3.定义identy
		String isIdentyStr = config.getAttr(ITableStruConfig.ATTR_IS_IDENTY);
		boolean isIdenty = StringUtils.isEmpty(isIdentyStr) ? false : Boolean.parseBoolean(isIdentyStr);
		
		column.setIdenty(isIdenty);
		
		//4.定义默认值
		String defaultVal = config.getAttr(ITableStruConfig.ATTR_DEFAULT_VALUE);
		column.setDefaultVal(defaultVal);
		
		//5.解析生成器
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
	 * 通过配置信息解析生成器对象
	 * @param config 配置信息
	 * @param generator 生成器
	 */
	protected void parseGenerator(ITableStruConfigEntity config, IGenerator generator){
		
		//1.定义类型
		String type = config.getAttr(ITableStruConfig.ATTR_TYPE);
		generator.setType(type);
		
		//2.定义参数
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
	 * 通过配置信息解析参数对象
	 * @param config 配置信息
	 * @param param 参数对象
	 */
	protected void parseParam(ITableStruConfigEntity config, IParam param){
		
		//1.设置名称
		param.setName(config.getAttr(ITableStruConfig.ATTR_NAME));
		
		//2.设置值
		param.setValue(config.getAttr(ITableStruConfig.ATTR_VALUE));
		
	}
	
	/**
	 * 通过配置信息解析字段对象
	 * @param config 配置信息
	 * @param fk 外键对象
	 */
	@SuppressWarnings("unchecked")
	protected void parseFk(ITableStruConfigEntity config, IForeignKey fk){

		//1.定义名称
		String name = config.getAttr(ITableStruConfig.ATTR_NAME);
		fk.setName(name);
		
		//2.定义关联表
		fk.setRefTableName(config.getAttr(ITableStruConfig.ATTR_CHILDTABLE));
		
		//3.定义关联项
		List<ITableStruConfigEntity> subConfigs = config.getSubConfigEntitys();
		if(subConfigs != null && subConfigs.size() > 0){
			
			int n = subConfigs.size();
			IPair<String, String>[] refColNames = new IPair[n];
			for (int i  = 0; i < n; i++){
				
				//获取配置信息
				ITableStruConfigEntity subConfig = subConfigs.get(i);
				
				//初始化
				refColNames[i] = new Pair<String, String>();
				
				//设置主表关联字段
				refColNames[i].setLeft(subConfig.getAttr(ITableStruConfig.ATTR_PCOLNAME));
				
				//设置关联表的关联字段
				refColNames[i].setRight(subConfig.getAttr(ITableStruConfig.ATTR_CHILDCOLNAME));
			}
			
			//设置关联项
			fk.setRefColNames(refColNames);
		}
	}
}
