/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.edi.edge.sender.decorator.async.param;

import com.bln.framework.util.callback.CallbackParam;

/**
 * 网络请求成功时所调用的参数
 */
public class OnFailedParam extends CallbackParam{

	/**
	 * 异常信息
	 */
	protected Throwable exception = null;

	/**
	 * 获得异常信息
	 * @return
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * 设置异常信息
	 * @param exception
	 */
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
}
