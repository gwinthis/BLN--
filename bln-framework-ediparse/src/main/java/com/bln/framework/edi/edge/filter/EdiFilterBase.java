/**
 * @author gengw
 * Created on Mar 31, 2012
 */
package com.bln.framework.edi.edge.filter;

import com.bln.framework.compress.ICompressor;

/**
 * EDI���ݹ���������
 */
public abstract class EdiFilterBase implements IEdiFilter{
	
	/**
	 * ѹ����ѹ��
	 */
	protected ICompressor compressor = null;

	/**
	 * @return the compressor
	 */
	public ICompressor getCompressor() {
		return compressor;
	}

	/**
	 * @param compressor the compressor to set
	 */
	public void setCompressor(ICompressor compressor) {
		this.compressor = compressor;
	}


}
