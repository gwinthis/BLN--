/**
 * @author gengw
 * Created at 2012-03-27
 */
package com.bln.framework.edi.edge.sender.decorator.async;

import com.bln.framework.edi.edge.sender.IEdiSender;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.util.callback.ICallBackable;

/**
 * EDI发送器的装饰器接口
 */
public interface IEdiSenderDecorator {

	public IMessageObject send(IMessageObject reqMo) throws Throwable;

	/**
	 * @return
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#getEdiSender()
	 */
	public IEdiSender getEdiSender();

	/**
	 * @return
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#getOnFailedHandler()
	 */
	public ICallBackable getOnFailedHandler();

	/**
	 * @return
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#getOnSuccessHandler()
	 */
	public ICallBackable getOnSuccessHandler();

	/**
	 * @param ediSender
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#setEdiSender(com.bln.framework.edi.edge.sender.IEdiSender)
	 */
	public void setEdiSender(IEdiSender ediSender);

	/**
	 * @param onFailedHandler
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#setOnFailedHandler(com.bln.framework.util.callback.ICallBackable)
	 */
	public void setOnFailedHandler(ICallBackable onFailedHandler);

	/**
	 * @param onSuccessHandler
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#setOnSuccessHandler(com.bln.framework.util.callback.ICallBackable)
	 */
	public void setOnSuccessHandler(ICallBackable onSuccessHandler);

}
