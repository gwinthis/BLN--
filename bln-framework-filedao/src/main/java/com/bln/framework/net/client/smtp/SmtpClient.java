/**
 * @author Gengw
 * Created at 2008-03-11
 */
package com.bln.framework.net.client.smtp;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.net.client.exception.NetSendException;

/**
 * 
 * 发送邮件
 */
public class SmtpClient{

	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(SmtpClient.class);
	
	/**
	 * 显示发送用户名
	 */
	private String displayName;

	/**
	 * 目的邮件地址
	 */
	private String to;

	/**
	 * 发送者邮件地址
	 */
	private String from;

	/**
	 * SMTP服务器地址
	 */
	private String smtpServer;


	/**
	 *  服务器是否要身份认证
	 */
	private boolean ifAuth;

	/**
	 * 认证的用户名
	 */
	private String userName;

	/**
	 * 认证的密码
	 */
	private String password;

	/**
	 * 字符编码
	 */
	private String encoding = "UTF-8";

	/**
	 * 发送邮件
	 * @param Sting subject(邮件主题)
	 * @param ArrayList attachments(附件路径名)
	 */
	public void send (String subject, String content, String[] attachments) throws NetSendException{
		
		//建立Session
		Session session = null;
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		if (ifAuth) { // 服务器需要身份认证
			props.put("mail.smtp.auth", "true");
			SmtpAuth smtpAuth = new SmtpAuth(userName, password);
			session = Session.getDefaultInstance(props, smtpAuth);
		} else {
			props.put("mail.smtp.auth", "false");
			session = Session.getDefaultInstance(props, null);
		}

		//session.setDebug(true);
		
		//建立TransPort
		Transport trans = null;
		try {
			
			Message msg = new MimeMessage(session);

			Address from_address = new InternetAddress(from, displayName);
			msg.setFrom(from_address);
			
			InternetAddress address[] = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			
			//主题
			msg.setSubject(subject);
			
			MimeMultipart mp = new MimeMultipart();
			
			//邮件主内容
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent(content, "text/html;charset=\"".concat(encoding).concat("\""));

			mp.addBodyPart(mbp);
			
			//添加附件
			if (attachments != null){
				String filename = null;
				
				for (int i = 0, n = attachments.length; i < n; i++){
					
					filename = attachments[i]; // 选择出每一个附件名
					FileDataSource fds = new FileDataSource(filename); // 得到数据源
					
					mbp = new MimeBodyPart();
					mbp.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
					//System.out.println(fds.getName());
					filename = MimeUtility.encodeWord(fds.getName(), encoding, null);
					mbp.setFileName(filename); // 得到文件名同样至入BodyPart
					mp.addBodyPart(mbp);
				}				
			}
			
			//Multipart加入到信件
			msg.setContent(mp);
			
			//设置信件头的发送日期
			msg.setSentDate(new Date()); 
			
			// 发送信件
			msg.saveChanges();
			trans = session.getTransport("smtp");
			
			int tryCount = 3; //尝试三次连接,如果都失败,抛出异常
			MessagingException me = null;
			while (tryCount > 0){
				try {
					trans.connect(smtpServer, userName, password);
					break;
				} catch (MessagingException e) {
					me = e;
					log.debug("connect smpt server failed !", e);
					tryCount --;
				}				
			}
			if(tryCount == 0){
				throw initNse(me);
			}
			
			if(log.isDebugEnabled()){
				log.debug("正在向".concat(this.to).concat("发送邮件......"));
			}
			
			trans.sendMessage(msg, msg.getAllRecipients());
			
			if(log.isDebugEnabled()){
				log.debug("向".concat(this.to).concat("发送邮件成功......"));
			}
			
		} catch (Throwable e) {
			throw initNse(e);
			
		}finally{
			
			if (trans != null && trans.isConnected()){
				try {
					trans.close();
				} catch (Throwable e) {
					throw initNse(e);
				}
			}
		}
	}
	
	/**
	 * 装配异常信息
	 * @param e
	 * @return
	 */
	protected NetSendException initNse(Throwable e){
		NetSendException nse = new NetSendException();
		nse.setContextValue("from", from);
		nse.setContextValue("to", to);
		nse.setContextValue("smtpServer", smtpServer);
		nse.setContextValue("userName", userName);
		nse.initCause(e);
		return nse;
	}
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the smtpServer
	 */
	public String getSmtpServer() {
		return smtpServer;
	}

	/**
	 * @param smtpServer the smtpServer to set
	 */
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return userName;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.userName = username;
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
	 * @return the ifAuth
	 */
	public boolean isIfAuth() {
		return ifAuth;
	}

	/**
	 * @param ifAuth the ifAuth to set
	 */
	public void setIfAuth(boolean ifAuth) {
		this.ifAuth = ifAuth;
	}

	/**
	 * @return the encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
