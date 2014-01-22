/**
 * @author gengw
 * Created at 2013-07-21
 */
package com.bln.framework.config;

/**
 *  Ӧ�����ö���
 */
public class AppConfig implements IAppConfig {

	/**
	 * �������ʹ�õ����ڸ�ʽ
	 */
	String dateFormat = null;
	
	/**
	 * �������ʹ�õ�����ʱ���ʽ
	 */
	String datetimeFormat = null;
		
	/* (non-Javadoc)
	 * @see com.bln.framework.app.IApplication#getDateFormat()
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.app.IApplication#setDateFormat(java.lang.String)
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.app.IApplication#getDatetimeFormat()
	 */
	public String getDatetimeFormat() {
		return datetimeFormat;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.app.IApplication#setDatetimeFormat(java.lang.String)
	 */
	public void setDatetimeFormat(String datetimeFormat) {
		this.datetimeFormat = datetimeFormat;
	}
}
