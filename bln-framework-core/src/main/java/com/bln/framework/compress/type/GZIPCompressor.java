/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.compress.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

import com.bln.framework.compress.ICompressor;
import com.bln.framework.util.asserter.Assert;

/**
 * GZIP的压缩格式处理
 */
public class GZIPCompressor implements ICompressor{

	/* (non-Javadoc)
	 * @see com.bln.framework.compress.ICompresser#compress(byte[])
	 */
	public byte[] compress(byte[] data) throws IOException {
		
		//校验参数
		Assert.paramIsNotNull(data, "data");
		
		//定义输出流
		ByteArrayOutputStream bosResult = new ByteArrayOutputStream();
		
		//添加到压缩流中
		GZIPOutputStream dos = new GZIPOutputStream(bosResult);
		dos.write(data);
		
		//结束添加
		dos.finish();
		
		dos.flush();
		dos.close();
		
		//返回压缩后的数据
		return bosResult.toByteArray();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.compress.ICompresser#decompress(byte[])
	 */
	public byte[] decompress(byte[] reqData) throws IOException {
		
		//校验参数
		Assert.paramIsNotNull(reqData, "data");
		
		//定义输入流
		ByteArrayInputStream bisResult = new ByteArrayInputStream(reqData);
		
		//添加到解压流中
		GZIPInputStream iis = new GZIPInputStream(bisResult);

		//获得解压后的字节数组
		byte[] respData = IOUtils.toByteArray(iis);
		
		//结束解压
		iis.close();
		
		//返回解压缩后的数据
		return respData;
	}

}
