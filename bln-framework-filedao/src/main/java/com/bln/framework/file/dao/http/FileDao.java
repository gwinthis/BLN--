/**
 * @author gengw
 * Created on Jan 21, 2013
 */
package com.bln.framework.file.dao.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bln.framework.file.dao.FileDaoBase;
import com.bln.framework.file.dao.IFileDao;
import com.bln.framework.net.client.http.IHttpClient;

/**
 * �ļ�ϵͳ-�ļ����ʶ���
 */
public class FileDao extends FileDaoBase implements IFileDao {
	
	/**
	 * HTTP�ͻ���
	 */
	IHttpClient httpClient = null;
	
	/**
	 * ���ļ�ʱʹ��get����
	 */
	boolean readFileUseGetMethod = false;
	
	/**
	 * �ļ�·����������
	 */
	String fileUrlField = "fileUrl";
	
	/**
	 * �ļ����ݲ�������
	 */
	String fileDataField = "fileData";
	
	/* (non-Javadoc)
	 * @see com.bln.framework.file.dao.IFileDao#readFile(java.lang.String)
	 */
	public byte[] readFile(String fileUrl)throws IOException{
		
		fileUrl = this.checkFileUrl(fileUrl);
		
		Map<String, Object> params = new HashMap<String, Object>(1);
		params.put(fileUrlField, fileUrl);
		
		byte[] fileData = null;
		if(readFileUseGetMethod){
			fileData = httpClient.get(params);
		}else{
			fileData = httpClient.postParams(params);
		}
		
		return fileData;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.file.dao.IFileDao#writeFile(java.lang.String, byte[])
	 */
	public void writeFile(String fileUrl, byte[] fileData) throws IOException{
		
		fileUrl = this.checkFileUrl(fileUrl);
		
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put(fileUrlField, fileUrl);
		params.put(fileDataField, fileData);
		
		httpClient.postParams(params);
	}

	/**
	 * @return the httpClient
	 */
	public IHttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * @param httpClient the httpClient to set
	 */
	public void setHttpClient(IHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * @return the fileUrlField
	 */
	public String getFileUrlField() {
		return fileUrlField;
	}

	/**
	 * @param fileUrlField the fileUrlField to set
	 */
	public void setFileUrlField(String fileUrlField) {
		this.fileUrlField = fileUrlField;
	}

	/**
	 * @return the fileDataField
	 */
	public String getFileDataField() {
		return fileDataField;
	}

	/**
	 * @param fileDataField the fileDataField to set
	 */
	public void setFileDataField(String fileDataField) {
		this.fileDataField = fileDataField;
	}

}
