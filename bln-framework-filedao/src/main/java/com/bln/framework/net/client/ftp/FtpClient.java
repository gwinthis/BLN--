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
 * FTP客户端基类
 */
public class FtpClient extends BaseObj implements IFtpClient{

	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(FtpClient.class);

	
	/**
	 * apache的Ftp客户端
	 */
	protected org.apache.commons.net.ftp.FTPClient client = null;
		
	/**
	 * 主机地址
	 */
	protected String hostUrl = null;
	
	/**
	 * 端口号
	 */
	protected String port = null;
	
	/**
	 * 用户名
	 */
	protected String userId = null;

	/**
	 * 密码
	 */
	protected String password = null;

	/**
	 * 被动模式
	 */
	protected boolean isPassiveMode = true;

	/**
	 * 连接时超时时间
	 */
	protected int connTimeout = 10000;

	/**
	 * 接收数据时的超时时间
	 */
	protected int soTimeout = 10000;
	
	/**
	 * 工作路径
	 */
	protected String workPath = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.INetClient#readyRequest()
	 */
	public void ready() throws Throwable {
		
		//登录服务器
		login();
		
		//设置工作路径
		client.changeWorkingDirectory(workPath);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.net.client.INetClient#dispose()
	 */
	public void dispose() throws Throwable{
		logout();
	}
	
	/**
	 * 登录服务器
	 * @throws SocketException
	 * @throws IOException
	 */
	protected void login() throws SocketException, IOException{
		
		//定义超时时间
		client.setDefaultTimeout(connTimeout);
		client.setDataTimeout(connTimeout);
		
		//连接FTP服务器
		client.connect(hostUrl);
		log.debug("ftp host connected....");
		
		int reply = client.getReplyCode();
		if(!FTPReply.isPositiveCompletion(reply)) {
			client.disconnect();
			log.debug("FTP server refused connection.");
	    }

		client.setSoTimeout(soTimeout);
		
		//登录
		client.login(userId, password);
		log.debug("ftp logined....");
		
		//设置FTP文件类型
		client.setFileType(org.apache.commons.net.ftp.FTP.ASCII_FILE_TYPE);
		
		//设置被动模式
		if(this.isPassiveMode){
			client.enterLocalPassiveMode();
		}			
	}
	
	/**
	 * 登出服务器
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	protected void logout() throws IllegalStateException, IOException{

		//登出
		client.logout();
		
		//取消链接
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
