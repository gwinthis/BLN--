package com.bln.framework.persist.jdbc.template.storeprocedure;

import java.util.List;

import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureParameters.SPParameter;

public interface IStoreProcedureParameters {

	/**
	 * �������
	 */
	public static final int PARAM_IN = 0;

	/**
	 * ��������
	 */
	public static final int PARAM_OUT = 1;
	
	/**
	 * ��Ӳ���
	 * @param value ����ֵ
	 * @param inout ����������������
	 * @param paramType ��������
	 */
	public abstract void addParameter(Object value, int inout, int paramType);

	/**
	 * ��ò���
	 * @return
	 */
	public abstract List<SPParameter> getParameters();

}