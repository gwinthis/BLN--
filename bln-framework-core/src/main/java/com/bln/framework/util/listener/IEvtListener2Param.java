/**
 * @author gengw
 * Created at 2013-09-26
 */
package com.bln.framework.util.listener;

/**
 * �������¼�������
 */
public interface IEvtListener2Param<T, T2> {
	
	/**
	 * �����¼�
	 * @param param
	 * @param param2
	 */
	public void fire(T param, T2 param2);
	
}
