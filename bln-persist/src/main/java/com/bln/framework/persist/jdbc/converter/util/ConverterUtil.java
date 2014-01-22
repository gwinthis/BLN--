/**
 * @author gengw
 * Created on Apr 19, 2012
 */
package com.bln.framework.persist.jdbc.converter.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.bln.framework.base.BaseObj;
import com.bln.framework.persist.jdbc.EJdbcType;
import com.bln.framework.persist.jdbc.converter.IConverter;
import com.bln.framework.persist.jdbc.converter.exception.ConvertException;
import com.bln.framework.persist.jdbc.converter.factory.IConverterFactory;
import com.bln.framework.util.asserter.Assert;

/**
 * 转换器工具箱
 */
public class ConverterUtil extends BaseObj implements IConverterUtil {
	
	/**
	 * 转换器工厂
	 */
	IConverterFactory convertFactory = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.util.IConverterUtil#convert(java.lang.String, int)
	 */
	public Object convert(String val, int type){
		
		//1.查找转换器
		IConverter converter = getConverter(type);
		
		//2.转换对象
		Object object = null;
		try {
			object = converter.convert(val);
		} catch (ParseException e) {
			ConvertException ce = new ConvertException();
			ce.initCause(e);
			throw ce;
		}
		
		//3.返回转换后的对象
		return object;
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.util.IConverterUtil#toString(java.lang.Object, int)
	 */
	public String toString(Object val, int type){
		
		if(val == null){
			return null;
		}
		
		//1.查找转换器
		IConverter converter = getConverter(type);
		
		//2.获得描述
		String info = converter.toString(val);
		
		//3.返回描述
		return info;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.util.IConverterUtil#toString(java.sql.ResultSet, java.lang.String, int)
	 */
	public String toString(ResultSet rs, String colName, int type) throws SQLException {
		
		Assert.paramIsNotNull(rs,"rs");
		Assert.paramIsNotNull(colName,"colName");
		
		//1.查找转换器
		IConverter converter = getConverter(type);
		
		//2.获得描述
		String info = converter.toString(rs, colName);
		
		//3.返回描述
		return info;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.util.IConverterUtil#toString(java.sql.ResultSet, int, int)
	 */
	public String toString(ResultSet rs, int colIdx, int type) throws SQLException {
		Assert.paramIsNotNull(rs,"rs");
		Assert.expressionIsNotTrue(colIdx < 1, "colIdx < 1");
		
		//1.查找转换器
		IConverter converter = getConverter(type);
		
		//2.获得描述
		String info = converter.toString(rs, colIdx);
		
		//3.返回描述
		return info;
	}
	
	/**
	 * 查找转换器
	 * @param type 转换器类型
	 * @return 转换器
	 */
	protected IConverter getConverter(int type){
		IConverter converter = convertFactory.getInstance(type);
		if(converter == null){
			throw new ConvertException("expect convert is not null, whose type is " + EJdbcType.toString(type));
		}
		
		return converter;
	}
	
	/**
	 * @return the convertFactory
	 */
	public IConverterFactory getConvertFactory() {
		return convertFactory;
	}

	/**
	 * @param convertFactory the convertFactory to set
	 */
	public void setConvertFactory(IConverterFactory convertFactory) {
		this.convertFactory = convertFactory;
	}

}
