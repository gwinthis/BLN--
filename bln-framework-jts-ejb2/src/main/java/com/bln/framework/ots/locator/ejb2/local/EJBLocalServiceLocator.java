/**
 * @author gengw
 * Created on 2012-03-29
 */
package com.bln.framework.ots.locator.ejb2.local;

import java.lang.reflect.Method;

import com.bln.framework.ots.locator.ejb2.EJBServiceLocatorBase;
import com.bln.framework.util.pair.IPair;

/**
 * EJB本地服务定位器
 */
public class EJBLocalServiceLocator extends EJBServiceLocatorBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.ejb.locator.IEjbLoacator#getLocalSercie(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getService()throws Throwable{

		//1.从缓存获取EJB服务生成方法
		IPair<Object, Method> creator = this.getHomeCreator();
		
		
		//2.生成服务对象
		T localService = (T)creator.getValue().invoke(creator.getKey());

		//3.返回服务对象
		return localService;
	}
}
