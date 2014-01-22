/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.strategy;

/**
 * 策略接口
 * @param <T> 返回值的类型
 * @param <P> 参数值的类型
 */
public interface IStrategy<T, P> {
	
	public T proecess(P p);

}
