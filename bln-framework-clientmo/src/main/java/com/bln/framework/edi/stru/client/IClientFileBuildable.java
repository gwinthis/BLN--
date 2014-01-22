/**
 * @author gengw
 * Created on 2012-03-22
 */
package com.bln.framework.edi.stru.client;

import java.util.List;
import java.util.Map;

import com.bln.framework.filestru.IFileBuildable;
import com.bln.framework.persist.row.IRow;

/**
 * ClientFile构造器的接口
 */
public interface IClientFileBuildable extends IFileBuildable {

	/**
	 * 在header节点中设置SERVICEID的值
	 * @param serviceId的值
	 * @throws Throwable
	 */
	public abstract void setServiceId(String serviceId);

	/**
	 * 设置请求时间
	 * @param requestDate 请求时间
	 */
	public void setRequestDate(String requestDate);

	/**
	 * 设置返回码
	 * @param returnCode 返回码
	 */
	public void setReturnCode(String returnCode);

	/**
	 * 设置错误码
	 * @param errorCode 错误码
	 */
	public void setErrorCode(String errorCode);

	/**
	 * 设置错误描述
	 * @param errorDesc 错误描述
	 */
	public void setErrorDesc(String errorDesc);

	/**
	 * 设置响应时间
	 * @param responseDate 响应时间
	 */
	public void setResponseDate(String responseDate);

	/**
	 * 设置SessionId
	 * @param SessionId SessionId
	 */
	public void setSessionId(String sessionId);

	/**
	 * 设置Header中的所有值
	 * @param headDatas
	 */
	public abstract void setAllValOfHeader(IRow headDatas);

	/**
	 * 设置扩展头节点的值
	 * @param row 头节点的数据
	 */
	public abstract void setAllValOfExtHeader(IRow row);

	/**
	 * 在request节点中添加记录集
	 * @param rowsMap 记录集的名称和内容集合
	 */
	public abstract void setAllRowsOfReq(Map<String, List<IRow>> rowsMap);

	/**
	 * 设置请求参数的所有值
	 * @param row 参数所有值
	 * @throws Throwable
	 */
	public abstract void setAllParamOfReq(IRow row);

	/**
	 * 在response节点中添加记录集
	 * @param rowsMap 记录集的名称和内容集合
	 */
	public abstract void setAllRowsOfResp(Map<String, List<IRow>> rowsMap);

	/**
	 * 设置响应参数的所有值
	 * @param row 参数所有值
	 * @throws Throwable
	 */
	public abstract void setAllParamsOfResp(IRow row);

	
	/**
	 * 获得消息头中的数据项
	 * @param paramNm 数据项名称
	 * @param val 数据项值
	 * @throws Throwable
	 */
	public void setValOfExtHeader(String paramNm, String val);

	/**
	 * 设置请求信息的参数值
	 * @param paramNm 参数名称
	 * @param val 参数值
	 */
	public void setParamOfReq(String paramNm, String val);

	/**
	 * 设置请求节点的指定节点名称的数据
	 * @param paramNm 数据集名称
	 * @param rows 记录集
	 */
	public void setRowsOfReq(String paramNm, List<IRow> rows);

	/**
	 * 设置响应信息的参数值
	 * @param paramNm 参数名称
	 * @param val 参数值
	 */
	public void setParamOfResp(String paramNm, String val);

	/**
	 * 设置请求节点的指定节点名称的数据
	 * @param paramNm 数据集名称
	 * @param rows 记录集
	 */
	public void setRowsOfResp(String paramNm, List<IRow> rows);

	/**
	 * 设置请求节点的指定节点名称的数据
	 * @param paramNm 数据集名称
	 * @param row 记录
	 */
	public void setRowOfReq(String paramNm, IRow row);

	/**
	 * 设置请求节点的指定节点名称的数据
	 * @param paramNm 数据集名称
	 * @param row 记录
	 */
	public void setRowOfResp(String paramNm, IRow row);
	
	/**
	 * 清除request信息
	 */
	public void clearRequest();

	/**
	 * 清除response信息
	 */
	public void clearResponse();
}