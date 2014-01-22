/**
 * @author gengw
 * Created on 2012-03-13
 */
package com.bln.framework.filestru;

import com.bln.framework.base.BaseObj;

/**
 * EDI���Ľṹ����
 */
public abstract class FileStruBase extends BaseObj implements IFileStru{
	
	/**
	 * �ļ�ԭʼ����
	 */
	protected byte[] fileData = null;
	
	/**
	 * �ַ�������
	 */
	protected String encoding = "";

	/**
	 * ����ļ���ԭʼ����
	 * @return
	 */
	public byte[] getFileData() {
		return fileData;
	}

	/**
	 * ����ļ��ı��뷽ʽ
	 * @return
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * �����ļ��ı��뷽ʽ
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * ��ʼ���ļ��ṹ
	 */
	protected abstract void initStru();

}
