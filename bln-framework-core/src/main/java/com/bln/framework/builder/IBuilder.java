/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.builder;

/**
 * 
 * ��������
 * @param <T> Ҫ���ɶ��������
 */
public interface IBuilder<T>  {
	
	/**
	 * ����ָ�����͵Ķ���
	 * @return ָ�����͵Ķ���
	 */
	public T build();
}
