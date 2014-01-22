package com.bln.framework.file.dao;

import java.io.IOException;

public interface IFileDao {

	/**
	 * 读取文件
	 * @param fileUrl 文件路径
	 * @return byte[] 文件内容,按字节数组返回。
	 */
	public abstract byte[] readFile(String fileUrl) throws IOException;

	/**
	 * 写入文件
	 * @param fileUrl 文件路径
	 * @param fileData 文件内容
	 * @throws IOException
	 */
	public abstract void writeFile(String fileUrl, byte[] fileData)
			throws IOException;

}