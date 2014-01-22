package com.bln.framework.biz.service.facade.message;

public interface IExceptionMessage {

	/**
	 * 根据异常获取消息码
	 * @param clazzName 类名
	 * @return 消息码
	 */
	public abstract String getErrorCode(String clazzName);

	/**
	 * 根据错误消息获取响应消息
	 * @param e 错误消息
	 * @return 响应消息
	 */
	public abstract String getErrorDesc(Throwable e);

}