/**
 * @author gengw
 * Craeted on 2012-03-15
 */
package com.bln.framework.factory;

/**
 * 工厂接口
 */
public interface  IFactory <T>{
	
	/**
	 * 根据对象名称获得对象
	 * @param objNm 对象名称
	 * @return 返回实际的对象
	 */
	public T getInstance(String objNm);

	/**
	 * 获得非空的实例，如果获得空实例将抛出异常
	 * @param objNm 对象名称
	 * @return 返回实际的对象
	 */
	public T getNotNullInstance(String objNm);

}
