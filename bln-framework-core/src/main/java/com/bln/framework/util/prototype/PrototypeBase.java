/**
 * @author gengw
 * Created on Apr 1, 2012
 */
package com.bln.framework.util.prototype;

import javax.naming.NamingException;

import com.bln.framework.base.BaseObj;
import com.bln.framework.factory.exception.NewInstanceErrorException;

/**
 * ԭ�ͻ���
 */
public abstract class PrototypeBase<P, T> extends BaseObj implements IPrototype<P, T> {
	
	/**
	 * ������Ϣ
	 */
	protected P property = null;
	
	/**
	 * ��Ҫ����ԭ�͵�ʵ��
	 */
	protected T instance = null;
	
	/**
	 * �Ƿ����ɵ���ʵ��
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
	 * ��ʼ��ʵ��
	 * @throws NamingException
	 */
	protected abstract T newInstance() throws Throwable;


}
