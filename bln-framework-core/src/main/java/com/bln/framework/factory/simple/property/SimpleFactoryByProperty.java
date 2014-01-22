/**
 * @author gengw
 * Created on 2012-03-19
 */
package com.bln.framework.factory.simple.property;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.factory.IFactory;
import com.bln.framework.factory.simple.SimpleFactory;

/**
 * 通过property文件初始化的简单工厂抽象类
 */
public abstract class SimpleFactoryByProperty<T> extends SimpleFactory<T> implements IFactory<T>{
	
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(SimpleFactoryByProperty.class);

	/**
	 * 配置文件路径
	 */
	protected String propertyUrl = null;
		
	/**
	 * 初始化工厂类
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	protected void init(){

		boolean isDebug = log.isDebugEnabled();
		if(isDebug){
			StringBuilder info = new StringBuilder("Initialize SimpleFactory ");
			
			//类型信息
			info.append("\r\n class is ").append(this.getClass().getName());
			
			//配置信息
			info.append("\r\n propertyUrl is ").append(propertyUrl);
			
			log.debug(info);
			
		}
		
		//生成实例
		ResourceBundle config = ResourceBundle.getBundle(propertyUrl);
		
		Enumeration<String> keys = config.getKeys();
		while (keys.hasMoreElements()) {
			
			String key = keys.nextElement();		
			String value = config.getString(key);
			
			//调试信息
			log.debug(new StringBuilder("Loding object ").append(key).append("!"));
			try {
				T instance = (T) Class.forName(value).newInstance();
				instanceMap.put(key, instance);
			} catch (Throwable e) {
				log.debug(new StringBuilder("When Load object ").append(key).append(" found error!"), e);
			}
			
		}
	}

	/**
	 * @return the propertyUrl
	 */
	public String getPropertyUrl() {
		return propertyUrl;
	}

	/**
	 * @param propertyUrl the propertyUrl to set
	 */
	public void setPropertyUrl(String propertyUrl) {
		this.propertyUrl = propertyUrl;
	}

	public T getNotNullInstance(String objNm) {
		return null;
	}
}
