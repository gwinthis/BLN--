/**
 * @author gengw
 * Created on Apr 26, 2012
 */
package com.bln.framework.persist.row.enhance;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bln.framework.persist.row.IRow;

/**
 * 增强型行对象的基类
 */
public abstract class EnhanceRowBase implements IEnhanceRow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 要增强的行记录
	 */
	protected IRow originalRow = null;

	/**
	 * @return the originalRow
	 */
	public IRow getOriginalRow() {
		return originalRow;
	}

	/**
	 * @param originalRow the originalRow to set
	 */
	public void setOriginalRow(IRow originalRow) {
		this.originalRow = originalRow;
	}

	/**
	 * @param subSetNm
	 * @param row
	 * @see com.bln.framework.persist.row.IRow#addSubRow(java.lang.String, com.bln.framework.persist.row.IRow)
	 */
	public void addSubRow(String subSetNm, IRow row) {
		originalRow.addSubRow(subSetNm, row);
	}

	/**
	 * @param subSetNm
	 * @param rows
	 * @see com.bln.framework.persist.row.IRow#addSubRows(java.lang.String, java.util.List)
	 */
	public void addSubRows(String subSetNm, List<IRow> rows) {
		originalRow.addSubRows(subSetNm, rows);
	}

	/**
	 * @return
	 * @see com.bln.framework.persist.row.IRow#getAllAttrsOfFields()
	 */
	public Map<String, Map<String, String>> getAllAttrsOfFields() {
		return originalRow.getAllAttrsOfFields();
	}

	/**
	 * @return
	 * @see com.bln.framework.persist.row.IRow#getAllFields()
	 */
	public List<String> getAllFields() {
		return originalRow.getAllFields();
	}

	/**
	 * @return
	 * @see com.bln.framework.persist.row.IRow#getAllFieldVals()
	 */
	public List<String> getAllFieldVals() {
		return originalRow.getAllFieldVals();
	}

	/**
	 * @return
	 * @see com.bln.framework.persist.row.IRow#getAllSubRows()
	 */
	public Map<String, List<IRow>> getAllSubRows() {
		return originalRow.getAllSubRows();
	}

	/**
	 * @return
	 * @see com.bln.framework.persist.row.IRow#getDataEntrys()
	 */
	public Entry<String, String>[] getDataEntrys() {
		return originalRow.getDataEntrys();
	}

	/**
	 * @param fieldNm
	 * @param attrNm
	 * @return
	 * @see com.bln.framework.persist.row.IRow#getFieldAttr(java.lang.String, java.lang.String)
	 */
	public String getFieldAttr(String fieldNm, String attrNm) {
		return originalRow.getFieldAttr(fieldNm, attrNm);
	}

	/**
	 * @param fieldNm
	 * @return
	 * @see com.bln.framework.persist.row.IRow#getFieldAttrs(java.lang.String)
	 */
	public Map<String, String> getFieldAttrs(String fieldNm) {
		return originalRow.getFieldAttrs(fieldNm);
	}

	/**
	 * @param subSetNm
	 * @return
	 * @see com.bln.framework.persist.row.IRow#getSubRows(java.lang.String)
	 */
	public List<IRow> getSubRows(String subSetNm) {
		return originalRow.getSubRows(subSetNm);
	}

	/**
	 * @param fieldNm
	 * @return
	 * @see com.bln.framework.persist.row.IRow#getValue(java.lang.String)
	 */
	public String getValue(String fieldNm) {
		return originalRow.getValue(fieldNm);
	}

	/**
	 * @param row
	 * @see com.bln.framework.persist.row.IRow#importRow(com.bln.framework.persist.row.IRow)
	 */
	public void importRow(IRow row) {
		originalRow.importRow(row);
	}

	/**
	 * @return
	 * @see com.bln.framework.persist.row.IRow#isEmpty()
	 */
	public boolean isEmpty() {
		return originalRow.isEmpty();
	}

	/**
	 * @return
	 * @see com.bln.framework.persist.row.IRow#isHasSubRows()
	 */
	public boolean isHasSubRows() {
		return originalRow.isHasSubRows();
	}

	/**
	 * @param rowData
	 * @see com.bln.framework.persist.row.IRow#readDataFromMap(java.util.Map)
	 */
	public void readDataFromMap(Map<String, String> rowData) {
		originalRow.readDataFromMap(rowData);
	}

	/**
	 * @param subSetNm
	 * @see com.bln.framework.persist.row.IRow#removeSubRows(java.lang.String)
	 */
	public void removeSubRows(String subSetNm) {
		originalRow.removeSubRows(subSetNm);
	}

	/**
	 * @param allAttrs
	 * @see com.bln.framework.persist.row.IRow#setAllAttrsOfFields(java.util.Map)
	 */
	public void setAllAttrsOfFields(Map<String, Map<String, String>> allAttrs) {
		originalRow.setAllAttrsOfFields(allAttrs);
	}

	/**
	 * @param subRows
	 * @see com.bln.framework.persist.row.IRow#setAllSubRows(java.util.Map)
	 */
	public void setAllSubRows(Map<String, List<IRow>> subRows) {
		originalRow.setAllSubRows(subRows);
	}

	/**
	 * @param fieldNm
	 * @param attrNm
	 * @param attrVal
	 * @see com.bln.framework.persist.row.IRow#setFieldAttr(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setFieldAttr(String fieldNm, String attrNm, String attrVal) {
		originalRow.setFieldAttr(fieldNm, attrNm, attrVal);
	}

	/**
	 * @param fieldNm
	 * @param theAttrs
	 * @see com.bln.framework.persist.row.IRow#setFieldAttrs(java.lang.String, java.util.Map)
	 */
	public void setFieldAttrs(String fieldNm, Map<String, String> theAttrs) {
		originalRow.setFieldAttrs(fieldNm, theAttrs);
	}

	/**
	 * @param fieldNm
	 * @param value
	 * @see com.bln.framework.persist.row.IRow#setValue(java.lang.String, java.lang.String)
	 */
	public void setValue(String fieldNm, String value) {
		originalRow.setValue(fieldNm, value);
	}

	/**
	 * @return
	 * @see com.bln.framework.persist.row.IRow#writeToMap()
	 */
	public Map<String, String> writeToMap() {
		return originalRow.writeToMap();
	}

	/**
	 * @param fieldNm
	 * @see com.bln.framework.persist.row.IRow#removeField(java.lang.String)
	 */
	public void removeField(String fieldNm) {
		originalRow.removeField(fieldNm);
	}
	
}
