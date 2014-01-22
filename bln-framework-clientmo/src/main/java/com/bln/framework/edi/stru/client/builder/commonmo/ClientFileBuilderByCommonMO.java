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
 * 客户端报文生成器
 */
public class ClientFileBuilderByCommonMO extends FileBuilderBase implements IFileBuilderByMo {
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.builder.FileBuildByMo#buildFile(com.bln.framework.mo.IMessageObject)
	 */
	public byte[] buildFile(IMessageObject mo) throws Throwable{
		
		//转成可生成clientFile的对象
		IClientFileBuildable clientFile = (IClientFileBuildable)this.fileBuildable;
		
		//初始文件
		clientFile.initFile();
		
		//设置ServiceId
		clientFile.setServiceId(mo.getServiceId());

		//设置RequestDate
		clientFile.setRequestDate(mo.getRequestDate());

		//设置ReturnCode
		clientFile.setReturnCode(mo.getReturnCode());

		//设置ErrorCode
		clientFile.setErrorCode(mo.getErrorCode());
		
		//设置ErrorDesc
		clientFile.setErrorDesc(mo.getErrorDesc());
		
		//设置ResponseDate
		clientFile.setResponseDate(mo.getResponseDate());
		
		//设置SessionId
		clientFile.setSessionId(mo.getSessionId());
		
		//设置header节点
		clientFile.setAllValOfHeader(mo.getAllValOfHeader());
		
		//设置Head的ext节点
		clientFile.setAllValOfExtHeader(mo.getAllValOfExtHeader());
		
		//设置响应业务信息
		clientFile.setAllRowsOfResp(mo.getAllRowsOfResp());
		
		//设置响应参数
		clientFile.setAllParamsOfResp(mo.getAllParamsOfResp());
		
		//输出字节数组
		byte[] bytes = clientFile.writeToBytes();
		
		//返回字节数组
		return bytes;
	}
	
}
