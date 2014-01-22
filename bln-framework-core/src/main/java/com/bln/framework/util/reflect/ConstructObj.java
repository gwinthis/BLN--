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
 * ���������
 */
public class ConstructObj {
	
	/**
	 * ����������
	 */
	protected static Map<String, Constructor<?>> cache = new HashMap<String, Constructor<?>>();
	
	/**
	 * �������
	 * @param classNm ���ȫ·������
	 * @param params �����ӵĲ���
	 * @return ���ɵĶ���
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IllegalArgumentException 
	 */
	public static Object construct(String classNm, Object...params) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		
		//У�����
		Assert.paramIsNotNull(classNm, "classNm");
		
		//1.��ȡ������
		
		//1.1���ɲ�������������
		Class<?> [] clazzs = null;
		if(params != null && params.length > 0){
			clazzs = new Class<?>[params.length];
		}
		
		//1.2��ȡ������
		Constructor<?> contructor = getConstructor(classNm, clazzs);
		
		//2.���ݹ��������ɶ���
		Object obj = contructor.newInstance(params);
		
		//3.���ض���
		return obj;
	}
	
	/**
	 * ���ݹ����ӵ�������ö���
	 * @param consKey
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	protected static Constructor<?> getConstructor(String classNm, Class<?>...paramClazzs) throws SecurityException, NoSuchMethodException, ClassNotFoundException{
		
		//1.���ɹ����ӵ�����
		StringBuilder consKeyInfo = new StringBuilder(classNm);
		if(paramClazzs != null && paramClazzs.length > 0){
			for( Class<?> paramClazz : paramClazzs){
				consKeyInfo.append(",").append(paramClazz.getName());
			}
		}
		String consKey = consKeyInfo.toString();
		
		//2.�ӻ����л�ȡ������
		Constructor<?> constru = cache.get(consKey);
		
		//3.���Ϊ�գ����ɹ�������ӵ�������
		if(constru == null){
			constru = Class.forName(classNm).getConstructor(paramClazzs);
			cache.put(consKey, constru);
		}
		
		//4.���ع�����
		return constru;
	}

}
