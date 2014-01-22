package com.bln.framework.ots.locator;

import com.bln.framework.jndi.IContextPrototype;

/**
 * �ⲿ����λ��
 */
public interface IOTSLocator{

	/**
	 * ����ⲿ�������
	 * @param <T> ���������
	 * @return �������
	 * @throws Throwable
	 */
	public abstract <T> T getService() throws Throwable;

	/**
	 * @return the contextPrototype
	 */
	public IContextPrototype getContextPrototype();

	/**
	 * @param contextPrototype the contextPrototype to set
	 */
	public void setContextPrototype(IContextPrototype contextPrototype);

	/**
	 * @return the serviceKey
	 */
	public String getServiceKey();

	/**
	 * @param serviceKey the serviceKey to set
	 */
	public void setServiceKey(String serviceKey);

}