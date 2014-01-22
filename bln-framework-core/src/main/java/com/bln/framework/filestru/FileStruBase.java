/**
 * @author gengw
 * Created on 2012-03-13
 */
package com.bln.framework.filestru;

import com.bln.framework.base.BaseObj;

/**
 * EDI报文结构基类
 */
public abstract class FileStruBase extends BaseObj implements IFileStru{
	
	/**
	 * 文件原始数据
	 */
	protected byte[] fileData = null;
	
	/**
	 * 字符集编码
	 */
	protected String encoding = "";

	/**
	 * 获得文件的原始数据
	 * @return
	 */
	public byte[] getFileData() {
		return fileData;
	}

	/**
	 * 获得文件的编码方式
	 * @return
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 设置文件的编码方式
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * 初始化文件结构
	 */
	protected abstract void initStru();

}
