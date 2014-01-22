/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.builder;

/**
 * 
 * 对象构造器
 * @param <T> 要生成对象的类型
 */
public interface IBuilder<T>  {
	
	/**
	 * 生成指定类型的对象
	 * @return 指定类型的对象
	 */
	public T build();
}
