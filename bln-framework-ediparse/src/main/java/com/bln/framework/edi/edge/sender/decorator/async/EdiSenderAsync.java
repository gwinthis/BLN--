/**
 * @author gengw
 * Created on 2012-03-23
 */
package com.bln.framework.edi.edge.sender.decorator.async;

import com.bln.framework.edi.edge.sender.IEdiSender;
import com.bln.framework.edi.edge.sender.decorator.async.param.OnFailedParam;
import com.bln.framework.edi.edge.sender.decorator.async.param.OnSuccessParam;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.threadpool.IThreadPoolPrototype;
import com.bln.framework.util.callback.ICallBackable;

/**
 * EDI异步发送
 */
public class EdiSenderAsync implements IEdiSenderDecorator{

	/**
	 * 线程池原型
	 */
	IThreadPoolPrototype threadPoolProto = null;
	
	/**
	 * 发送任务
	 */
	SendCommand sendCmd = new SendCommand();
	
	/**
	 * 构造函数
	 * @param ediSender EDI发送器
	 * @param onSuccessHandler 发送成功处理器
	 * @param onFailedHandler 发送失败处理器
	 */
	public EdiSenderAsync(IEdiSender ediSender, ICallBackable onSuccessHandler, ICallBackable onFailedHandler){
		
		sendCmd.setEdiSender(ediSender);
		sendCmd.setOnSuccessHandler(onSuccessHandler);
		sendCmd.setOnFailedHandler(onFailedHandler);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.sender.IEdiSender#send(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject send(IMessageObject reqMo) throws Throwable {
		
		//1.装配异步发送命令对象
		
		//1.1设置要发送的数据
		sendCmd.setReqMo(reqMo);
		
		//2.调用发送命令
		threadPoolProto.getInstance().execute(sendCmd);
		
		//3.返回空
		return null;
	}
	
	/**
	 * 发送任务
	 */
	protected class SendCommand implements Runnable{

		/**
		 * EDI发送器
		 */
		IEdiSender ediSender = null;

		/**
		 * 要发送的数据
		 */
		protected IMessageObject reqMo = null;
		
		/**
		 * 操作成功的处理器
		 */
		protected ICallBackable onSuccessHandler = null;

		/**
		 * 操作失败的处理器
		 */
		protected ICallBackable onFailedHandler = null;

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			
			try {
				
				//EDI发送
				IMessageObject respMo = ediSender.send(reqMo);
				
				//如果成功处理器不为空，调用成功处理方法
				if(onSuccessHandler != null){
					
					//定义参数
					OnSuccessParam param = new OnSuccessParam();
					param.setRespData(respMo);
					
					//执行回调方法
					onSuccessHandler.callBack(param);
				}

			} catch (Throwable e) {
				
				//如失败处理器不为空，调用失败处理方法
				if(onFailedHandler != null){
					
					//定义参数
					OnFailedParam param = new OnFailedParam();
					param.setException(e);
					
					//执行回调方法
					onFailedHandler.callBack(param);
				}
			}
		}

		public ICallBackable getOnSuccessHandler() {
			return onSuccessHandler;
		}


		public void setOnSuccessHandler(ICallBackable onSuccessHandler) {
			this.onSuccessHandler = onSuccessHandler;
		}


		public ICallBackable getOnFailedHandler() {
			return onFailedHandler;
		}


		public void setOnFailedHandler(ICallBackable onFailedHandler) {
			this.onFailedHandler = onFailedHandler;
		}


		public IEdiSender getEdiSender() {
			return ediSender;
		}


		public void setEdiSender(IEdiSender ediSender) {
			this.ediSender = ediSender;
		}


		public IMessageObject getReqMo() {
			return reqMo;
		}


		public void setReqMo(IMessageObject reqMo) {
			this.reqMo = reqMo;
		}
		
	}

	/**
	 * @return
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#getEdiSender()
	 */
	public IEdiSender getEdiSender() {
		return sendCmd.getEdiSender();
	}

	/**
	 * @return
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#getOnFailedHandler()
	 */
	public ICallBackable getOnFailedHandler() {
		return sendCmd.getOnFailedHandler();
	}

	/**
	 * @return
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#getOnSuccessHandler()
	 */
	public ICallBackable getOnSuccessHandler() {
		return sendCmd.getOnSuccessHandler();
	}

	/**
	 * @param ediSender
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#setEdiSender(com.bln.framework.edi.edge.sender.IEdiSender)
	 */
	public void setEdiSender(IEdiSender ediSender) {
		sendCmd.setEdiSender(ediSender);
	}

	/**
	 * @param onFailedHandler
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#setOnFailedHandler(com.bln.framework.util.callback.ICallBackable)
	 */
	public void setOnFailedHandler(ICallBackable onFailedHandler) {
		sendCmd.setOnFailedHandler(onFailedHandler);
	}

	/**
	 * @param onSuccessHandler
	 * @see com.bln.framework.edi.edge.sender.decorator.async.EdiSenderAsync.SendCommand#setOnSuccessHandler(com.bln.framework.util.callback.ICallBackable)
	 */
	public void setOnSuccessHandler(ICallBackable onSuccessHandler) {
		sendCmd.setOnSuccessHandler(onSuccessHandler);
	}

	/**
	 * @return the threadPoolProto
	 */
	public IThreadPoolPrototype getThreadPoolProto() {
		return threadPoolProto;
	}

	/**
	 * @param threadPoolProto the threadPoolProto to set
	 */
	public void setThreadPoolProto(IThreadPoolPrototype threadPoolProto) {
		this.threadPoolProto = threadPoolProto;
	}

}
