package com.bln.framework.net.client.ftp;

import java.io.IOException;

public interface IFtpClient{

	/**
	 * ׼�����ͣ�������Դ
	 * @throws Throwable ׼������ʱ���׳����쳣
	 */
	public void ready() throws Throwable;
		
	/**
	 * �������ͣ��ͷ���Դ
	 * @throws Throwable
	 */
	public void dispose() throws Throwable;
	
	/**
	 * �ڵ�ǰ�Ĺ���·�����ϴ�ָ�����ļ�
	 * @param fileNm Ҫ�ϴ����ļ���
	 * @param data �ļ����ݰ��ֽ�������ʽ�洢
	 * @throws IOException �ϴ��ļ�ʱ���׳����쳣
	 */
	public abstract void uploadFile(String fileNm, byte[] data)
			throws IOException;

	/**
	 * �ڵ�ǰ�Ĺ���·��������ָ�����ļ�
	 * @param fileNm Ҫ���ص��ļ���
	 * @return �ļ����ݣ����ֽ�������ʽ����
	 * @throws IOException ���ش��ļ�ʱ���׳����쳣
	 */
	public abstract byte[] downloadFile(String fileNm) throws IOException;

	/**
	 * ��ȡ��ǰ����·���µ��ļ��б�
	 * @return ��ǰ����·���µ��ļ��б�
	 * @throws IOException ��ȡ�ļ��ļ��б�ʱ���׳����쳣
	 */
	public abstract String[] getList() throws IOException;

	/**
	 * ɾ����ǰ����·���µ��ļ�
	 * @param fileNm Ҫɾ�����ļ�����
	 * @throws IOException ɾ���ļ�ʱ���׳����쳣
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