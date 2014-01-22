/**
 * @author gengw
 * Created at 2013-07-21
 */
package com.bln.framework.config;

/**
 *  应用配置对象
 */
public class AppConfig implements IAppConfig {

	/**
	 * 框架内所使用的日期格式
	 */
	String dateFormat = null;
	
	/**
	 * 框架内所使用的日期时间格式
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
