/**
 * @author gengw
 * Created on Jan 21, 2013
 */
package com.bln.framework.file.dao;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.util.asserter.Assert;

/**
 * 文件访问对象基类
 */
public abstract class FileDaoBase implements IFileDao{
	
	/**
	 * 文件路径前缀
	 */
	protected String fileUrlPrefix = null;
	
	/**
	 * 检查文件路径，并添加前缀
	 * @param fileUrl
	 * @return fileUrl
	 */
	protected String checkFileUrl(String fileUrl){
		Assert.paramIsNotNull(fileUrl, "fileUrl can't be empty!");
		
		if(!StringUtils.isEmpty(fileUrlPrefix)){
			fileUrl = fileUrlPrefix + fileUrl;
		}
		
		return fileUrl;
	}

	/**
	 * @return the fileUrlPrefix
	 */
	public String getFileUrlPrefix() {
		return fileUrlPrefix;
	}

	/**
	 * @param fileUrlPrefix the fileUrlPrefix to set
	 */
	public void setFileUrlPrefix(String fileUrlPrefix) {
		this.fileUrlPrefix = fileUrlPrefix;
	}
}
