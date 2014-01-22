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
 * ��ṹ������ֶι�����
 */
public class AllColumnsComponentBuilder extends ComponentBuilderBase<IAllColumnsComponent>{

	/**
	 * ���캯��
	 */
	public AllColumnsComponentBuilder(){
		super();
		this.componentName = ITableStru.COMPONENT_ALLCOLUMNS_NAME;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.builder.IBuilder1Step#build(java.lang.Object)
	 */
	public IAllColumnsComponent build(ITableStruConfig tableStruConfig) {
		
		//1.У�����
		Assert.paramIsNotNull(tableStruConfig, "tableStruConfig");
				
		//���û���ֶ�ֱ�ӷ���
		List<ITableStruConfigEntity> colConfigEntitys = tableStruConfig.getColumns();
		if(colConfigEntitys == null || colConfigEntitys.size() <= 0){
			return null;
		}

		//2.����AllColumnComponent����
		IAllColumnsComponent allColumnComponent = new AllColumnsComponent();

		//2.1�����ֶ�ʵ��

		//2.1��ʼ�ֶ�
		int colCount = colConfigEntitys.size();
		IColumn[] columns = new IColumn[colCount];
		
		for ( int i = 0; i < colCount; i++ ){
			
			//��ȡ������Ϣ
			ITableStruConfigEntity config = colConfigEntitys.get(i);
			
			//�����ֶζ���
			columns[i] = new Column();
			parseColumn(config, columns[i]);
			
			//���˳���
			columns[i].setColIndx(i + 1);
		}
		
		//���������ֶ�ʵ��
		allColumnComponent.setColumns(columns);
		
		//2.2����������ֵ�ֶ�
		
		//��ȡ����
		String tableNm = tableStruConfig.getTableNm();
		
		//��ȡ����
		List<ITableStruConfigEntity> idConfigEntitys = tableStruConfig.getIDS();
		
		//�����������ֶ�
		IColumn[] allColumnOrders = new IColumn[colCount];

		//������������û���������ֶ�
		List<IColumn> columnOrderNoIds = new ArrayList<IColumn>(colCount);

		//�����ֶ���������
		String[] colNames = new String[colCount];
		
		//������������ֶ���������
		String[] orderColNames = new String[colCount];
		
		//�����ֶ������ַ���
		StringBuilder colNamesAsStr = new StringBuilder();
		
		//�������������ֶ������ַ���
		StringBuilder colNamesWithTableNm = new StringBuilder();
		
		//�ֶ�����Map
		Map<String, Integer> columnTypeMap = new HashMap<String, Integer>();
		
		for ( int i = 0; i < colCount; i++){
			
			IColumn column = columns[i];
			
			//�����ֶ�����
			allColumnOrders[i] = column;
						
			//�ֶ���������
			String colName = column.getColName();
			
			//�����ֶ�����
			colNames[i] = colName;
			
			//�������ֶ�����
			orderColNames[i] = colName;
			
			//�������������ֶ�����
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

			
			//��Ӷ���
			if(i != 0){
				colNamesAsStr.append(", ");
				colNamesWithTableNm.append(", ");
			}
			
			//���ֶ������ַ���
			colNamesAsStr.append(colName);
			
			//�������������ֶ������ַ���
			colNamesWithTableNm.append(tableNm).append(".").append(colName);
			
			//�ֶ�����Map
			columnTypeMap.put(colName, column.getColType());
		}
		
		//2.5 �����ֶ�
		
		//2.5.1���������������ֶ�����
		Arrays.sort(allColumnOrders);
		allColumnComponent.setOrderColumns(allColumnOrders);
		
		//2.5.2����û��������������ֶ�����
		IColumn[] columnOrderNoIdArray = columnOrderNoIds.toArray(new IColumn[columnOrderNoIds.size()]);
		Arrays.sort(columnOrderNoIdArray);
		
		allColumnComponent.setOrderColumnNoIds(columnOrderNoIdArray);
		
		//2.3�����ֶ���������
		allColumnComponent.setColNames(colNames);
		
		//2.4������������ֶ���������
		Arrays.sort(orderColNames);
		allColumnComponent.setOrderColNames(orderColNames);
		
		//2.5���������ֶ������ַ���
		allColumnComponent.setColNamesAsStr(colNamesAsStr.toString());

		//2.6����������������ֶ������ַ���
		allColumnComponent.setColNamesWithTableNm(colNamesWithTableNm.toString());

		//2.7 �����ֶ�����Map
		allColumnComponent.setColumnTypeMap(columnTypeMap);

		//3.�����ֶ����
		return allColumnComponent;
	}	
}
