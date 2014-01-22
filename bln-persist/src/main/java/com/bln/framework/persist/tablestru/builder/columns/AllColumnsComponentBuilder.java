/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.persist.tablestru.builder.columns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bln.framework.persist.tablestru.ITableStru;

import com.bln.framework.persist.tablestru.builder.ComponentBuilderBase;
import com.bln.framework.persist.tablestru.component.allcolumn.AllColumnsComponent;
import com.bln.framework.persist.tablestru.component.allcolumn.IAllColumnsComponent;
import com.bln.framework.persist.tablestru.component.column.Column;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.config.ITableStruConfig;
import com.bln.framework.persist.tablestru.config.entity.ITableStruConfigEntity;
import com.bln.framework.util.asserter.Assert;

/**
 * 表结构对象的字段构造器
 */
public class AllColumnsComponentBuilder extends ComponentBuilderBase<IAllColumnsComponent>{

	/**
	 * 构造函数
	 */
	public AllColumnsComponentBuilder(){
		super();
		this.componentName = ITableStru.COMPONENT_ALLCOLUMNS_NAME;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.builder.IBuilder1Step#build(java.lang.Object)
	 */
	public IAllColumnsComponent build(ITableStruConfig tableStruConfig) {
		
		//1.校验参数
		Assert.paramIsNotNull(tableStruConfig, "tableStruConfig");
				
		//如果没有字段直接返回
		List<ITableStruConfigEntity> colConfigEntitys = tableStruConfig.getColumns();
		if(colConfigEntitys == null || colConfigEntitys.size() <= 0){
			return null;
		}

		//2.构造AllColumnComponent对象
		IAllColumnsComponent allColumnComponent = new AllColumnsComponent();

		//2.1生成字段实体

		//2.1初始字段
		int colCount = colConfigEntitys.size();
		IColumn[] columns = new IColumn[colCount];
		
		for ( int i = 0; i < colCount; i++ ){
			
			//获取配置信息
			ITableStruConfigEntity config = colConfigEntitys.get(i);
			
			//解析字段对象
			columns[i] = new Column();
			parseColumn(config, columns[i]);
			
			//添加顺序号
			columns[i].setColIndx(i + 1);
		}
		
		//设置所有字段实体
		allColumnComponent.setColumns(columns);
		
		//2.2设置其他增值字段
		
		//获取表名
		String tableNm = tableStruConfig.getTableNm();
		
		//获取主键
		List<ITableStruConfigEntity> idConfigEntitys = tableStruConfig.getIDS();
		
		//所有已排序字段
		IColumn[] allColumnOrders = new IColumn[colCount];

		//所有已排序但是没有主键的字段
		List<IColumn> columnOrderNoIds = new ArrayList<IColumn>(colCount);

		//所有字段名称数组
		String[] colNames = new String[colCount];
		
		//已排序的所有字段名称数组
		String[] orderColNames = new String[colCount];
		
		//所有字段名称字符串
		StringBuilder colNamesAsStr = new StringBuilder();
		
		//带表名的所有字段名称字符串
		StringBuilder colNamesWithTableNm = new StringBuilder();
		
		//字段类型Map
		Map<String, Integer> columnTypeMap = new HashMap<String, Integer>();
		
		for ( int i = 0; i < colCount; i++){
			
			IColumn column = columns[i];
			
			//所有字段数组
			allColumnOrders[i] = column;
						
			//字段名称数组
			String colName = column.getColName();
			
			//所有字段名称
			colNames[i] = colName;
			
			//排序后的字段名称
			orderColNames[i] = colName;
			
			//不包含主键的字段数组
			boolean isId = false;
			if(idConfigEntitys != null){
				for (ITableStruConfigEntity configEntity : idConfigEntitys){
					if(column.getColName().equals(configEntity.getAttr(ITableStruConfig.ATTR_NAME))){
						isId = true;
						break;
					}
				}
			}
			
			if(!isId){
				columnOrderNoIds.add(columns[i]);
			}

			
			//添加逗号
			if(i != 0){
				colNamesAsStr.append(", ");
				colNamesWithTableNm.append(", ");
			}
			
			//所字段名称字符串
			colNamesAsStr.append(colName);
			
			//带表名的所有字段名称字符串
			colNamesWithTableNm.append(tableNm).append(".").append(colName);
			
			//字段类型Map
			columnTypeMap.put(colName, column.getColType());
		}
		
		//2.5 定义字段
		
		//2.5.1定义所有已排序字段数组
		Arrays.sort(allColumnOrders);
		allColumnComponent.setOrderColumns(allColumnOrders);
		
		//2.5.2定义没有主键已排序的字段数组
		IColumn[] columnOrderNoIdArray = columnOrderNoIds.toArray(new IColumn[columnOrderNoIds.size()]);
		Arrays.sort(columnOrderNoIdArray);
		
		allColumnComponent.setOrderColumnNoIds(columnOrderNoIdArray);
		
		//2.3定义字段名称数组
		allColumnComponent.setColNames(colNames);
		
		//2.4定义已排序的字段名称数组
		Arrays.sort(orderColNames);
		allColumnComponent.setOrderColNames(orderColNames);
		
		//2.5定义所有字段名称字符串
		allColumnComponent.setColNamesAsStr(colNamesAsStr.toString());

		//2.6定义带表名的所有字段名称字符串
		allColumnComponent.setColNamesWithTableNm(colNamesWithTableNm.toString());

		//2.7 定义字段类型Map
		allColumnComponent.setColumnTypeMap(columnTypeMap);

		//3.返回字段组件
		return allColumnComponent;
	}	
}
