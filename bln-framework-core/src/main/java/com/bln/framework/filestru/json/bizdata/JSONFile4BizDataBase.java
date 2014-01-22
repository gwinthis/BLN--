/**
 * @author gengw
 * Created on Jul 17, 2012
 */
package com.bln.framework.filestru.json.bizdata;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.bln.framework.filestru.json.JSONFileBase;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.row.Row;
import com.bln.framework.util.asserter.Assert;

/**
 * JSON���͵��ļ�����ҵ�����ݵĴ洢
 */
public abstract class JSONFile4BizDataBase extends JSONFileBase<IRow>{

	/**
	 * ��ĳһ���ڵ�������м�¼����
	 * @param owner ���ڸýڵ�����Ӽ�¼��
	 * @param rowsMap ��¼�������ƺ�����
	 */
	protected void addRowsOfElement(JSONObject owner, Map<String, List<IRow>> rowsMap){
		
		//1.У�����
		Assert.paramIsNotNull(owner, "owner"); 
		if(rowsMap == null || rowsMap.isEmpty()){
			return;
		}
		
		//2.��Ӽ�¼����ָ��Ԫ����
		for ( Map.Entry<String, List<IRow>> rowsEntry : rowsMap.entrySet()){
			this.addRowsOfElement(owner, rowsEntry.getKey(), rowsEntry.getValue());
		}
	}
	
	/**
	 * ��ĳһ���ڵ�������м�¼����
	 * @param owner ���ڸýڵ�����Ӽ�¼��
	 * @param rowName ��¼��������
	 * @param rows ��¼��
	 */
	protected void addRowsOfElement(JSONObject owner, String rowName, List<IRow> rows){
		
		//1.У�����
		Assert.paramIsNotNull(owner, "owner");
		Assert.paramIsNotNull(rowName, "rowName");
		
		if(rows == null || rows.isEmpty()){
			return;
		}
		
		//2.��Ӽ�¼����ָ��Ԫ����
		for ( IRow row : rows){
			this.addRowOfElement(owner, rowName, row);
		}
	}
	
	/**
	 * ��ĳһ���ڵ�������м�¼����
	 * @param owner ���ڸýڵ�����Ӽ�¼
	 * @param rowName ��¼������
	 * @param row ��¼
	 */
	protected void addRowOfElement(JSONObject owner, String rowName, IRow row){
		
		//1.У�����
		if(row == null || row.isEmpty()){
			return;
		}
		
		//2.����Ԫ��
		JSONArray jsonArray = owner.getJSONArray(rowName);
		if(jsonArray == null){
			jsonArray = new JSONArray();
			owner.put(rowName, jsonArray);
		}
		
		JSONObject element = new JSONObject();
		jsonArray.add(element);
		
		//3.�����Ԫ�ص��ֶμ���
		List<String> fields = row.getAllFields();
		List<String> vals = row.getAllFieldVals();
		
		for(int i = 0, n = fields.size(); i < n; i++){
			
			String fieldNm = fields.get(i);
			String val = vals.get(i);
			
			if(val == null){
				continue;
			}
			
			//����ֶ�ֵ
			element.put(fieldNm, val);
		}
		
		//4.����м�¼���Ӽ�¼��
		Map<String, List<IRow>> subRowsMap = row.getAllSubRows();
		if(subRowsMap != null && !subRowsMap.isEmpty()){
			for ( Map.Entry<String, List<IRow>> subRowsEntry : subRowsMap.entrySet()){
				this.addRowsOfElement(element, subRowsEntry.getKey(), subRowsEntry.getValue());
			}
		}
	}
	
	/**
	 * ��Ԫ�������ж���
	 * @param element
	 * @return �ж���
	 */
	@Override
	protected IRow element2Entity(JSONObject element){
				
		//1.У�����
		if(element == null || element.size() <= 0 ){
			return null;
		}
		
		//2.�����ж���
		IRow row = new Row();

		//������������Ƶ��ж�����
		for (Map.Entry<String, Object> e : element.entrySet()){
			
			//Ԫ������
			String eleName = e.getKey();
			
			//Ԫ��ֵ
			Object value = e.getValue();
			
			//����ֵ��������ͬ�Ĵ���
			if(value instanceof JSONObject){
				
				JSONObject jsonObject = (JSONObject)value;
				
				IRow subRow = element2Entity(jsonObject);
				row.addSubRow(eleName, subRow);
				
			}else if(value instanceof JSONArray){
				
				JSONArray jsonArray = (JSONArray)value;
				for (Object o : jsonArray){
					if(o instanceof JSONObject){
						JSONObject jsonObject = (JSONObject)o;
						
						IRow subRow = element2Entity(jsonObject);
						row.addSubRow(eleName, subRow);
					}
				}
				
			}else{
				
				//������
				String strValue = null;
				if(e != null){
					Object obj = e.getValue();
					if(obj != null){
						strValue = obj.toString();
					}
				}
				row.setValue(eleName, strValue);
			}
		}
		
		//������
		return row;
	}
}
