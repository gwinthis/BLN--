/**
 * @author gengw
 * Created on 2012-03-22
 */
package com.bln.framework.edi.stru.client.builder.clientmo;

import com.bln.framework.filestru.builder.FileBuilderBase;
import com.bln.framework.filestru.builder.bymo.IFileBuilderByMo;
import com.bln.framework.mo.IMessageObject;

/**
 * 客户端报文生成器
 */
public class ClientFileBuilderByClientMO extends FileBuilderBase implements IFileBuilderByMo {

	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.builder.FileBuildByMo#buildFile(com.bln.framework.mo.IMessageObject)
	 */
	public byte[] buildFile(IMessageObject mo) throws Throwable{
				
		//输出字节数组
		byte[] bytes = mo.writeToBytes();
		
		//返回字节数组
		return bytes;
	}

}
