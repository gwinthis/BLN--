package com.bln.framework.mo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bln.framework.persist.row.IRow;

public interface IMessageObject {

	/**
	 * 获得行为代码
	 * @return
	 * @throws Throwable 
	 */
	public abstract String getServiceId();

	/**
	 * 设置行为代码
	 * @param serviceId
	 */
	public abstract void setServiceId(String serviceId);
	
	/**
	 * 获得消息头中的数据项
	 * @param paramNm 数据项名称
	 * @return 数据项值
	 * @throws Throwable 
	 */
	public abstract String getValueOfExtHeader(String paramNm);

	/**
	 * 设置消息头中的数据项
	 * @param paramNm 数据项名称
	 * @param val 数据项值
	 * @throws Throwable 
	 */
	public abstract void setValOfExtHeader(String paramNm, String val);

	/**
	 * 获得Header中的所有值
	 * @return
	 */
	public abstract IRow getAllValOfHeader();

	/**
	 * 设置Header中的所有值
	 * @param headDatas
	 */
	public abstract void setAllValOfHeader(IRow headDatas);
	
	/**
	 * 获得所有消息头中的数据
	 * @return
	 * @throws Throwable 
	 */
	public abstract IRow getAllValOfExtHeader();

	/**
	 * 设置所有消息头中的数据
	 * @param headDatas
	 */
	public abstract void setAllValOfExtHeader(IRow headDatas);
	
	/**
	 * 获得请求信息的参数值
	 * @param paramNm 参数名称
	 * @return
	 */
	public abstract String getParamOfReq(String paramNm);

	/**
	 * 设置请求信息的参数值
	 * @param paramNm 参数名称
	 * @param val 参数值
	 */
	public abstract void setParamOfReq(String paramNm, String val);

	/**
	 * 获得所有请求参数
	 * @return
	 * @throws Throwable 
	 */
	public abstract IRow getAllParamsOfReq();

	/**
	 * 设置所有请求业务数据
	 * @param reqParams
	 */
	public abstract void setAllParamOfReq(IRow reqParams);
	
	/**
	 * 获得所有请求业务数据
	 * @return
	 * @throws Throwable 
	 */
	public abstract Map<String, List<IRow>> getAllRowsOfReq();

	/**
	 * 设置所有请求业务数据
	 * @param reqRowsMap
	 */
	public abstract void setAllRowsOfReq(Map<String, List<IRow>> reqRowsMap);

	/**
	 * 获得请求节点的指定节点名称的数据
	 * @param objNm 指定名称
	 * @return rows 指定名称的所有数据
	 */
	public List<IRow> getRowsOfReq(String objNm);
	
	/**
	 * 设置请求节点的指定节点名称的数据
	 * @param objNm
	 * @param rows
	 */
	public void setRowsOfReq(String objNm,  List<IRow> rows);

	/**
	 * 获得响应信息的参数值
	 * @param paramNm 参数名称
	 * @return
	 */
	public abstract String getParamOfResp(String paramNm);

	/**
	 * 设置响应信息的参数值
	 * @param paramNm 参数名称
	 * @param val 参数值
	 */
	public abstract void setParamOfResp(String paramNm, String val);

	/**
	 * 获得所有请求参数
	 * @return
	 * @throws Throwable 
	 */
	public abstract IRow getAllParamsOfResp();

	/**
	 * 设置所有响应参数
	 * @param respParams
	 */
	public abstract void setAllParamsOfResp(IRow respParams);
	
	
	/**
	 * 从响应段中获取数据
	 * @param paramNm 数据项名称
	 */
	public abstract List<IRow> getRowsOfResp(String paramNm);

	/**
	 * 设置响应节点的指定节点名称的数据
	 * @param paramNm 指定节点名称
	 * @param rows 数据集
	 */
	public abstract void setRowsOfResp (String paramNm, List<IRow> rows);
	
	/**
	 * 获得所有响应业务数据
	 * @return
	 * @throws Throwable 
	 */
	public abstract Map<String, List<IRow>> getAllRowsOfResp();
	
	/**
	 * 设置所有响应业务数据
	 * @param respRowsMap
	 */
	public abstract void setAllRowsOfResp(Map<String, List<IRow>> respRowsMap);

	/**
	 * 输出到字节数组中
	 * @return 字节数组
	 * @throws IOException 
	 * @throws Throwable
	 */
	public abstract byte[] writeToBytes() throws IOException;

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getRequestDate()
	 */
	public String getRequestDate();

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getErrorCode()
	 */
	public String getErrorCode();

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getResponseDate()
	 */
	public String getResponseDate();

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getErrorDesc()
	 */
	public String getErrorDesc();

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getReturnCode()
	 */
	public String getReturnCode();

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getSessionId()
	 */
	public String getSessionId();

	/**
	 * @param requestDate
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setRequestDate(java.lang.String)
	 */
	public void setRequestDate(String requestDate);

	/**
	 * @param errorCode
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setErrorCode(java.lang.String)
	 */
	public void setErrorCode(String errorCode);

	/**
	 * @param responseDate
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setResponseDate(java.lang.String)
	 */
	public void setResponseDate(String responseDate);

	/**
	 * @param errorDesc
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setErrorDesc(java.lang.String)
	 */
	public void setErrorDesc(String errorDesc);

	/**
	 * @param returnCode
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setReturnCode(java.lang.String)
	 */
	public void setReturnCode(String returnCode);

	/**
	 * @param sessionId
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setSessionid(java.lang.String)
	 */
	public void setSessionId(String sessionId);
	
	/**
	 * 清除Request节点中的内容
	 */
	public void clearRequest();

	/**
	 * 清除Response节点中的内容
	 */
	public void clearResponse();


	/**
	 * 在request中设置单行记录
	 * @param paramNm 实体名称
	 * @param row 记录
	 */
	public void setRowOfReq(String paramNm, IRow row);

	/**
	 * 在response中设置单行记录
	 * @param paramNm 实体名称
	 * @param rows 记录
	 */
	public void setRowOfResp(String paramNm, IRow row);


	/**
	 * 取请求响应中的节点第一条记录
	 * @param paramNm
	 * @return
	 */
	public IRow getRowOfResp(String paramNm);

	/**
	 * 获取请求信息中的节点第一条记录
	 * @param paramNm 节点名称
	 * @return 记录
	 */
	public IRow getRowOfReq(String paramNm);


	/**
	 * 从请求信息中获取指定表的数据
	 * @param tableNames 指定表名
	 * @return 指定表的数据
	 * @throws Throwable 
	 */
	public Map<String, List<IRow>> getRowsOfReq(String[] tableNames);

	/**
	 * 从响应信息中获取指定表的数据
	 * @param tableNames 指定表名
	 * @return 指定表的数据
	 * @throws Throwable 
	 */
	public Map<String, List<IRow>> getRowsOfResp(String[] tableNames);

	/**
	 * 返回空的mo对象
	 * @return
	 */
	public IMessageObject emptyMo();
	
}