/**
 * @author Gengw
 * Created at 2008-05-23
 */
package com.bln.framework.app.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Ӧ�������ġ�
 * ���԰������߳�ID������ݺ�ȡ�����ݡ�
 */
public class AppContext {
	
	/**
	 * ʵ��
	 */
	private static AppContext instance = new AppContext();
	
	/**
	 * ����߳���ص������ı���
	 */
	private ThreadLocal<Map<Object, Object>> content = new ThreadLocal<Map<Object, Object>>();
	
	/**
	 * ���캯��
	 */
	private AppContext(){
		
	}
	
	/**
	 * ����ʵ��
	 * @return ServiceContext
	 */
	public static AppContext singleton(){
		return instance;
	}
	
	/**
	 * ��ȡKey����Ӧ��ֵ
	 * @param key
	 * @return value
	 */
	public Object getValue(Object key){
		Map<Object, Object> mc = content.get();
		if(mc == null){
			return null;
		}
		return mc.get(key);
	}
	
	/**
	 * ��ȡKey����Ӧ��ֵ
	 * @param key
	 * @param value
	 */
	public void setValue(Object key, Object value){
		Map<Object, Object> mc = content.get();
		
		if(mc == null){
			mc = new HashMap<Object, Object>();
			content.set(mc);
		}
		
		mc.put(key, value);
	}
	
	/**
	 * �����ǰ�̵߳����б���
	 */
	public void clearCurrentThreadValues(){
		content.set(null);
	}
}
