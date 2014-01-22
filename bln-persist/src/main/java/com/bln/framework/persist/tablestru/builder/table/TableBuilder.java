/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.builder.table;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;

import com.bln.framework.base.BaseObj;
import com.bln.framework.builder.exception.BuildException;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.TableStru;
import com.bln.framework.persist.tablestru.builder.ITableComponentBuilder;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;
import com.bln.framework.persist.tablestru.exception.TableAssembleException;
import com.bln.framework.util.asserter.Assert;

/**
 * ��ṹ��������
 */
public class TableBuilder extends BaseObj implements ITableBuilder{
	
	/**
	 * ���ö���
	 */
	ITableStruConfig tableStruConfig = null;
	
	/**
	 * ��ṹ���������
	 */
	ITableComponentBuilder<?>[] componentBuilders = null;

	/**
	 * @return the componentBuilders
	 */
	public ITableComponentBuilder<?>[] getComponentBuilders() {
		return componentBuilders;
	}

	/**
	 * @param componentBuilders the componentBuilders to set
	 */
	public void setComponentBuilders(ITableComponentBuilder<?>[] componentBuilders) {
		this.componentBuilders = componentBuilders;
	}
	
	/**
	 * ���ر�ṹ���ö���
	 * @param configUrl �����ļ���url
	 * @return ��ṹ���ö���
	 */
	protected void loadConfig(String configUrl)throws Throwable{
		
		byte[] bytes = IOUtils.toByteArray(this.getClass().getResourceAsStream(configUrl));
		tableStruConfig.readFromData(bytes);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablestru.builder.table.ITableStruBuilder#build(com.bln.framework.persist.tablestru.config.ITableStruConfig)
	 */
	public ITableStru build(String configUrl){

		//1.У��ǰ������
		Assert.paramIsNotNull(configUrl, "configUrl");
		
		//2.�������ö���
		try {
			this.loadConfig(configUrl);
		} catch (Throwable e1) {
			BuildException be = new BuildException(" when load configUrl " + configUrl + ", occurs error!");
			be.initCause(e1);
			throw be;
		}
		
		//3.�����ṹ����
		ITableStru tableStru = new TableStru();
		
		//3.1�������
		tableStru.setTableName(tableStruConfig.getTableNm());
		
		//����ģʽ��
		tableStru.setSchemaName(tableStruConfig.getSchemaNm());
		
		//3.2�����ṹ���
		try {
			assembleComponents(tableStruConfig, tableStru);
		} catch (Throwable e) {
			BuildException be = new BuildException();
			be.initCause(e);
			throw be;
		}
		
		//4.���������ֶ�
		parseAllSpecialColumns(tableStru);
			
		//4.���ر�ṹ����
		return tableStru;
		
	}
	
	/**
	 * װ���ṹ���
	 * @param tableStru ��ṹ����
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected void assembleComponents(ITableStruConfig tableStruConfig, ITableStru tableStru){
		
		//���û�������������ֱ�ӷ���
		if(componentBuilders == null || componentBuilders.length <= 0){
			return;
		}
		
		//װ�����
		for ( ITableComponentBuilder<?> builder : componentBuilders){
			
			//�������
			Object tableComponent =  builder.build(tableStruConfig);
			
			//��ȡ�������
			String componentName = builder.getComponentName();
			
			try {
				//װ�����
				if(tableComponent != null){
					BeanUtils.setProperty(tableStru, componentName, tableComponent);
				}
			} catch (Throwable e) {
				TableAssembleException tae = new TableAssembleException(" when assemble " + componentName + " occurs error!");
				tae.initCause(e);
				throw tae;
				
			}
		}		
	}
	
	/**
	 * ���������ֶ�
	 * @param column �����ֶ�
	 * @param allColumns �����ֶ�
	 */
	protected void parseAllSpecialColumns(ITableStru tableStru){
		
		//��������ֶ�
		IColumn[] allColumns = tableStru.getAllColumns().getColumns();
		
		//��������
		IColumn[] ids = tableStru.getIds();
		if(ids != null && ids.length > 0){
			for ( IColumn id : ids){
				parseSpecialColumn(id, allColumns);
			}
		}
		
		//����汾��
		IColumn versionColumn = tableStru.getVerCol();
		parseSpecialColumn(versionColumn, allColumns);
	}
	
	/**
	 * ���������ֶ�
	 * @param column �����ֶ�
	 * @param allColumns �����ֶ�
	 */
	protected void parseSpecialColumn(IColumn column, IColumn[] allColumns){
		
		for ( int i = 0, n = allColumns.length; i < n; i++ ){
			IColumn theColumn = allColumns[i];
			
			if( column != null && column.getColName().equals(theColumn.getColName())){
				column.setColIndx(theColumn.getColIndx());
				column.setColType(theColumn.getColType());
				column.setDefaultVal(theColumn.getDefaultVal());
				column.setGenerator(theColumn.getGenerator());
			}
		}
	}

	/**
	 * @return the tableStruConfig
	 */
	public ITableStruConfig getTableStruConfig() {
		return tableStruConfig;
	}

	/**
	 * @param tableStruConfig the tableStruConfig to set
	 */
	public void setTableStruConfig(ITableStruConfig tableStruConfig) {
		this.tableStruConfig = tableStruConfig;
	}


}
