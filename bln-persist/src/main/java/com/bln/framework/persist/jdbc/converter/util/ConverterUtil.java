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
 * ת����������
 */
public class ConverterUtil extends BaseObj implements IConverterUtil {
	
	/**
	 * ת��������
	 */
	IConverterFactory convertFactory = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.util.IConverterUtil#convert(java.lang.String, int)
	 */
	public Object convert(String val, int type){
		
		//1.����ת����
		IConverter converter = getConverter(type);
		
		//2.ת������
		Object object = null;
		try {
			object = converter.convert(val);
		} catch (ParseException e) {
			ConvertException ce = new ConvertException();
			ce.initCause(e);
			throw ce;
		}
		
		//3.����ת����Ķ���
		return object;
		
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.util.IConverterUtil#toString(java.lang.Object, int)
	 */
	public String toString(Object val, int type){
		
		if(val == null){
			return null;
		}
		
		//1.����ת����
		IConverter converter = getConverter(type);
		
		//2.�������
		String info = converter.toString(val);
		
		//3.��������
		return info;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.util.IConverterUtil#toString(java.sql.ResultSet, java.lang.String, int)
	 */
	public String toString(ResultSet rs, String colName, int type) throws SQLException {
		
		Assert.paramIsNotNull(rs,"rs");
		Assert.paramIsNotNull(colName,"colName");
		
		//1.����ת����
		IConverter converter = getConverter(type);
		
		//2.�������
		String info = converter.toString(rs, colName);
		
		//3.��������
		return info;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.converter.util.IConverterUtil#toString(java.sql.ResultSet, int, int)
	 */
	public String toString(ResultSet rs, int colIdx, int type) throws SQLException {
		Assert.paramIsNotNull(rs,"rs");
		Assert.expressionIsNotTrue(colIdx < 1, "colIdx < 1");
		
		//1.����ת����
		IConverter converter = getConverter(type);
		
		//2.�������
		String info = converter.toString(rs, colIdx);
		
		//3.��������
		return info;
	}
	
	/**
	 * ����ת����
	 * @param type ת��������
	 * @return ת����
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
