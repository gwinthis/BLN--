/**
 * @author gengw
 * created on 2012-03-15
 */
package com.bln.framework.filestru.xml.bizdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.bln.framework.filestru.xml.XMLFileBase;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.row.Row;
import com.bln.framework.util.asserter.Assert;

/**
 * XML类型的文件用于业务数据的存储
 */
public abstract class XMLFile4BizDataBase extends XMLFileBase<IRow>{

	/**
	 * 在某一个节点下添加行记录集合
	 * @param owner 将在该节点下添加记录集
	 * @param rowsMap 记录集的名称和内容
	 */
	protected void addRowsOfElement(Element owner, Map<String, List<IRow>> rowsMap){
		
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
	protected void addRowsOfElement(Element owner, String rowName, List<IRow> rows){
		
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
	protected void addRowOfElement(Element owner, String rowName, IRow row){
		
		//1.校验参数
		if(row == null || row.isEmpty()){
			return;
		}
		
		//2.定义元素
		Element element = owner.addElement(rowName);
		
		//3.添加子元素的字段集合
		List<String> fields = row.getAllFields();
		List<String> vals = row.getAllFieldVals();
		
		for(int i = 0, n = fields.size(); i < n; i++){
			
			String fieldNm = fields.get(i);
			String val = vals.get(i);
			
			if(val == null){
				continue;
			}
			
			//3.1添加字段
			Element fieldElement = element.addElement(fieldNm);
			fieldElement.setText(val);
			
			
			//3.2添加属性
			Map<String, String> attrs = row.getFieldAttrs(fieldNm);
			if(attrs != null && !attrs.isEmpty()){
				for ( Map.Entry<String, String> attr : attrs.entrySet()){
					fieldElement.addAttribute(attr.getKey(), attr.getValue());
				}
			}
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
	 * @return
	 */
	@Override
	protected IRow element2Entity(Element element){
				
		//1.获取当前元素下的所有元素
		List <Element> elements = element.elements();
		if(elements == null || elements.size() <= 0 ){
			return null;
		}
		
		//2.生成行对象
		IRow row = new Row();

		//迭代数据项并复制到行对象中
		for (Element e : elements){
			String eleName = e.getName();
			
			//判断是否还有子集
			if(!e.isTextOnly()){
				IRow subRow = element2Entity(e);
				row.addSubRow(eleName, subRow);
			}else{
				
				//设置数据项的值
				row.setValue(eleName, e.getText());
				
				//设置数据项的属性表
				List <Attribute> attrs = e.attributes();
				if(attrs != null && attrs.size() >= 0){
					Map<String, String> attrMap = new HashMap<String, String>();
					for(Attribute attr : attrs){
						attrMap.put(attr.getName(), attr.getValue());
					}
					
					//设置属性表
					row.setFieldAttrs(eleName, attrMap);
				}
			}
		}
		
		//返回行
		return row;
	}

}
