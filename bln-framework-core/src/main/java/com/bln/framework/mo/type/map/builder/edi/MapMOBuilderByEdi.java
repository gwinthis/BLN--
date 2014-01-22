/**
 * @author gengw
 * Created on 2012-03-12
 */
package com.bln.framework.mo.type.map.builder.edi;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.mo.buildable.edi.IBuildMOByEdiable;
import com.bln.framework.mo.builder.MoBuilderBase;

/**
 * <p>通过外部报文生成IMessageObject对象</p>
 * 外部报文按字节数组的形式传过来
 */
public class MapMOBuilderByEdi extends MoBuilderBase<IMessageObject, IBuildMOByEdiable> implements IMapMOBuilderByEdi{

	/**
	 * 把报文解包成消息对象
	 * @param bytes 接收到的字节数组
	 * @return 把报文解包后的消息对象
	 * @throws Throwable 解析报文所抛出的异常
	 */
	public IMessageObject buildMo(byte[] bytes) throws Throwable {
		
		//1.从字节数组生成报文结构
		this.buildMOable.readFromData(bytes);
		
		//2.构造消息对象
		
		//设置ServiceId
		mo.setServiceId(buildMOable.getServiceId());

		//设置RequestDate
		mo.setRequestDate(buildMOable.getRequestDate());

		//设置ReturnCode
		mo.setReturnCode(buildMOable.getReturnCode());

		//设置ErrorCode
		mo.setErrorCode(buildMOable.getErrorCode());
		
		//设置ErrorDesc
		mo.setErrorDesc(buildMOable.getErrorDesc());
		
		//设置ResponseDate
		mo.setResponseDate(buildMOable.getResponseDate());
		
		//设置SessionId
		mo.setSessionId(buildMOable.getSessionId());
		
		//设置消息头
		mo.setAllValOfExtHeader(buildMOable.getAllValOfExtHeader());

		//设置请求的业务数据
		mo.setAllRowsOfReq(buildMOable.getAllRowsOfReq());

		//设置请求的参数数据
		mo.setAllParamOfReq(buildMOable.getAllParamsOfReq());

		//设置响应的业务数据
		mo.setAllRowsOfResp(buildMOable.getAllRowsOfResp());

		//设置响应的参数数据
		mo.setAllParamsOfResp(buildMOable.getAllParamsOfResp());
		
		//3.返回消息对象
		return mo;
	}
}
