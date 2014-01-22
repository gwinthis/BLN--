/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.persist.jdbc.converter.factory;

import java.util.HashMap;
import java.util.Map;

import com.bln.framework.persist.jdbc.converter.IConverter;


/**
 * 转换器工厂类的基类
 */
public abstract class ConverterFactoryBase implements IConverterFactory{
	
	/**
	 * 存储表的结构对象
	 */
	protected Map<String, IConverter> instanceMap = new HashMap<String, IConverter>();
	
}
