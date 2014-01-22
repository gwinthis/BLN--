/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.util.callback;

/**
 * 可回调接口
 */
public interface ICallBackable {
	
	/**
	 * 回调方法
	 * @param param 参数值
	 */
	public <T> void callBack( T param);

}
