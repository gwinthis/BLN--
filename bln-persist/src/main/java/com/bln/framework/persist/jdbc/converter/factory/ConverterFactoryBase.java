/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.jdbc.converter.factory;

import java.util.HashMap;
import java.util.Map;

import com.bln.framework.persist.jdbc.converter.IConverter;


/**
 * ת����������Ļ���
 */
public abstract class ConverterFactoryBase implements IConverterFactory{
	
	/**
	 * �洢��Ľṹ����
	 */
	protected Map<String, IConverter> instanceMap = new HashMap<String, IConverter>();
	
}
