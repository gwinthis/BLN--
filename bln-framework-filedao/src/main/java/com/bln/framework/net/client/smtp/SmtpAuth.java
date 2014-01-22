/**
 * @author Gengw
 * Created At 2008-03-11
 */
package com.bln.framework.net.client.smtp;

/**
 * SMTP服务器验证类
 */
public class SmtpAuth extends javax.mail.Authenticator {
	private String username;
	private String password;

	public SmtpAuth(String username, String password) {
		this.username = username;
		this.password = password;
	}

	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		return new javax.mail.PasswordAuthentication(username, password);
	}
}