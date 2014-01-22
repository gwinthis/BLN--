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
 * JSON类型的文件用于业务数据的存储
 */
public abstract class JSONFile4BizDataBase extends JSONFileBase<IRow>{

	/**
	 * 在某一个节点下添加行记录集合
	 * @param owner 将在该节点下添加记录集
	 * @param rowsMap 记录集的名称和内容
	 */
	protected void addRowsOfElement(JSONObject owner, Map<String, List<IRow>> rowsMap){
		
		//1.校验参数
		Assert.paramIsNotNull(owner, "owner"); 
		if(rowsMap == null || rowsMap.isEmpty()){
			return;
		}
		
		//2.添加记录集到指定元素下
		for ( Map.Entry<String, List<IRow>> rowsEntry : rowsMap.entrySet()){
			this.addRowsOfElement(owner, rowsEntry.getKey(), rowsEntry.getValue());
		}
	}
	
	/**
	 * 在某一个节点下添加行记录集合
	 * @param owner 将在该节点下添加记录集
	 * @param rowName 记录集的名称
	 * @param rows 记录集
	 */
	protected void addRowsOfElement(JSONObject owner, String rowName, List<IRow> rows){
		
		//1.校验参数
		Assert.paramIsNotNull(owner, "owner");
		Assert.paramIsNotNull(rowName, "rowName");
		
		if(rows == null || rows.isEmpty()){
			return;
		}
		
		//2.添加记录集到指定元素下
		for ( IRow row : rows){
			this.addRowOfElement(owner, rowName, row);
		}
	}
	
	/**
	 * 在某一个节点下添加行记录集合
	 * @param owner 将在该节点下添加记录
	 * @param rowName 记录的名称
	 * @param row 记录
	 */
	protected void addRowOfElement(JSONObject owner, String rowName, IRow row){
		
		//1.校验参数
		if(row == null || row.isEmpty()){
			return;
		}
		
		//2.定义元素
		JSONArray jsonArray = owner.getJSONArray(rowName);
		if(jsonArray == null){
			jsonArray = new JSONArray();
			owner.put(rowName, jsonArray);
		}
		
		JSONObject element = new JSONObject();
		jsonArray.add(element);
		
		//3.添加子元素的字段集合
		List<String> fields = row.getAllFields();
		List<String> vals = row.getAllFieldVals();
		
		for(int i = 0, n = fields.size(); i < n; i++){
			
			String fieldNm = fields.get(i);
			String val = vals.get(i);
			
			if(val == null){
				continue;
			}
			
			//添加字段值
			element.put(fieldNm, val);
		}
		
		//4.添加行记录的子记录集
		Map<String, List<IRow>> subRowsMap = row.getAllSubRows();
		if(subRowsMap != null && !subRowsMap.isEmpty()){
			for ( Map.Entry<String, List<IRow>> subRowsEntry : subRowsMap.entrySet()){
				this.addRowsOfElement(element, subRowsEntry.getKey(), subRowsEntry.getValue());
			}
		}
	}
	
	/**
	 * 把元素生成行对象
	 * @param element
	 * @return 行对象
	 */
	@Override
	protected IRow element2Entity(JSONObject element){
				
		//1.校验参数
		if(element == null || element.size() <= 0 ){
			return null;
		}
		
		//2.生成行对象
		IRow row = new Row();

		//迭代数据项并复制到行对象中
		for (Map.Entry<String, Object> e : element.entrySet()){
			
			//元素名称
			String eleName = e.getKey();
			
			//元素值
			Object value = e.getValue();
			
			//根据值类型做不同的处理
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
				
				//数据项
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
		
		//返回行
		return row;
	}
}
