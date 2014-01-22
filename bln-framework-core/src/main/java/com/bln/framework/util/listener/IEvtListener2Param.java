/**
 * @author gengw
 * Created at 2013-09-26
 */
package com.bln.framework.util.listener;

/**
 * 单参数事件监听器
 */
public interface IEvtListener2Param<T, T2> {
	
	/**
	 * 触发事件
	 * @param param
	 * @param param2
	 */
	public void fire(T param, T2 param2);
	
}
