/**
 * @author gengw
 * Created at 2012-03-23
 */
package com.bln.framework.edi.edge.sender;

import java.io.IOException;

import com.bln.framework.edi.edge.EdiEdgeBase;
import com.bln.framework.mo.IMessageObject;

/**
 * EDI����������
 */
public abstract class EdiSenderBase extends EdiEdgeBase implements IEdiSender{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.edge.sender.IEdiSender#send(com.bln.framework.mo.IMessageObject)
	 */
	public IMessageObject send(IMessageObject reqMo) throws Throwable{
		
		//1.�����ֽ�����
		byte[] ediDatas = fileBuilderByMo.buildFile(reqMo);
		
		//2.���EDI
		this.ediOutput(ediDatas, encoding);
		
		//3.����������
		ediDatas = this.requestEdiFilter(ediDatas);	
		
		//4.���緢��
		byte[] respData = netSend(reqMo, ediDatas);

		//5.������Ӧ����
		respData = this.responseEdiFilter(respData);	

		//6.������Ӧ����
		IMessageObject respMo = null;
		if(respData != null && respData.length > 0){
			respMo = this.moBuilder.buildMo(respData);
		}
		
		//7.������Ӧ��Ϣ
		return respMo;
		
	}
	
	/**
	 * ���緢�ͱ���
	 * @param reqMo ������Ϣ����
	 * @return ��Ӧ��Ϣ
	 * @throws IOExecption
	 */
	protected abstract byte[] netSend(IMessageObject reqMo, byte[] ediDatas) throws IOException;
}
