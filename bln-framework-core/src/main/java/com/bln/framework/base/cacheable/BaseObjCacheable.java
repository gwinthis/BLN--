/**
 * @author gengw
 * Craeted on 2012-03-29
 */
package com.bln.framework.base.cacheable;

import java.util.HashMap;
import java.util.Map;

import com.bln.framework.base.BaseObj;

/**
 * ���Ի���Ļ������
 */
public class BaseObjCacheable extends BaseObj{
	
	/**
	 * ����
	 */
	private final Map<String, Object> cache = new HashMap<String, Object>();
	
	/**
	 * �Ѷ�����ӵ�����
	 * @param key ��Ҫ������������
	 * @param value ��Ҫ����Ķ���
	 */
	protected void putToCache(String key, Object value){
		cache.put(key, value);
	}
	
	/**
	 * �Ѷ�����ӵ�����
	 * @param key ��Ҫ������������
	 * @param value ��Ҫ����Ķ���
	 */
	protected Object getFromCache(String key){
		return cache.get(key);
	}
	
	/**
	 * �����Ƿ��ڻ����д���
	 * @param key ������������
	 */
	protected boolean containsKey(String key){
		return cache.containsKey(key);
	}
}
