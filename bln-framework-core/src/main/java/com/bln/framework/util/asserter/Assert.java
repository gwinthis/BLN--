/**
 * @author gengw
 * Created on 2012-03-12
 */
package com.bln.framework.util.asserter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.bln.framework.exception.DeveloperConfigErrorException;

/**
 * ������
 */
public class Assert extends Validate{
	
	/**
	 * <p>���Բ�������Ϊ��</p>
	 * �ַ����жϲ���null,����Ϊ""
	 * @param var ҪУ��ı���
	 * @param paramNm ��������
	 */
	public static void paramIsNotNull(String param, String paramNm){
		paramIsNotNull(param, paramNm, null);
	}
	
	/**
	 * <p>���Բ�������Ϊ��</p>
	 * �ַ����жϲ���null,����Ϊ""
	 * @param var ҪУ��ı���
	 * @param paramNm ��������
	 * @param tips ������ʾ
	 */
	public static void paramIsNotNull(String param, String paramNm, String tips){
		if(StringUtils.isEmpty(param)){
			throw newNotNullExeption(paramNm, tips);
		}
	}
	
	/**
	 * <p>���Բ�������Ϊ��</p>
	 * �����жϲ���null�����ҳ���Ҫ������
	 * @param var ҪУ��ı���
	 * @param paramNm ��������
	 */
	public static <T> void paramIsNotNull(T[] param, String paramNm){
		paramIsNotNull(param, paramNm, null);
	}
	
	/**
	 * <p>���Բ�������Ϊ��</p>
	 * �����жϲ���null�����ҳ���Ҫ������
	 * @param var ҪУ��ı���
	 * @param paramNm ��������
	 * @param tips ������ʾ
	 */
	public static <T> void paramIsNotNull(T[] param, String paramNm, String tips){
		if(param == null || param.length == 0){
			throw newNotNullExeption(paramNm, tips);
		}
	}
	
	/**
	 * <p>���Բ�������Ϊ��</p>
	 * @param <T> ��������
	 * @param var ҪУ��ı���
	 * @param paramNm ��������
	 */
	public static <T> void paramIsNotNull(T var, String paramNm){
		paramIsNotNull(var, paramNm, null);
	}
	
	/**
	 * <p>���Բ�������Ϊ��</p>
	 * @param <T> ��������
	 * @param var ҪУ��ı���
	 * @param paramNm ��������
	 * @param tips ������ʾ
	 */
	public static <T> void paramIsNotNull(T var, String paramNm, String tips){
		if(var == null){
			throw newNotNullExeption(paramNm, tips);
		}
	}
	
	/**
	 * ���ʽ����Ϊ�棬���Ϊ���׳��쳣
	 * @param exp ���ʽ���
	 * @param expLabel ���ʽlabel
	 */
	public static void expressionIsNotTrue(boolean exp, String expLabel){
		if(exp){
			throw new DeveloperConfigErrorException(expLabel + " can't be true"); 
		}
	}
	
	/**
	 * �׳�����Ϊ���쳣 
	 * @param paramNm ��������
	 * @param tips ������ʾ
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
