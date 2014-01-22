/**
 * @author Gengw
 * Created at 2008-05-23
 */
package com.bln.framework.app.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用上下文。
 * 可以按请求线程ID存放数据和取出数据。
 */
public class AppContext {
	
	/**
	 * 实例
	 */
	private static AppContext instance = new AppContext();
	
	/**
	 * 存放线程相关的上下文变量
	 */
	private ThreadLocal<Map<Object, Object>> content = new ThreadLocal<Map<Object, Object>>();
	
	/**
	 * 构造函数
	 */
	private AppContext(){
		
	}
	
	/**
	 * 返回实例
	 * @return ServiceContext
	 */
	public static AppContext singleton(){
		return instance;
	}
	
	/**
	 * 获取Key所对应的值
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
	 * 获取Key所对应的值
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
	 * 清除当前线程的所有变量
	 */
	public void clearCurrentThreadValues(){
		content.set(null);
	}
}
