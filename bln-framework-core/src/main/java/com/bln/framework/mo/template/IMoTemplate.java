package com.bln.framework.mo.template;

import com.bln.framework.mo.IMessageObject;

public interface IMoTemplate {

	/**
	 * 请求结束
	 * @return TODO
	 */
	public abstract IMessageObject requestEnd(IMessageObject mo);

	/**
	 * 服务成功完成
	 * @param mo 要处理的消息对象
	 * @return TODO
	 */
	public abstract IMessageObject serviceSuccessfulEnd(IMessageObject mo);

	/**
	 * 服务失败结束
	 * @param mo 要处理的消息对象
	 * @param respCode 错误码
	 * @param respDesc 错误描述
	 * @return 处理完的消息对象
	 */
	public IMessageObject serviceFailedEnd(IMessageObject mo, String errorCode, String respDesc);

	/**
	 * 服务失败结束 
	 * @param mo 要处理的消息对象
	 * @param errorCode 错误码
	 * @return 处理完的消息对象
	 */
	public IMessageObject serviceFailedEnd(IMessageObject mo, String errorCode);

}