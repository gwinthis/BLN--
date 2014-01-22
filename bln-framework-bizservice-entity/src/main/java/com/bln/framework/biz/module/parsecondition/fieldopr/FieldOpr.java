/**
 * @author gengw
 * Created on Nov 23, 2012
 */
package com.bln.framework.biz.module.parsecondition.fieldopr;

/**
 * �ֶ����Ͳ�����ʵ��
 */
public class FieldOpr {
	
	/**
	 * �ֶ���
	 */
	protected String field = null;

	/**
	 * �ֶ���
	 */
	protected String fieldValue = null;

	/**
	 * ������
	 */
	protected String opr = null;
	
	/**
	 * �ֶ��Ƿ��Ӧ��ֵ
	 */
	protected boolean isMultiValue = false;
	
	/**
	 * ��ֵ�洢��
	 */
	protected String[] fieldValues = null;
	
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the opr
	 */
	public String getOpr() {
		return opr;
	}

	/**
	 * @param opr the opr to set
	 */
	public void setOpr(String opr) {
		this.opr = opr;
	}

	public FieldOpr(String field, String fieldValue, String opr) {
		super();
		this.field = field;
		this.fieldValue = fieldValue;
		this.opr = opr;
	}

	public FieldOpr() {
		super();
	}

	/**
	 * @return the fieldValue
	 */
	public String getFieldValue() {
		return fieldValue;
	}

	/**
	 * @param fieldValue the fieldValue to set
	 */
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @return the isMultiValue
	 */
	public boolean isMultiValue() {
		return isMultiValue;
	}

	/**
	 * @param isMultiValue the isMultiValue to set
	 */
	public void setMultiValue(boolean isMultiValue) {
		this.isMultiValue = isMultiValue;
	}

	/**
	 * @return the fieldValues
	 */
	public String[] getFieldValues() {
		return fieldValues;
	}

	/**
	 * @param fieldValues the fieldValues to set
	 */
	public void setFieldValues(String[] fieldValues) {
		this.fieldValues = fieldValues;
	}

}
