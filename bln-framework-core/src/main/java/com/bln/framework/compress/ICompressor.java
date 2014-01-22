/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.compress;

import java.io.IOException;

/**
 * 压缩处理接口
 */
public interface ICompressor {
	
	/**
	 * 压缩数据
	 * @param data 要压缩的数据，字节数组的形式
	 * @return 已压缩的数据。
	 */
	public byte[] compress(byte[] data)throws IOException;
	
	/**
	 * 解压数据
	 * @param reqData 要解压的数据，字节数组的形式
	 * @return 已解压的数据。
	 */
	public byte[] decompress(byte[] reqData)throws IOException;

}
