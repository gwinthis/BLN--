/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.ots.locator.ejb2.remote;

import java.lang.reflect.Method;

import com.bln.framework.ots.locator.ejb2.EJBServiceLocatorBase;
import com.bln.framework.util.pair.IPair;

/**
 * EJB���ط���λ��
 */
public class EJBRemoteServiceLocator extends EJBServiceLocatorBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.ejb.locator.IEjbLoacator#getLocalSercie(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getService()throws Throwable{
		
		//1.�ӻ����ȡEJB�������ɷ���
		IPair<Object, Method> crateor = this.getHomeCreator();
		
		//2.���ɷ������
		T remoteService = (T)crateor.getValue().invoke(crateor.getKey());
		
		//3.���ط������
		return (T)remoteService;
	}
}
