/**
 * @author gengw
 * Created on Apr 12, 2012
 */
package com.bln.framework.builder;

/**
 * 只需要一个步骤即可生成对象
 * @param <T>要生成对象的类型
 * @param <P> 构造对象所需的参数的类型
 */
public interface IBuilder1Step <T, P>{
	
	/**
	 * 生成指定类型的对象
	 * @param p 构造对象所需的参数
	 * @return 指定类型的对象
	 */
	public T build(P p);

}
