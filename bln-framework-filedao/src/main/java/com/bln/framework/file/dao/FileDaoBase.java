/**
 * @author gengw
 * Created on Jan 21, 2013
 */
package com.bln.framework.file.dao;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.util.asserter.Assert;

/**
 * �ļ����ʶ������
 */
public abstract class FileDaoBase implements IFileDao{
	
	/**
	 * �ļ�·��ǰ׺
	 */
	protected String fileUrlPrefix = null;
	
	/**
	 * ����ļ�·���������ǰ׺
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
