/**
 * @author gengw
 * Created on Jun 7, 2012
 */
package com.bln.framework.factory;

import com.bln.framework.base.BaseObj;
import com.bln.framework.factory.exception.NewInstanceErrorException;

/**
 * ��������
 */
public abstract class FactoryBase<T> extends BaseObj implements IFactory<T>{
	
	/**
	 * ��������
	 */
	public String factoryName = this.getClass().getSimpleName();

	/**
	 * @return the factoryName
	 */
	public String getFactoryName() {
		return factoryName;
	}
	
	/**
	 * ��÷ǿյ�ʵ���������ÿ�ʵ�����׳��쳣
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
