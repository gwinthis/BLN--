/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.compress;

import java.io.IOException;

/**
 * ѹ������ӿ�
 */
public interface ICompressor {
	
	/**
	 * ѹ������
	 * @param data Ҫѹ�������ݣ��ֽ��������ʽ
	 * @return ��ѹ�������ݡ�
	 */
	public byte[] compress(byte[] data)throws IOException;
	
	/**
	 * ��ѹ����
	 * @param reqData Ҫ��ѹ�����ݣ��ֽ��������ʽ
	 * @return �ѽ�ѹ�����ݡ�
	 */
	public byte[] decompress(byte[] reqData)throws IOException;

}
