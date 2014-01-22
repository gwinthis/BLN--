/**
 * @author gengw
 * Created on 2012-03-14
 */
package com.bln.framework.filestru;

import java.io.IOException;

import com.bln.framework.filestru.exception.ParseFileException;

/**
 * ���Ľṹ�ӿ�
 */
public interface IFileStru {

	/**
	 * ��ñ��ĵ�ԭʼ����
	 * @return
	 */
	public byte[] getFileData();

	/**
	 * ��ñ��ĵı��뷽ʽ
	 * @return
	 */
	public String getEncoding();

	/**
	 * ���ñ��ĵı��뷽ʽ
	 * @param encoding
	 */
	public void setEncoding(String encoding);
	
	/**
	 * ���ñ������ݣ��������ɱ��Ķ���
	 * @param ����<code>byte[]</code>���ò���ΪҪ���ɱ��Ķ����ԭʼ����
	 * @throws Throwable if an error occurs during parsing
	 */
	public void readFromData(byte[] fileData) throws ParseFileException;
	
	/**
	 * ��Document������ֽ�������
	 * @return Xml Document��������ֽ�����
	 * @throws Throwable ���ʱ���׳����쳣
	 */
	public byte[] writeToBytes() throws IOException;
	
	/**
	 * ��ʼ���ļ�����
	 */
	public void initFile();


}
