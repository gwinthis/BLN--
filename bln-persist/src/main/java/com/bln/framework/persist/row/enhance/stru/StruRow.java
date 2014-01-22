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
 * �ṹ�Ե��м�¼
 */
public class StruRow extends EnhanceRowBase implements IStruRow{

	/**
	 * �汾
	 */
	private static final long serialVersionUID = 771642955660313328L;
	
	/**
	 * �ֶ����͵Ľṹ
	 */
	protected Map<String, Integer> columnTypes = null;
	
	/**
	 * ֵת����
	 */
	protected IConverterUtil converterUtil = null;
	
	
	/**
	 * ������
	 */
	public StruRow(){
		super();
	}
	
	/**
	 * ������
	 * @param originalRow ԭʼ�ж���
	 * @param columnTypes �ֶ�����Map
	 * @param converterUtil ת����
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
		
		//1.����ֶ�����
		Integer colType = columnTypes.get(fieldNm);
		if(colType == null){
			throw new GetValueException(fieldNm + " not found in columnTypes");
		}
		
		//2.����ֶ�ֵ
		String info = this.getValue(fieldNm);
		
		//3.ת����ָ������
		Object value = converterUtil.convert(info, colType.intValue());
		
		//4.����ת�����ֵ
		return value;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.row.enhance.stru.IStruRow#setValueFromObject(java.lang.String, java.lang.Object)
	 */
	public void setValueFromObject(String fieldNm, Object value){
		
		//У�����
		Assert.paramIsNotNull(fieldNm, "fieldNm");
		
		//��ö�������
		String info = null;
		if(value != null){
			int colType = getColumnType(fieldNm);
			info = converterUtil.toString(value, colType);
		}
		
		//��ֵ
		this.setValue(fieldNm, info);
	}
	
	/**
	 * ����ֶ�����
	 * @param fieldNm
	 */
	protected int getColumnType(String fieldNm){
		
		//1.����ֶ�����
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
