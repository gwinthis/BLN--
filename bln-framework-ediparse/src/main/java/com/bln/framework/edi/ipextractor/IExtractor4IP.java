/**
 * @author gengw(gengw@17guagua.com)
 * @version 2013-8-3 ����4:25:29
 */
package com.bln.framework.edi.ipextractor;

/**
 * IP��ȡ��
 * 
 */
public interface IExtractor4IP<T> {

	/**
	 * ��reqeust������ȡIP
	 * @param reqeust���Ӹö����л�ȡIP
	 * @return IP
	 * @throws Throwable
	 */
	public String extract(T request);
}
