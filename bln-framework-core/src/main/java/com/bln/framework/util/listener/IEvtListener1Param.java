/**
 * @author gengw
 * Created at 2013-09-26
 */
package com.bln.framework.util.listener;

/**
 * 单参数事件监听器
 */
public interface IEvtListener1Param<T> {

	/**
	 * 触发事件
	 * @param param 事件参数
	 */
	public void fire(T param);

}
