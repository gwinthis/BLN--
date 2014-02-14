/**
 * @author gengw
 * Created on 2012-03-26
 */
package com.bln.framework.edi.edge.rcver.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bln.framework.biz.service.IServiceFacade;
import com.bln.framework.edi.edge.rcver.EdiRcverBase;
import com.bln.framework.edi.edge.rcver.listener.IMOConsumeListener;
import com.bln.framework.edi.service.facadelocator.IServiceFacadeLocator;
import com.bln.framework.mo.IMessageObject;

/**
 * 客户端报文接收器
 * 把请求消息对象提交给服务层
 */
public class ClientEdiRcver extends EdiRcverBase{

	/**
	 * 获得当前对象的Log对象
	 */
	private static final Log log = LogFactory.getLog(ClientEdiRcver.class);

	
	/**
	 * 服务层门面对象定位器
	 */
	IServiceFacadeLocator serviceFacadeLocator = null;
	
	/**
	 * MO消费监听器
	 */
	IMOConsumeListener[] moConsumeListeners = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.rcver.EdiRcverBase#consumeReqMo(com.bln.framework.mo.IMessageObject)
	 */
	@Override
	protected IMessageObject consumeReqMo(IMessageObject reqMo, Object originalRequestObject) throws Throwable {
		
		//1.定位服务层对象
		IServiceFacade serviceFacade = serviceFacadeLocator.locate(reqMo, originalRequestObject);
		
		//2.执行服务
		
		//2.1触发服务执行前事件
		if(moConsumeListeners != null && moConsumeListeners.length > 0){
			for ( IMOConsumeListener listener : moConsumeListeners ){
				try {
					listener.beforExecuteService(reqMo);
				}
				catch (Exception e) {
					log.warn("execute listener " + listener + " beforExecuteService error!", e);
				}
			}
		}
		
		//2.2触发服务执行
		IMessageObject respMo = serviceFacade.service(reqMo);
		
		//2.3触发服务执行完事件
		if(moConsumeListeners != null && moConsumeListeners.length > 0){
			for ( IMOConsumeListener listener : moConsumeListeners ){
				try {
					listener.afterExecuteService(reqMo, respMo);
				}
				catch (Exception e) {
					log.warn("execute listener " + listener + " afterExecuteService error!", e);
				}
			}
		}
		
		//3.返回响应报文
		return respMo;
	}

	public IServiceFacadeLocator getServiceFacadeLocator() {
		return serviceFacadeLocator;
	}

	public void setServiceFacadeLocator(IServiceFacadeLocator serviceFacadeLocator) {
		this.serviceFacadeLocator = serviceFacadeLocator;
	}

}
