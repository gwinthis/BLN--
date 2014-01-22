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
 * XML���͵��ļ�����ҵ�����ݵĴ洢
 */
public abstract class XMLFile4BizDataBase extends XMLFileBase<IRow>{

	/**
	 * ��ĳһ���ڵ�������м�¼����
	 * @param owner ���ڸýڵ�����Ӽ�¼��
	 * @param rowsMap ��¼�������ƺ�����
	 */
	protected void addRowsOfElement(Element owner, Map<String, List<IRow>> rowsMap){
		
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
	protected void addRowsOfElement(Element owner, String rowName, List<IRow> rows){
		
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
	protected void addRowOfElement(Element owner, String rowName, IRow row){
		
		//1.У�����
		if(row == null || row.isEmpty()){
			return;
		}
		
		//2.����Ԫ��
		Element element = owner.addElement(rowName);
		
		//3.�����Ԫ�ص��ֶμ���
		List<String> fields = row.getAllFields();
		List<String> vals = row.getAllFieldVals();
		
		for(int i = 0, n = fields.size(); i < n; i++){
			
			String fieldNm = fields.get(i);
			String val = vals.get(i);
			
			if(val == null){
				continue;
			}
			
			//3.1����ֶ�
			Element fieldElement = element.addElement(fieldNm);
			fieldElement.setText(val);
			
			
			//3.2�������
			Map<String, String> attrs = row.getFieldAttrs(fieldNm);
			if(attrs != null && !attrs.isEmpty()){
				for ( Map.Entry<String, String> attr : attrs.entrySet()){
					fieldElement.addAttribute(attr.getKey(), attr.getValue());
				}
			}
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
	 * @return
	 */
	@Override
	protected IRow element2Entity(Element element){
				
		//1.��ȡ��ǰԪ���µ�����Ԫ��
		List <Element> elements = element.elements();
		if(elements == null || elements.size() <= 0 ){
			return null;
		}
		
		//2.�����ж���
		IRow row = new Row();

		//������������Ƶ��ж�����
		for (Element e : elements){
			String eleName = e.getName();
			
			//�ж��Ƿ����Ӽ�
			if(!e.isTextOnly()){
				IRow subRow = element2Entity(e);
				row.addSubRow(eleName, subRow);
			}else{
				
				//�����������ֵ
				row.setValue(eleName, e.getText());
				
				//��������������Ա�
				List <Attribute> attrs = e.attributes();
				if(attrs != null && attrs.size() >= 0){
					Map<String, String> attrMap = new HashMap<String, String>();
					for(Attribute attr : attrs){
						attrMap.put(attr.getName(), attr.getValue());
					}
					
					//�������Ա�
					row.setFieldAttrs(eleName, attrMap);
				}
			}
		}
		
		//������
		return row;
	}

}
