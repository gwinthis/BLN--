/**
 * @author gengw
 * Created at 2012-03-23
 */
package com.bln.framework.edi.edge.sender;

import java.io.IOException;

import com.bln.framework.edi.edge.EdiEdgeBase;
import com.bln.framework.mo.IMessageObject;

/**
 * EDI发送器基类
 */
public abstract class EdiSenderBase extends EdiEdgeBase implements IEdiSender{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.sender.IEdiSender#send(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject send(IMessageObject reqMo) throws Throwable{
		
		//1.生成字节数组
		byte[] ediDatas = fileBuilderByMo.buildFile(reqMo);
		
		//2.输出EDI
		this.ediOutput(ediDatas, encoding);
		
		//3.过滤请求报文
		ediDatas = this.requestEdiFilter(ediDatas);	
		
		//4.网络发送
		byte[] respData = netSend(reqMo, ediDatas);

		//5.过滤响应报文
		respData = this.responseEdiFilter(respData);	

		//6.构造响应报文
		IMessageObject respMo = null;
		if(respData != null && respData.length > 0){
			respMo = this.moBuilder.buildMo(respData);
		}
		
		//7.返回响应信息
		return respMo;
		
	}
	
	/**
	 * 网络发送报文
	 * @param reqMo 请求信息报文
	 * @return 响应信息
	 * @throws IOExecption
	 */
	protected abstract byte[] netSend(IMessageObject reqMo, byte[] ediDatas) throws IOException;
}
