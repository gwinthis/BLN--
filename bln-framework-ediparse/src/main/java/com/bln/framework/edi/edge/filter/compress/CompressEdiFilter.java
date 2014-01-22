/**
 * @author gengw
 * Created on Mar 31, 2012
 */
package com.bln.framework.edi.edge.filter.compress;

import java.io.IOException;

import com.bln.framework.edi.edge.filter.EdiFilterBase;

/**
 * ѹ��EDI���ݹ�����
 */
public class CompressEdiFilter extends EdiFilterBase{
		
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.filter.IEdiFilter#filter(byte[])
	 */
	public byte[] filter(byte[] ediData) throws IOException {
		return compressor.compress(ediData);
	}

}
