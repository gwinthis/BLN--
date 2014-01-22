/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.util.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.bln.framework.util.asserter.Assert;

/**
 * 构造对象类
 */
public class ConstructObj {
	
	/**
	 * 构造器缓存
	 */
	protected static Map<String, Constructor<?>> cache = new HashMap<String, Constructor<?>>();
	
	/**
	 * 构造对象
	 * @param classNm 类的全路径名称
	 * @param params 构造子的参数
	 * @return 生成的对象
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IllegalArgumentException 
	 */
	public static Object construct(String classNm, Object...params) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		
		//校验参数
		Assert.paramIsNotNull(classNm, "classNm");
		
		//1.获取构造子
		
		//1.1生成参数的类型数组
		Class<?> [] clazzs = null;
		if(params != null && params.length > 0){
			clazzs = new Class<?>[params.length];
		}
		
		//1.2获取构造子
		Constructor<?> contructor = getConstructor(classNm, clazzs);
		
		//2.根据构造子生成对象
		Object obj = contructor.newInstance(params);
		
		//3.返回对象
		return obj;
	}
	
	/**
	 * 根据构造子的描述获得对象
	 * @param consKey
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	protected static Constructor<?> getConstructor(String classNm, Class<?>...paramClazzs) throws SecurityException, NoSuchMethodException, ClassNotFoundException{
		
		//1.生成构造子的描述
		StringBuilder consKeyInfo = new StringBuilder(classNm);
		if(paramClazzs != null && paramClazzs.length > 0){
			for( Class<?> paramClazz : paramClazzs){
				consKeyInfo.append(",").append(paramClazz.getName());
			}
		}
		String consKey = consKeyInfo.toString();
		
		//2.从缓存中获取构造子
		Constructor<?> constru = cache.get(consKey);
		
		//3.如果为空，生成构造子添加到缓存中
		if(constru == null){
			constru = Class.forName(classNm).getConstructor(paramClazzs);
			cache.put(consKey, constru);
		}
		
		//4.返回构造子
		return constru;
	}

}
