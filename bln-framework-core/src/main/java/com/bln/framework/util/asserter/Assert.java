/**
 * @author gengw
 * Created on 2012-03-12
 */
package com.bln.framework.util.asserter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * 断言类
 */
public class Assert extends Validate{
	
	/**
	 * <p>断言参数不能为空</p>
	 * 字符串判断不能null,不能为""
	 * @param var 要校验的变量
	 * @param paramNm 变量名称
	 */
	public static void paramIsNotNull(String param, String paramNm){
		paramIsNotNull(param, paramNm, null);
	}
	
	/**
	 * <p>断言参数不能为空</p>
	 * 字符串判断不能null,不能为""
	 * @param var 要校验的变量
	 * @param paramNm 变量名称
	 * @param tips 错误提示
	 */
	public static void paramIsNotNull(String param, String paramNm, String tips){
		if(StringUtils.isEmpty(param)){
			throw newNotNullExeption(paramNm, tips);
		}
	}
	
	/**
	 * <p>断言参数不能为空</p>
	 * 数组判断不能null，并且长度要大于零
	 * @param var 要校验的变量
	 * @param paramNm 变量名称
	 */
	public static <T> void paramIsNotNull(T[] param, String paramNm){
		paramIsNotNull(param, paramNm, null);
	}
	
	/**
	 * <p>断言参数不能为空</p>
	 * 数组判断不能null，并且长度要大于零
	 * @param var 要校验的变量
	 * @param paramNm 变量名称
	 * @param tips 错误提示
	 */
	public static <T> void paramIsNotNull(T[] param, String paramNm, String tips){
		if(param == null || param.length == 0){
			throw newNotNullExeption(paramNm, tips);
		}
	}
	
	/**
	 * <p>断言参数不能为空</p>
	 * @param <T> 变量类型
	 * @param var 要校验的变量
	 * @param paramNm 变量名称
	 */
	public static <T> void paramIsNotNull(T var, String paramNm){
		paramIsNotNull(var, paramNm, null);
	}
	
	/**
	 * <p>断言参数不能为空</p>
	 * @param <T> 变量类型
	 * @param var 要校验的变量
	 * @param paramNm 变量名称
	 * @param tips 错误提示
	 */
	public static <T> void paramIsNotNull(T var, String paramNm, String tips){
		if(var == null){
			throw newNotNullExeption(paramNm, tips);
		}
	}
	
	/**
	 * 表达式不能为真，如果为真抛出异常
	 * @param exp 表达式结果
	 * @param expLabel 表达式label
	 */
	public static void expressionIsNotTrue(boolean exp, String expLabel){
		if(exp){
			throw new DeveloperConfigErrorException(expLabel + " can't be true"); 
		}
	}
	
	/**
	 * 抛出不能为空异常 
	 * @param paramNm 变量名称
	 * @param tips 错误提示
	 */
	protected static RuntimeException newNotNullExeption(String paramNm, String tips){
		StringBuilder info = new StringBuilder(paramNm).append(" can't be null");
		if(StringUtils.isEmpty(tips)){
			info.append("!");
		}else{
			info.append(", ").append(tips);
		}
		return new DeveloperConfigErrorException(info.toString());
	}

}
