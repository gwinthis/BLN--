/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.jdbc.converter;


import com.bln.framework.persist.jdbc.converter.ConverterBase;
import com.bln.framework.persist.jdbc.converter.IConverter;

/**
 * 日期类型转换器基类
 */
public abstract class DateConverterBase extends ConverterBase implements IConverter{
	
	/**
	 * 日期格式
	 */
	protected String[] dateFormats = null;
	
	/**
	 * @return the dateFormats
	 */
	public String[] getDateFormats() {
		return dateFormats;
	}

	/**
	 * @param dateFormats the dateFormats to set
	 */
	public void setDateFormats(String[] dateFormats) {
		this.dateFormats = dateFormats;
	}

}
