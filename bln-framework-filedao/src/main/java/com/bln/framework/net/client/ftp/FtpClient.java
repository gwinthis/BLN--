/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.net.client.ftp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPReply;

import com.bln.framework.base.BaseObj;

/**
 * FTP�ͻ��˻���
 */
public class FtpClient extends BaseObj implements IFtpClient{

	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(FtpClient.class);

	
	/**
	 * apache��Ftp�ͻ���
	 */
	protected org.apache.commons.net.ftp.FTPClient client = null;
		
	/**
	 * ������ַ
	 */
	protected String hostUrl = null;
	
	/**
	 * �˿ں�
	 */
	protected String port = null;
	
	/**
	 * �û���
	 */
	protected String userId = null;

	/**
	 * ����
	 */
	protected String password = null;

	/**
	 * ����ģʽ
	 */
	protected boolean isPassiveMode = true;

	/**
	 * ����ʱ��ʱʱ��
	 */
	protected int connTimeout = 10000;

	/**
	 * ��������ʱ�ĳ�ʱʱ��
	 */
	protected int soTimeout = 10000;
	
	/**
	 * ����·��
	 */
	protected String workPath = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.INetClient#readyRequest()
	 */
	public void ready() throws Throwable {
		
		//��¼������
		login();
		
		//���ù���·��
		client.changeWorkingDirectory(workPath);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.INetClient#dispose()
	 */
	public void dispose() throws Throwable{
		logout();
	}
	
	/**
	 * ��¼������
	 * @throws SocketException
	 * @throws IOException
	 */
	protected void login() throws SocketException, IOException{
		
		//���峬ʱʱ��
		client.setDefaultTimeout(connTimeout);
		client.setDataTimeout(connTimeout);
		
		//����FTP������
		client.connect(hostUrl);
		log.debug("ftp host connected....");
		
		int reply = client.getReplyCode();
		if(!FTPReply.isPositiveCompletion(reply)) {
			client.disconnect();
			log.debug("FTP server refused connection.");
	    }

		client.setSoTimeout(soTimeout);
		
		//��¼
		client.login(userId, password);
		log.debug("ftp logined....");
		
		//����FTP�ļ�����
		client.setFileType(org.apache.commons.net.ftp.FTP.ASCII_FILE_TYPE);
		
		//���ñ���ģʽ
		if(this.isPassiveMode){
			client.enterLocalPassiveMode();
		}			
	}
	
	/**
	 * �ǳ�������
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	protected void logout() throws IllegalStateException, IOException{

		//�ǳ�
		client.logout();
		
		//ȡ������
		client.disconnect();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.ftp.IFtpClient#uploadFile(java.lang.String, byte[])
	 */
	public void uploadFile(String fileNm, byte[] data)throws IOException{
		
		ByteArrayInputStream bais = new ByteArrayInputStream(data); 
		try {
			client.storeFile(fileNm, bais);
		}finally{
			bais.close();
			bais = null;		
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.ftp.IFtpClient#downloadFile(java.lang.String)
	 */
	public byte[] downloadFile(String fileNm) throws IOException{
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			
			client.retrieveFile(fileNm, baos);
			byte[] bytes = baos.toByteArray();
			
			return bytes;
			
		}finally{
			baos.close();
			baos = null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.ftp.IFtpClient#getList()
	 */
	public String[] getList() throws IOException{
		String[] fileList = client.listNames();
		return fileList;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.ftp.IFtpClient#deleteFile(java.lang.String)
	 */
	public void deleteFile(String fileNm) throws IOException{
		this.client.deleteFile(fileNm);
	}

	/**
	 * @return the hostUrl
	 */
	public String getHostUrl() {
		return hostUrl;
	}

	/**
	 * @param hostUrl the hostUrl to set
	 */
	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the isPassiveMode
	 */
	public boolean isPassiveMode() {
		return isPassiveMode;
	}

	/**
	 * @param isPassiveMode the isPassiveMode to set
	 */
	public void setPassiveMode(boolean isPassiveMode) {
		this.isPassiveMode = isPassiveMode;
	}

	/**
	 * @return the connTimeout
	 */
	public int getConnTimeout() {
		return connTimeout;
	}

	/**
	 * @param connTimeout the connTimeout to set
	 */
	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}

	/**
	 * @return the soTimeout
	 */
	public int getSoTimeout() {
		return soTimeout;
	}

	/**
	 * @param soTimeout the soTimeout to set
	 */
	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	/**
	 * @return the workPath
	 */
	public String getWorkPath() {
		return workPath;
	}

	/**
	 * @param workPath the workPath to set
	 */
	public void setWorkPath(String workPath) {
		this.workPath = workPath;
	}
}
