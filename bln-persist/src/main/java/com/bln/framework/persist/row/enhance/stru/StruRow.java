/**
 * @author gengw
 * Created on Apr 26, 2012
 */
package com.bln.framework.persist.row.enhance.stru;

import java.util.Map;

import com.bln.framework.persist.jdbc.converter.util.IConverterUtil;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.row.enhance.EnhanceRowBase;
import com.bln.framework.persist.row.enhance.stru.exception.GetValueException;
import com.bln.framework.util.asserter.Assert;

/**
 * 结构性的行记录
 */
public class StruRow extends EnhanceRowBase implements IStruRow{

	/**
	 * 版本
	 */
	private static final long serialVersionUID = 771642955660313328L;
	
	/**
	 * 字段类型的结构
	 */
	protected Map<String, Integer> columnTypes = null;
	
	/**
	 * 值转换器
	 */
	protected IConverterUtil converterUtil = null;
	
	
	/**
	 * 构造子
	 */
	public StruRow(){
		super();
	}
	
	/**
	 * 构造子
	 * @param originalRow 原始行对象
	 * @param columnTypes 字段类型Map
	 * @param converterUtil 转换器
	 */
	public StruRow(IRow originalRow, Map<String, Integer> columnTypes, IConverterUtil converterUtil ){
		super();
		this.originalRow = originalRow;
		this.columnTypes = columnTypes;
		this.converterUtil = converterUtil;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.enhance.stru.IStruRow#getValueAsObject(java.lang.String)
	 */
	public Object getValueAsObject(String fieldNm){
		
		Assert.paramIsNotNull(fieldNm, "fieldNm");
		
		//1.获得字段类型
		Integer colType = columnTypes.get(fieldNm);
		if(colType == null){
			throw new GetValueException(fieldNm + " not found in columnTypes");
		}
		
		//2.获得字段值
		String info = this.getValue(fieldNm);
		
		//3.转换成指定类型
		Object value = converterUtil.convert(info, colType.intValue());
		
		//4.返回转换后的值
		return value;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.enhance.stru.IStruRow#setValueFromObject(java.lang.String, java.lang.Object)
	 */
	public void setValueFromObject(String fieldNm, Object value){
		
		//校验参数
		Assert.paramIsNotNull(fieldNm, "fieldNm");
		
		//获得对象描述
		String info = null;
		if(value != null){
			int colType = getColumnType(fieldNm);
			info = converterUtil.toString(value, colType);
		}
		
		//赋值
		this.setValue(fieldNm, info);
	}
	
	/**
	 * 获得字段类型
	 * @param fieldNm
	 */
	protected int getColumnType(String fieldNm){
		
		//1.获得字段类型
		Integer colType = columnTypes.get(fieldNm);
		if(colType == null){
			throw new GetValueException(fieldNm + " not found in columnTypes");
		}
		
		return colType.intValue();
	}
	
	

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.enhance.stru.IStruRow#getColumnTypes()
	 */
	public Map<String, Integer> getColumnTypes() {
		return columnTypes;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.enhance.stru.IStruRow#setColumnTypes(java.util.Map)
	 */
	public void setColumnTypes(Map<String, Integer> columnTypes) {
		this.columnTypes = columnTypes;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.enhance.stru.IStruRow#getConverterUtil()
	 */
	public IConverterUtil getConverterUtil() {
		return converterUtil;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.enhance.stru.IStruRow#setConverterUtil(com.bln.framework.persist.jdbc.converter.util.IConverterUtil)
	 */
	public void setConverterUtil(IConverterUtil converterUtil) {
		this.converterUtil = converterUtil;
	}
	

}
