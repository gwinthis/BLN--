/**
 * @author gengw
 * Created on Mar 31, 2012
 */
package com.bln.framework.edi.edge.filter;

import java.io.IOException;

/**
 * �������ݹ�����
 */
public interface IEdiFilter {
	
	/**
	 * ���ݹ�����
	 * @param ediData
	 * @return
	 */
	public byte[] filter(byte[] ediData)throws IOException;

}
