/**
 * @author gengw
 * Craeted on 2012-03-15
 */
package com.bln.framework.factory;

/**
 * �����ӿ�
 */
public interface  IFactory <T>{
	
	/**
	 * ���ݶ������ƻ�ö���
	 * @param objNm ��������
	 * @return ����ʵ�ʵĶ���
	 */
	public T getInstance(String objNm);

	/**
	 * ��÷ǿյ�ʵ���������ÿ�ʵ�����׳��쳣
	 * @param objNm ��������
	 * @return ����ʵ�ʵĶ���
	 */
	public T getNotNullInstance(String objNm);

}
