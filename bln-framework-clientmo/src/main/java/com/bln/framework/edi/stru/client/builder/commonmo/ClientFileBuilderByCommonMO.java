/**
 * @author gengw
 * Created on 2012-03-22
 */
package com.bln.framework.edi.stru.client.builder.commonmo;

import com.bln.framework.edi.stru.client.IClientFileBuildable;
import com.bln.framework.filestru.builder.FileBuilderBase;
import com.bln.framework.filestru.builder.bymo.IFileBuilderByMo;
import com.bln.framework.mo.IMessageObject;

/**
 * �ͻ��˱���������
 */
public class ClientFileBuilderByCommonMO extends FileBuilderBase implements IFileBuilderByMo {
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.builder.FileBuildByMo#buildFile(com.bln.framework.mo.IMessageObject)
	 */
	public byte[] buildFile(IMessageObject mo) throws Throwable{
		
		//ת�ɿ�����clientFile�Ķ���
		IClientFileBuildable clientFile = (IClientFileBuildable)this.fileBuildable;
		
		//��ʼ�ļ�
		clientFile.initFile();
		
		//����ServiceId
		clientFile.setServiceId(mo.getServiceId());

		//����RequestDate
		clientFile.setRequestDate(mo.getRequestDate());

		//����ReturnCode
		clientFile.setReturnCode(mo.getReturnCode());

		//����ErrorCode
		clientFile.setErrorCode(mo.getErrorCode());
		
		//����ErrorDesc
		clientFile.setErrorDesc(mo.getErrorDesc());
		
		//����ResponseDate
		clientFile.setResponseDate(mo.getResponseDate());
		
		//����SessionId
		clientFile.setSessionId(mo.getSessionId());
		
		//����header�ڵ�
		clientFile.setAllValOfHeader(mo.getAllValOfHeader());
		
		//����Head��ext�ڵ�
		clientFile.setAllValOfExtHeader(mo.getAllValOfExtHeader());
		
		//������Ӧҵ����Ϣ
		clientFile.setAllRowsOfResp(mo.getAllRowsOfResp());
		
		//������Ӧ����
		clientFile.setAllParamsOfResp(mo.getAllParamsOfResp());
		
		//����ֽ�����
		byte[] bytes = clientFile.writeToBytes();
		
		//�����ֽ�����
		return bytes;
	}
	
}
