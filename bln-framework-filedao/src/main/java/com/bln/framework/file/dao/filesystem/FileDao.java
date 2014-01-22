/**
 * @author gengw
 * Created on Jan 21, 2013
 */
package com.bln.framework.file.dao.filesystem;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.bln.framework.file.dao.FileDaoBase;
import com.bln.framework.file.dao.IFileDao;

/**
 * 文件系统-文件访问对象
 */
public class FileDao extends FileDaoBase implements IFileDao {
	

	/* (non-Javadoc)
	 * @see com.bln.framework.file.dao.IFileDao#readFile(java.lang.String)
	 */
	public byte[] readFile(String fileUrl)throws IOException{
	
		fileUrl = this.checkFileUrl(fileUrl);
		
		byte[] fileData = FileUtils.readFileToByteArray(new File(fileUrl));
		return fileData;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.file.dao.IFileDao#writeFile(java.lang.String, byte[])
	 */
	public void writeFile(String fileUrl, byte[] fileData) throws IOException{
		
		fileUrl = this.checkFileUrl(fileUrl);
		FileUtils.writeByteArrayToFile(new File(fileUrl), fileData, false);
		
	}

}
