/**
 * @author gengw
 * Created on Apr 18, 2012
 */
package com.bln.framework.factory.simple;

import java.util.Map;

import com.bln.framework.factory.FactoryBase;
import com.bln.framework.util.asserter.Assert;

/**
 * 简单工厂，根据名称获得实例
 */
public class SimpleFactory<T> extends FactoryBase<T> implements ISimpleFactory<T>{
	
	/**
	 * 存储表的结构对象
	 */
	protected Map<String, T> instanceMap = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.simple.ISimpleFactory#getInstance(java.lang.String)
	 */
	public T getInstance(String objNm){
		
		//对象名称不能为空
		Assert.paramIsNotNull(objNm, "objNm");
		
		//获得对象
		T object = instanceMap.get(objNm);
		return object;
	}
	
	/**
	 * @return the objInstanceMap
	 */
	public Map<String, T> getAllInstance() {
		return instanceMap;
	}

	/**
	 * @param objInstanceMap the objInstanceMap to set
	 */
	public void setInstanceMap(Map<String, T> instanceMap) {
		this.instanceMap = instanceMap;
	}

}
