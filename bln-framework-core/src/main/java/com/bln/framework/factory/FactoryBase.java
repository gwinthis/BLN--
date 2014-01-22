/**
 * @author gengw
 * Created on Jun 7, 2012
 */
package com.bln.framework.factory;

import com.bln.framework.base.BaseObj;
import com.bln.framework.factory.exception.NewInstanceErrorException;

/**
 * 工厂基类
 */
public abstract class FactoryBase<T> extends BaseObj implements IFactory<T>{
	
	/**
	 * 工厂名称
	 */
	public String factoryName = this.getClass().getSimpleName();

	/**
	 * @return the factoryName
	 */
	public String getFactoryName() {
		return factoryName;
	}
	
	/**
	 * 获得非空的实例，如果获得空实例将抛出异常
	 * @param objNm
	 * @return
	 */
	public T getNotNullInstance(String objNm){
		T object = getInstance(objNm);
		if(object == null){
			throw new NewInstanceErrorException(objNm + " is not in " + this.factoryName + " factory!");
		}
		return object;
	}

}
