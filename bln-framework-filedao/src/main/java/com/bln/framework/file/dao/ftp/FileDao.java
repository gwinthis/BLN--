/**
 * @author gengw
 * Created on Jan 21, 2013
 */
package com.bln.framework.file.dao.ftp;

import java.io.IOException;

import com.bln.framework.file.dao.FileDaoBase;
import com.bln.framework.file.dao.IFileDao;
import com.bln.framework.net.client.ftp.IFtpClient;

/**
 * 文件系统-文件访问对象
 */
public class FileDao extends FileDaoBase implements IFileDao {
	
	/**
	 * FTP客户端
	 */
	IFtpClient ftpClient = null;
		
	/* (non-Javadoc)
	 * @see com.bln.framework.file.dao.IFileDao#readFile(java.lang.String)
	 */
	public byte[] readFile(String fileUrl)throws IOException{
		
		fileUrl = this.checkFileUrl(fileUrl);
		
		byte[] fileData = ftpClient.downloadFile(fileUrl);
		return fileData;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.file.dao.IFileDao#writeFile(java.lang.String, byte[])
	 */
	public void writeFile(String fileUrl, byte[] fileData) throws IOException{
		
		fileUrl = this.checkFileUrl(fileUrl);
		ftpClient.uploadFile(fileUrl, fileData);
	}

	/**
	 * @return the ftpClient
	 */
	public IFtpClient getFtpClient() {
		return ftpClient;
	}

	/**
	 * @param ftpClient the ftpClient to set
	 */
	public void setFtpClient(IFtpClient ftpClient) {
		this.ftpClient = ftpClient;
	}

}
