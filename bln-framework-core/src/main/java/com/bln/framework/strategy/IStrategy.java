/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.strategy;

/**
 * ���Խӿ�
 * @param <T> ����ֵ������
 * @param <P> ����ֵ������
 */
public interface IStrategy<T, P> {
	
	public T proecess(P p);

}
