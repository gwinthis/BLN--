package com.bln.framework.file.dao;

import java.io.IOException;

public interface IFileDao {

	/**
	 * ��ȡ�ļ�
	 * @param fileUrl �ļ�·��
	 * @return byte[] �ļ�����,���ֽ����鷵�ء�
	 */
	public abstract byte[] readFile(String fileUrl) throws IOException;

	/**
	 * д���ļ�
	 * @param fileUrl �ļ�·��
	 * @param fileData �ļ�����
	 * @throws IOException
	 */
	public abstract void writeFile(String fileUrl, byte[] fileData)
			throws IOException;

}