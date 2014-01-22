/**
 * @author gengw
 * Created on 2012-03-14
 */
package com.bln.framework.filestru;

import java.io.IOException;

import com.bln.framework.filestru.exception.ParseFileException;

/**
 * 报文结构接口
 */
public interface IFileStru {

	/**
	 * 获得报文的原始数据
	 * @return
	 */
	public byte[] getFileData();

	/**
	 * 获得报文的编码方式
	 * @return
	 */
	public String getEncoding();

	/**
	 * 设置报文的编码方式
	 * @param encoding
	 */
	public void setEncoding(String encoding);
	
	/**
	 * 设置报文数据，并解析成报文对象
	 * @param 设置<code>byte[]</code>，该参数为要生成报文对象的原始数据
	 * @throws Throwable if an error occurs during parsing
	 */
	public void readFromData(byte[] fileData) throws ParseFileException;
	
	/**
	 * 把Document输出到字节数组中
	 * @return Xml Document所输出的字节数组
	 * @throws Throwable 输出时所抛出的异常
	 */
	public byte[] writeToBytes() throws IOException;
	
	/**
	 * 初始化文件对象
	 */
	public void initFile();


}
