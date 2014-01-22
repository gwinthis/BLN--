/**
 * @author gengw(gengw@17guagua.com)
 * @version 2013-8-3 下午4:25:29
 */
package com.bln.framework.edi.ipextractor;

/**
 * IP提取器
 * 
 */
public interface IExtractor4IP<T> {

	/**
	 * 从reqeust对象提取IP
	 * @param reqeust，从该对象中获取IP
	 * @return IP
	 * @throws Throwable
	 */
	public String extract(T request);
}
