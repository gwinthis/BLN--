package com.bln.framework.persist.jdbc.template.storeprocedure;

import java.util.List;

import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureParameters.SPParameter;

public interface IStoreProcedureParameters {

	/**
	 * 传入参数
	 */
	public static final int PARAM_IN = 0;

	/**
	 * 传出参数
	 */
	public static final int PARAM_OUT = 1;
	
	/**
	 * 添加参数
	 * @param value 参数值
	 * @param inout 输入参数，输出参数
	 * @param paramType 参数类型
	 */
	public abstract void addParameter(Object value, int inout, int paramType);

	/**
	 * 获得参数
	 * @return
	 */
	public abstract List<SPParameter> getParameters();

}