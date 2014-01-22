/**
 * @author gengw
 * Created on Mar 31, 2012
 */
package com.bln.framework.edi.edge.filter.compress;

import java.io.IOException;

import com.bln.framework.edi.edge.filter.EdiFilterBase;

/**
 * 解压EDI数据过滤器
 */
public class DecompressEdiFilter extends EdiFilterBase{
		
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.filter.IEdiFilter#filter(byte[])
	 */
	public byte[] filter(byte[] ediData) throws IOException {
		return compressor.decompress(ediData);
	}

}
