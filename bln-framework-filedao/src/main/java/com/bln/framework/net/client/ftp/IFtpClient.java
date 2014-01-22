package com.bln.framework.net.client.ftp;

import java.io.IOException;

public interface IFtpClient{

	/**
	 * 准备发送，请求资源
	 * @throws Throwable 准备发送时所抛出的异常
	 */
	public void ready() throws Throwable;
		
	/**
	 * 结束发送，释放资源
	 * @throws Throwable
	 */
	public void dispose() throws Throwable;
	
	/**
	 * 在当前的工作路径中上传指定的文件
	 * @param fileNm 要上传的文件名
	 * @param data 文件数据按字节数组形式存储
	 * @throws IOException 上传文件时所抛出的异常
	 */
	public abstract void uploadFile(String fileNm, byte[] data)
			throws IOException;

	/**
	 * 在当前的工作路径中下载指定的文件
	 * @param fileNm 要下载的文件名
	 * @return 文件内容，按字节数组形式下载
	 * @throws IOException 下载传文件时所抛出的异常
	 */
	public abstract byte[] downloadFile(String fileNm) throws IOException;

	/**
	 * 获取当前工作路径下的文件列表
	 * @return 当前工作路径下的文件列表
	 * @throws IOException 获取文件文件列表时所抛出的异常
	 */
	public abstract String[] getList() throws IOException;

	/**
	 * 删除当前工作路径下的文件
	 * @param fileNm 要删除的文件名称
	 * @throws IOException 删除文件时所抛出的异常
	 */
	public abstract void deleteFile(String fileNm) throws IOException;

	/**
	 * @return the hostUrl
	 */
	public String getHostUrl();

	/**
	 * @param hostUrl the hostUrl to set
	 */
	public void setHostUrl(String hostUrl);

	/**
	 * @return the port
	 */
	public String getPort();

	/**
	 * @param port the port to set
	 */
	public void setPort(String port);

	/**
	 * @return the userId
	 */
	public String getUserId();

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId);

	/**
	 * @return the password
	 */
	public String getPassword();

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password);

	/**
	 * @return the isPassiveMode
	 */
	public boolean isPassiveMode();

	/**
	 * @param isPassiveMode the isPassiveMode to set
	 */
	public void setPassiveMode(boolean isPassiveMode);

	/**
	 * @return the connTimeout
	 */
	public int getConnTimeout();

	/**
	 * @param connTimeout the connTimeout to set
	 */
	public void setConnTimeout(int connTimeout);

	/**
	 * @return the soTimeout
	 */
	public int getSoTimeout();

	/**
	 * @param soTimeout the soTimeout to set
	 */
	public void setSoTimeout(int soTimeout);

	/**
	 * @return the workPath
	 */
	public String getWorkPath();

	/**
	 * @param workPath the workPath to set
	 */
	public void setWorkPath(String workPath);

}