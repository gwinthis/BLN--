/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.builder;

/**
 * ֻ��Ҫһ�����輴�����ɶ���
 * @param <T>Ҫ���ɶ��������
 * @param <P> �����������Ĳ���������
 */
public interface IBuilder1Step <T, P>{
	
	/**
	 * ����ָ�����͵Ķ���
	 * @param p �����������Ĳ���
	 * @return ָ�����͵Ķ���
	 */
	public T build(P p);

}
