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
 * EJB本地服务定位器
 */
public abstract class EJBServiceLocatorBase extends OTSLocatorBase{
	
	/**
	 * 根据服务关键字查找Home
	 * @return Pair<Object, Method> home对象和create方法的值对
	 * @throws NamingException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	protected IPair<Object, Method> getHomeCreator() throws NamingException, SecurityException, NoSuchMethodException{
		
		//1.从缓存获取新建方法
		IPair<Object, Method> creator = (IPair<Object, Method>)this.getFromCache(this.serviceKey);
		
		//2.如果为空，从上下文中查找，并放入到缓存中
		if(creator == null){
						
			//2.1查找Home对象
			Object home = this.lookUpHome(serviceKey);

			//2.2获得新建方法
			Method method = home.getClass().getDeclaredMethod("create");
			
			//2.3新建伴侣
			creator = new Pair<Object, Method>(home, method);
			
			//2.4将伴侣放入到缓存中
			this.putToCache(serviceKey, creator);
		}
		
		//3.返回EJBLocalHome对象
		return creator;
	}
	
	/**
	 * 查找Home对象
	 * @param <T> Home类型
	 * @param key 服务关键字
	 * @return Home对象
	 * @throws NamingException 
	 * @throws NamingException
	 */
	@SuppressWarnings("unchecked")
	private <T> T lookUpHome(String key) throws NamingException{
		
		//1获取服务端上下文
		Context context = contextPrototype.getInstance();

		//2查找EJBLocalHome对象
		T value = (T)context.lookup(serviceKey);
		
		return value;
		
	}
}
