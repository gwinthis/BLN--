/**
 * @author gengw
 * Created on Apr 1, 2012
 */
package com.bln.framework.util.prototype;

import javax.naming.NamingException;

import com.bln.framework.base.BaseObj;
import com.bln.framework.factory.exception.NewInstanceErrorException;

/**
 * 原型基类
 */
public abstract class PrototypeBase<P, T> extends BaseObj implements IPrototype<P, T> {
	
	/**
	 * 配置信息
	 */
	protected P property = null;
	
	/**
	 * 需要设置原型的实例
	 */
	protected T instance = null;
	
	/**
	 * 是否生成单例实例
	 */
	protected boolean isSingle = false;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.util.prototype.IPrototype#getProperty()
	 */
	public P getProperty() {
		return property;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.util.prototype.IPrototype#setProperty(P)
	 */
	public void setProperty(P property) {
		this.property = property;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.util.prototype.IPrototype#getInstance()
	 */
	public T getInstance(){
		
		T obj = null;
		
		try {
			if (!isSingle) {
				obj = newInstance();
			} else {
				if (this.instance == null) {
					this.instance = newInstance();
				}
				obj = this.instance;
			}
		} catch (Throwable e) {
			NewInstanceErrorException goe = new NewInstanceErrorException();
			goe.initCause(e);
			throw goe;
		}
		return obj;
	}
	
	/**
	 * 初始化实例
	 * @throws NamingException
	 */
	protected abstract T newInstance() throws Throwable;


}
