/**
 * @author gengw
 * Created on 2012-03-22
 */
package com.bln.framework.edi.stru.client.builder.clientmo;

import com.bln.framework.filestru.builder.FileBuilderBase;
import com.bln.framework.filestru.builder.bymo.IFileBuilderByMo;
import com.bln.framework.mo.IMessageObject;

/**
 * �ͻ��˱���������
 */
public class ClientFileBuilderByClientMO extends FileBuilderBase implements IFileBuilderByMo {

	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.builder.FileBuildByMo#buildFile(com.bln.framework.mo.IMessageObject)
	 */
	public byte[] buildFile(IMessageObject mo) throws Throwable{
				
		//����ֽ�����
		byte[] bytes = mo.writeToBytes();
		
		//�����ֽ�����
		return bytes;
	}

}
