/**
 * @author gengw
 * Craeted on 2012-03-29
 */
package com.bln.framework.base.cacheable;

import java.util.HashMap;
import java.util.Map;

import com.bln.framework.base.BaseObj;

/**
 * 可以缓存的基类对象
 */
public class BaseObjCacheable extends BaseObj{
	
	/**
	 * 缓存
	 */
	private final Map<String, Object> cache = new HashMap<String, Object>();
	
	/**
	 * 把对象添加到缓存
	 * @param key 需要缓存对象的名称
	 * @param value 需要缓存的对象
	 */
	protected void putToCache(String key, Object value){
		cache.put(key, value);
	}
	
	/**
	 * 把对象添加到缓存
	 * @param key 需要缓存对象的名称
	 * @param value 需要缓存的对象
	 */
	protected Object getFromCache(String key){
		return cache.get(key);
	}
	
	/**
	 * 对象是否在缓存中存在
	 * @param key 缓存对象的名称
	 */
	protected boolean containsKey(String key){
		return cache.containsKey(key);
	}
}
