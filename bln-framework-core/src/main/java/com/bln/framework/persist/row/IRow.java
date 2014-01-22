package com.bln.framework.persist.row;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IRow extends Serializable{

	/**
	 * 是否包含子记录
	 * @return <p>true: 包含</p><p>false: 不包含</p>
	 * 
	 */
	public abstract boolean isHasSubRows();

	/**
	 * 获得数据项的值
	 * @param 数据项名称
	 * @return 数据项值
	 */
	public abstract String getValue(String fieldNm);

	/**
	 * 设置数据项的值
	 * @param 数据项名称
	 * @return 数据项值
	 */
	public abstract void setValue(String fieldNm, String value);
	
	/**
	 * 删除指定字段
	 * @param fieldNm 字段名
	 */
	public void removeField(String fieldNm);
	
	/**
	 * 获得子集
	 * @param 要获得子集的名称
	 * @return 子集
	 */
	public abstract List<IRow> getSubRows(String subSetNm);

	/**
	 * 添加子集
	 * @param 子集名称
	 * @param 要添加的子集
	 */
	
	public abstract void addSubRows(String subSetNm, List<IRow> rows);

	/**
	 * 删除子集
	 * @param 子集名称
	 */
	
	public abstract void removeSubRows(String subSetNm);

	/**
	 * 获得行对象的所有数据项和数据值
	 * @return ENTRY类型的数组
	 */
	public abstract Map.Entry<String, String>[] getDataEntrys();

	/**
	 * 返回行对象的数据结构
	 * @return String类型的数组
	 */
	public abstract List<String> getAllFields();

	/**
	 * 添加子集中的行
	 * @param subSetNm 要添加的子集名称
	 * @param row 子集
	 */
	public void addSubRow(String subSetNm, IRow row);

	/**
	 * 设置一个字段的属性表
	 * @param fieldNm 要设置属性表的字段名称
	 * @param attrs 属性表
	 */
	public Map<String, String> getFieldAttrs(String fieldNm);

	/**
	 * 设置一个字段的属性表
	 * @param fieldNm 要设置属性表的字段名称
	 * @param theAttrs 属性表
	 */
	public void setFieldAttrs(String fieldNm, Map<String, String> theAttrs);

	/**
	 * 设置一个字段的属性值
	 * @param fieldNm 要获取属性的字段名称
	 * @param attrNm 属性名称
	 * @return 属性值
	 */
	public String getFieldAttr(String fieldNm, String attrNm);

	/**
	 * 设置一个字段的属性值
	 * @param fieldNm 要设置属的字段名称
	 * @param attrNm 属性名称
	 * @param attrVal 属性值
	 */
	public void setFieldAttr(String fieldNm, String attrNm, String attrVal);

	/**
	 * 获得行记录中的子集集合
	 * @return 子集集合
	 */
	public Map<String, List<IRow>> getAllSubRows();

	/**
	 * 设置行记录的子集集合
	 * @param subRows 把subRows设置到记录的子集
	 */
	public void setAllSubRows(Map<String, List<IRow>> subRows);

	/**
	 * 把当前行对象的数据作为Map对象返回
	 * @return 返回Key为数据项名称，Value为数据项值的Map
	 */
	public Map<String, String> writeToMap();

	/**
	 * <p>从一个Map对象中读取数据</p>
	 * 该方法将重置当前行对象的存储
	 * @param rowData 将从该对象中读取数据
	 */
	public void readDataFromMap(Map<String, String> rowData);

	/**
	 * 获得字段的所有值
	 * @return 字段值的数组
	 */
	public List<String> getAllFieldVals();

	/**
	 * 是否空记录
	 * @return
	 */
	public boolean isEmpty();

	/**
	 * 获得所有字段的属性列表
	 * @return 所有字段的属性列表
	 */
	public Map<String, Map<String, String>> getAllAttrsOfFields();

	/**
	 * 设置所有属性值
	 * @param allAttrs 所有字段的属性列表
	 */
	public void setAllAttrsOfFields(Map<String, Map<String, String>> allAttrs);

	/**
	 * 从其他行导入数据
	 * @param row 从该记录导入记录
	 */
	public void importRow(IRow row);

}