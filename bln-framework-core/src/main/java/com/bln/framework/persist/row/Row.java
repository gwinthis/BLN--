/**
 * @author gengw
 * Created on 2012-03-13
 */
package com.bln.framework.persist.row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang3.math.NumberUtils;

import com.bln.framework.base.BaseObj;
import com.bln.framework.util.asserter.Assert;

/**
 * <p>�ж���һ�м�¼������</p>
 * ���������ƺ�������ֵֻ������String���ö����������Ӽ���
 */
public class Row extends BaseObj implements IRow {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6429224737599229969L;

	/**
	 * �����ֶε�����
	 */
	protected final List<String> fields = new ArrayList<String>();

	/**
	 * �����ֶε�ֵ
	 */
	protected final List<String> vals = new ArrayList<String>();
	
	/**
	 * �ж������洢���Ӽ�
	 * �Ӽ������Ӽ�
	 */
	protected final Map<String, List<IRow>> subRows = new HashMap<String, List<IRow>>();

	/**
	 * �ֶε������б�
	 * �ֶ����������б�
	 */
	protected final Map<String, Map<String, String>> fieldAttrs = new HashMap<String, Map<String, String>>();
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#isEmpty()
	 */
	public boolean isEmpty(){
		return (fields == null || fields.isEmpty()) && 
			   (subRows == null || subRows.isEmpty());
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#isHasSubRows()
	 */
	public boolean isHasSubRows(){
		return subRows != null && !subRows.isEmpty();
	}
	
	/**
	 * ����ֶ����Ƶ�����ֵ
	 * @param fieldNm
	 * @return
	 */
	public int idxOfField(String fieldNm){
		Assert.paramIsNotNull(fieldNm, "fieldNm");
		
		int i = 0;
		int n = fields.size();
		for (; i < n; i++){
			if(fieldNm.equals(fields.get(i))){
				break;
			}
		}
		if( i == n ){
			i = -1;
		}
		return i;
	}
	
	/**
	 * @param fieldNm
	 * @return
	 */
	public Integer getIntValue(String fieldNm){
		return NumberUtils.toInt(this.getValue(fieldNm));
	}
	
	/**
	 * @param fieldNm
	 * @return
	 */
	public Integer getIntegerValue(String fieldNm){
		String value = this.getValue(fieldNm);
		return value == null ? null : new Integer(value);
	}
	
	/**
	 * @param fieldNm
	 * @return
	 */
	public Double getdoubleValue(String fieldNm){
		return NumberUtils.toDouble(this.getValue(fieldNm));
	}
	
	/**
	 * @param fieldNm
	 * @return
	 */
	public Double getDoubleValue(String fieldNm){
		String value = this.getValue(fieldNm);
		return value == null ? null : new Double(value);
	}
	
	/**
	 * @param fieldNm
	 * @return
	 */
	public float getfloatValue(String fieldNm){
		return NumberUtils.toFloat(this.getValue(fieldNm));
	}
	
	/**
	 * @param fieldNm
	 * @return
	 */
	public Float getFloatValue(String fieldNm){
		String value = this.getValue(fieldNm);
		return value == null ? null : new Float(value);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#getValue(java.lang.String)
	 */
	public String getValue(String fieldNm){
		int idx = idxOfField(fieldNm);
		return idx < 0 ? null : this.vals.get(idx);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#setValue(java.lang.String, java.lang.String)
	 */
	public void setValue(String fieldNm, String value){
		int idx = idxOfField(fieldNm);
		if(idx >= 0){
			this.vals.set(idx, value);
		}else{
			this.fields.add(fieldNm);
			this.vals.add(value);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#getSubRows(java.lang.String)
	 */
	public List<IRow> getSubRows(String subSetNm){
		Assert.paramIsNotNull(subSetNm, "subSetNm");
		return subRows.get(subSetNm);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#addSubRows(java.lang.String, java.util.List)
	 */
	public void addSubRows(String subSetNm, List<IRow> rows){
		Assert.paramIsNotNull(subSetNm, "subSetNm");
		subRows.put(subSetNm, rows);
	}
	

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#addSubRow(java.lang.String, com.bln.framework.persist.row.IRow)
	 */
	public void addSubRow(String subSetNm, IRow row){
		
		//�жϲ���
		Assert.paramIsNotNull(subSetNm, "subSetNm");
				
		//����Ӽ�
		List<IRow> rows = subRows.get(subSetNm);
		if(rows == null){
			rows = new ArrayList<IRow>();
			subRows.put(subSetNm, rows);
		}
		
		//���ж�����ӵ��Ӽ���
		rows.add(row);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#removeField(java.lang.String)
	 */
	public void removeField(String fieldNm) {
		
		//�жϲ���
		Assert.paramIsNotNull(fieldNm, "fieldNm");
		
		int idx = idxOfField(fieldNm);
		if(idx >=0 ){
			this.fields.remove(idx);
			this.vals.remove(idx);
		}
		
		this.fieldAttrs.remove(fieldNm);
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#removeSubRows(java.lang.String)
	 */
	public void removeSubRows(String subSetNm){
		Assert.paramIsNotNull(subSetNm, "subSetNm");
		subRows.remove(subSetNm);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#getFieldAttrs(java.lang.String)
	 */
	public Map<String, String> getFieldAttrs(String fieldNm){
		
		//�жϲ���
		Assert.paramIsNotNull(fieldNm, "fieldNm");
		
		//�������Ա�
		return fieldAttrs.get(fieldNm);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#setFieldAttrs(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public void setFieldAttrs(String fieldNm, Map<String, String> theAttrs){
		
		//�жϲ���
		Assert.paramIsNotNull(fieldNm, "fieldNm");
		
		//����ֶε�����Map
		Map<String, String> attrs = ListOrderedMap.decorate(theAttrs);
		this.fieldAttrs.put(fieldNm, attrs);
	}
	
	/**
	 * ��������ֶε������б�
	 * @return �����ֶε������б�
	 */
	public Map<String, Map<String, String>> getAllAttrsOfFields(){
		return this.fieldAttrs;
	}

	/**
	 * ������������ֵ
	 * @param allAttrs �����ֶε������б�
	 */
	public void setAllAttrsOfFields(Map<String, Map<String, String>> allAttrs){
				
		this.fieldAttrs.clear();
		fieldAttrs.putAll(allAttrs);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#getFieldAttr(java.lang.String, java.lang.String)
	 */
	public String getFieldAttr(String fieldNm, String attrNm){
		
		//�жϲ���
		Assert.paramIsNotNull(fieldNm, "fieldNm");

		//�жϲ���
		Assert.paramIsNotNull(attrNm, "attrNm");
			
		//��������б�
		Map<String, String> attrs = fieldAttrs.get(attrNm);
		if(attrs == null || attrs.isEmpty()){
			return null;
		}
		
		//��������ֵ
		return attrs.get(attrNm);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#setFieldAttr(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void setFieldAttr(String fieldNm, String attrNm, String attrVal){
		
		//�жϲ���
		Assert.paramIsNotNull(fieldNm, "fieldNm");

		//�жϲ���
		Assert.paramIsNotNull(attrNm, "attrNm");
		
		//����Ӽ�
		Map<String, String> attrs = fieldAttrs.get(fieldNm);
		if(attrs == null){
			attrs = ListOrderedMap.decorate(new HashMap<String, String>());
			fieldAttrs.put(fieldNm, attrs);
		}
		
		//�����ֶε�����ֵ
		attrs.put(attrNm, attrVal);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#getDataEntrys()
	 */
	@SuppressWarnings("unchecked")
	public Map.Entry<String, String>[] getDataEntrys(){
		
		Map<String, String> rowData = this.writeToMap();
		if(rowData == null){
			return null;
		}
		Set<Map.Entry<String, String>> set = rowData.entrySet();
		return set == null ? null : set.toArray(new Map.Entry[set.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#getDataFields()
	 */
	public List<String> getAllFields(){
		return this.fields;
	}
		
	/**
	 * ����ֶε�����ֵ
	 * @return �ֶ�ֵ������
	 */
	public List<String> getAllFieldVals(){
		return this.vals;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#asMap()
	 */
	public Map<String, String> writeToMap() {

		//����MAP
		Map<String, String> rowData = new HashMap<String, String>(fields.size());
		for ( int i = 0, n = fields.size(); i < n; i++){
			rowData.put(fields.get(i), vals.get(i));
		}
		
		//����MAP
		return rowData;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#readDataFromMap(java.util.Map)
	 */
	public void readDataFromMap(Map<String, String> rowData) {
		
		//���ô洢
		this.clear();
				
		//��ֵ����
		int i = 0;
		for ( Map.Entry<String, String> entry : rowData.entrySet()){
			fields.set(i, entry.getKey());
			vals.set(i, entry.getValue());
			i++;
		}
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#getSubRows()
	 */
	public Map<String, List<IRow>> getAllSubRows() {
		return subRows;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#setSubRows(java.util.Map)
	 */
	public void setAllSubRows(Map<String, List<IRow>> subRows) {
		this.subRows.clear();
		this.subRows.putAll(subRows);
	}
	
	/**
	 * ���ô洢
	 */
	public void clear(){
		this.fields.clear();
		this.vals.clear();
		this.fieldAttrs.clear();
		this.subRows.clear();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.IRow#importRow(com.bln.framework.persist.row.IRow)
	 */
	public void importRow(IRow row){
		if(row == null){
			return;
		}
		
		//���ô洢
		this.clear();
		
		//�����еĴ洢
		this.fields.addAll(row.getAllFields());
		this.vals.addAll(row.getAllFieldVals());
		this.fieldAttrs.putAll(row.getAllAttrsOfFields());
		this.setAllSubRows(row.getAllSubRows());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer info = new StringBuffer();
		
		//1.����������ֵ
		info.append("The Data Of Fields: ").append("\r\n");
		
		//����������
		if(fields != null && !fields.isEmpty()){
			for(int i = 0, n = fields.size(); i < n; i++){
				if(i != 0){
					info.append(", ");
				}
				info.append(fields.get(i)).append(" = ").append(vals.get(i));
			}
		}
		
		//2.����Ӽ�
		//�����Ӽ�
		if(subRows != null && !subRows.isEmpty()){
			info.append("\r\n").append("The Info Of SubSets: ");
			for(Map.Entry<String, List<IRow>> entry : subRows.entrySet()){
				info.append("\r\n");
				info.append(entry.getKey()).append(" subset: ");
				List<IRow> rows = entry.getValue();
				if(rows != null && !rows.isEmpty()){
					for ( int i = 0, n = rows.size(); i < n; i++){
						info.append("\r\n").append(i).append(".  ");
						info.append(rows.get(i));
					}
				}
			}
		}
		
		info.append("\r\n");
		
		//3.���ؽ��
		return info.toString();
	}	
}
