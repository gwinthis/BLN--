/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.ots.locator.ejb2;

import java.lang.reflect.Method;

import javax.naming.Context;
import javax.naming.NamingException;

import com.bln.framework.ots.locator.OTSLocatorBase;
import com.bln.framework.util.pair.IPair;
import com.bln.framework.util.pair.Pair;

/**
 * EJB���ط���λ��
 */
public abstract class EJBServiceLocatorBase extends OTSLocatorBase{
	
	/**
	 * ���ݷ���ؼ��ֲ���Home
	 * @return Pair<Object, Method> home�����create������ֵ��
	 * @throws NamingException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	protected IPair<Object, Method> getHomeCreator() throws NamingException, SecurityException, NoSuchMethodException{
		
		//1.�ӻ����ȡ�½�����
		IPair<Object, Method> creator = (IPair<Object, Method>)this.getFromCache(this.serviceKey);
		
		//2.���Ϊ�գ����������в��ң������뵽������
		if(creator == null){
						
			//2.1����Home����
			Object home = this.lookUpHome(serviceKey);

			//2.2����½�����
			Method method = home.getClass().getDeclaredMethod("create");
			
			//2.3�½�����
			creator = new Pair<Object, Method>(home, method);
			
			//2.4�����·��뵽������
			this.putToCache(serviceKey, creator);
		}
		
		//3.����EJBLocalHome����
		return creator;
	}
	
	/**
	 * ����Home����
	 * @param <T> Home����
	 * @param key ����ؼ���
	 * @return Home����
	 * @throws NamingException 
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	private <T> T lookUpHome(String key) throws NamingException{
		
		//1��ȡ�����������
		Context context = contextPrototype.getInstance();

		//2����EJBLocalHome����
		T value = (T)context.lookup(serviceKey);
		
		return value;
		
	}
}
