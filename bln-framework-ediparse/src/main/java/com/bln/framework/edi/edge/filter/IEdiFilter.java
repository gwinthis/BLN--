/**
 * @author gengw
 * Created on Mar 31, 2012
 */
package com.bln.framework.edi.edge.filter;

import java.io.IOException;

/**
 * 报文数据过滤器
 */
public interface IEdiFilter {
	
	/**
	 * 数据过滤器
	 * @param ediData
	 * @return
	 */
	public byte[] filter(byte[] ediData)throws IOException;

}
