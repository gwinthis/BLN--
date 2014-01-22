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
 * �����ʼ�
 */
public class SmtpClient{

	/**
	 * ��õ�ǰ�����Log����
	 */
	private static final Log log = LogFactory.getLog(SmtpClient.class);
	
	/**
	 * ��ʾ�����û���
	 */
	private String displayName;

	/**
	 * Ŀ���ʼ���ַ
	 */
	private String to;

	/**
	 * �������ʼ���ַ
	 */
	private String from;

	/**
	 * SMTP��������ַ
	 */
	private String smtpServer;


	/**
	 *  �������Ƿ�Ҫ�����֤
	 */
	private boolean ifAuth;

	/**
	 * ��֤���û���
	 */
	private String userName;

	/**
	 * ��֤������
	 */
	private String password;

	/**
	 * �ַ�����
	 */
	private String encoding = "UTF-8";

	/**
	 * �����ʼ�
	 * @param Sting subject(�ʼ�����)
	 * @param ArrayList attachments(����·����)
	 */
	public void send (String subject, String content, String[] attachments) throws NetSendException{
		
		//����Session
		Session session = null;
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		if (ifAuth) { // ��������Ҫ�����֤
			props.put("mail.smtp.auth", "true");
			SmtpAuth smtpAuth = new SmtpAuth(userName, password);
			session = Session.getDefaultInstance(props, smtpAuth);
		} else {
			props.put("mail.smtp.auth", "false");
			session = Session.getDefaultInstance(props, null);
		}

		//session.setDebug(true);
		
		//����TransPort
		Transport trans = null;
		try {
			
			Message msg = new MimeMessage(session);

			Address from_address = new InternetAddress(from, displayName);
			msg.setFrom(from_address);
			
			InternetAddress address[] = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			
			//����
			msg.setSubject(subject);
			
			MimeMultipart mp = new MimeMultipart();
			
			//�ʼ�������
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent(content, "text/html;charset=\"".concat(encoding).concat("\""));

			mp.addBodyPart(mbp);
			
			//��Ӹ���
			if (attachments != null){
				String filename = null;
				
				for (int i = 0, n = attachments.length; i < n; i++){
					
					filename = attachments[i]; // ѡ���ÿһ��������
					FileDataSource fds = new FileDataSource(filename); // �õ�����Դ
					
					mbp = new MimeBodyPart();
					mbp.setDataHandler(new DataHandler(fds)); // �õ�������������BodyPart
					//System.out.println(fds.getName());
					filename = MimeUtility.encodeWord(fds.getName(), encoding, null);
					mbp.setFileName(filename); // �õ��ļ���ͬ������BodyPart
					mp.addBodyPart(mbp);
				}				
			}
			
			//Multipart���뵽�ż�
			msg.setContent(mp);
			
			//�����ż�ͷ�ķ�������
			msg.setSentDate(new Date()); 
			
			// �����ż�
			msg.saveChanges();
			trans = session.getTransport("smtp");
			
			int tryCount = 3; //������������,�����ʧ��,�׳��쳣
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
				log.debug("������".concat(this.to).concat("�����ʼ�......"));
			}
			
			trans.sendMessage(msg, msg.getAllRecipients());
			
			if(log.isDebugEnabled()){
				log.debug("��".concat(this.to).concat("�����ʼ��ɹ�......"));
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
	 * װ���쳣��Ϣ
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
