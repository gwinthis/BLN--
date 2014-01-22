/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.jdbc.converter.factory;

import java.sql.Types;

import com.bln.framework.persist.jdbc.EJdbcType;
import com.bln.framework.persist.jdbc.converter.IConverter;
import com.bln.framework.persist.jdbc.converter.factory.ConverterFactoryBase;
import com.bln.framework.persist.jdbc.converter.factory.IConverterFactory;

/**
 * 集成的转换器工厂类
 */
public class ConverterFactoryIntegrate extends ConverterFactoryBase implements IConverterFactory{
	
	/**
	 * 字符类型的转换器
	 */
	protected IConverter charConverter = null;

	/**
	 * 数值类型的转换器
	 */
	protected IConverter numberConverter = null;

	/**
	 * 日期类型的转换器
	 */
	protected IConverter dateConverter = null;

	/**
	 * 时间戳类型的转换器
	 */
	protected IConverter timestampConverter = null;
	
	/**
	 * blob类型的转换器
	 */
	protected IConverter blobConverter = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.factory.oracle.IConverterFactory#getInstance(int)
	 */
	public IConverter getInstance(int type) {
		
		IConverter converter = null;
		
		switch(type){
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
		case Types.CLOB:
			converter = charConverter;
			break;
		case Types.BIT:
		case Types.TINYINT:
		case Types.SMALLINT:
		case Types.INTEGER:
		case Types.BIGINT:
		case Types.FLOAT:
		case Types.REAL:
		case Types.DOUBLE:
		case Types.NUMERIC:
		case Types.DECIMAL:
			converter = numberConverter;
			break;
		case Types.DATE:
		case Types.TIME:
			converter = dateConverter;
			break;
		case Types.TIMESTAMP:
			converter = timestampConverter;
			break;
		case Types.BLOB:
			converter = blobConverter;
			break;
		
		case Types.BINARY:
		case Types.VARBINARY:
		case Types.LONGVARBINARY:
		case Types.NULL:
		case Types.OTHER:
			String typeStr = EJdbcType.toString(type);
			converter = this.instanceMap.get(typeStr);
			break;
		}
		
		return converter;
	}

	/**
	 * @return the charConverter
	 */
	public IConverter getCharConverter() {
		return charConverter;
	}

	/**
	 * @param charConverter the charConverter to set
	 */
	public void setCharConverter(IConverter charConverter) {
		this.charConverter = charConverter;
	}

	/**
	 * @return the numberConverter
	 */
	public IConverter getNumberConverter() {
		return numberConverter;
	}

	/**
	 * @param numberConverter the numberConverter to set
	 */
	public void setNumberConverter(IConverter numberConverter) {
		this.numberConverter = numberConverter;
	}

	/**
	 * @return the dateConverter
	 */
	public IConverter getDateConverter() {
		return dateConverter;
	}

	/**
	 * @param dateConverter the dateConverter to set
	 */
	public void setDateConverter(IConverter dateConverter) {
		this.dateConverter = dateConverter;
	}

	/**
	 * @return the blobConverter
	 */
	public IConverter getBlobConverter() {
		return blobConverter;
	}

	/**
	 * @param blobConverter the blobConverter to set
	 */
	public void setBlobConverter(IConverter blobConverter) {
		this.blobConverter = blobConverter;
	}

	/**
	 * @param timestampConverter the timestampConverter to set
	 */
	public void setTimestampConverter(IConverter timestampConverter) {
		this.timestampConverter = timestampConverter;
	}
}
