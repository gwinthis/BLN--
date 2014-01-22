/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.edi.edge.rcver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.edi.edge.EdiEdgeBase;
import com.bln.framework.edi.edge.rcver.listener.IRcverListener;
import com.bln.framework.mo.IMessageObject;

/**
 * EDI接收器
 */
public abstract class EdiRcverBase extends EdiEdgeBase implements IEdiRcver {
	
	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(EdiRcverBase.class);
	
	/**
	 * 监听器
	 */
	protected IRcverListener[] listeners = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.rcver.EdiRcverBase#receive(byte[])
	 */
	public byte[] receive(byte[] reqEdiData, Object originalRequestObject) throws Throwable{
		
		byte[] respData = null;
		try{
			
			//触发监听器
			if(listeners != null && listeners.length > 0){
				for ( IRcverListener listener : listeners){
					try{
						listener.startRequest(reqEdiData, originalRequestObject);
					}catch(Throwable e){
						log.warn("call listener " + listener + " startRequest error! ", e);
					}
				}
			}
			
			//1.过滤请求报文
			reqEdiData = this.requestEdiFilter(reqEdiData);	
			
			//2.输出请求EDI
			if(this.outputRequestEdi){
				this.ediOutput(reqEdiData, encoding);
			}
			
			//3.生成消息对象
			IMessageObject reqMo = moBuilder.buildMo(reqEdiData);
			
			//输出跟踪日志
			if(log.isInfoEnabled()){
				StringBuilder info = new StringBuilder("it received request action ");
				info.append(reqMo.getServiceId());
				info.append(", starting process!");
				
				log.info(info);
			}
			
			//4.消费请求消息对象
			IMessageObject respMo = this.consumeReqMo(reqMo, originalRequestObject);
			
			//5.生成响应信息的字节数组
			respData = fileBuilderByMo.buildFile(respMo);
			
			//输出响应报文
			if(outputResponseEdi){
				this.ediOutput(respData, encoding);
			}
			
			//6.过滤响应报文
			respData = this.responseEdiFilter(respData);
			
			//输出跟踪日志
			if(log.isInfoEnabled()){
				StringBuilder info = new StringBuilder("action ");
				info.append(reqMo.getServiceId());
				info.append(" has been processed sucessfully!");
				
				log.info(info);
			}
			
		}finally{
			//触发监听器
			if(listeners != null && listeners.length > 0){
				for ( IRcverListener listener : listeners){
					try{
						listener.finishRequest(reqEdiData, respData, originalRequestObject);
					}catch(Throwable e){
						log.warn("call listener " + listener + " finishRequest error! ", e);
					}
				}
			}
		}
		
		//7.返回响应信息
		return respData;

	}
	
	/**
	 * 消费接收到的消息对象
	 * @param reqMo 消息对象，通过接收到的EDI数据组装而成
	 * @param originalRequestObject 原始请求对象，例如HTTPServletRequest
	 * @return 响应信息的消息对象
	 * @throws Throwable
	 */
	protected abstract IMessageObject consumeReqMo(IMessageObject reqMo, Object originalRequestObject) throws Throwable;

	/**
	 * @return the listeners
	 */
	public IRcverListener[] getListeners() {
		return listeners;
	}

	/**
	 * @param listeners the listeners to set
	 */
	public void setListeners(IRcverListener[] listeners) {
		this.listeners = listeners;
	}

}
