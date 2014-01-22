/**
 * @author gengw
 * Created on 2012-03-13
 */
package com.bln.framework.mo.buildable;

import java.util.List;
import java.util.Map;

import com.bln.framework.persist.row.IRow;

/**
 * 可生成MO的接口
 */
public interface IBuildMOable {
	
	/**
	 * 获得请求实体
	 * @return
	 */
	public String getServiceId();

	/**
	 * 获取请求时间
	 * @return 请求时间
	 * @
	 */
	public String getRequestDate();

	/**
	 * 获取返回码
	 * @return 返回码
	 * @
	 */
	public String getReturnCode();

	/**
	 * 获取错误码
	 * @return 错误码
	 */
	public String getErrorCode();

	/**
	 * 获取错误描述
	 * @return 错误描述
	 * @
	 */
	public String getErrorDesc();

	/**
	 * 获取响应时间
	 * @return 错误描述
	 * @
	 */
	public String getResponseDate();

	/**
	 * 获取SessionId
	 * @return SessionId
	 * @
	 */
	public String getSessionId();

	
	/**
	 * 获得Header中的所有值
	 * @return
	 */
	public abstract IRow getAllValOfHeader();

	/**
	 * 获得扩展消息头实体
	 * @return
	 */
	public IRow getAllValOfExtHeader();

	/**
	 * 获得请求实体
	 * @return
	 */
	public Map<String, List<IRow>> getAllRowsOfReq();

	/**
	 * 获得请求参数实体
	 * @return
	 */
	public IRow getAllParamsOfReq();

	/**
	 * 获得响应实体
	 * @return
	 */
	public Map<String, List<IRow>> getAllRowsOfResp();

	/**
	 * 获得响应参数实体
	 * @return
	 */
	public IRow getAllParamsOfResp();
	
	/**
	 * 从响应段中获取数据
	 * @param paramNm 数据项名称
	 */
	public List<IRow> getRowsOfResp(String paramNm);
	

	/**
	 * 获得消息头中的数据项
	 * @param paramNm 数据项名称
	 * @return 数据项值
	 * @
	 */
	public String getValOfExtHeader(String paramNm);

	/**
	 * 获得请求信息的参数值
	 * @param paramNm 参数名称
	 * @return
	 */
	public String getParamOfReq(String paramNm);

	/**
	 * 获得请求节点的指定节点名称的数据
	 * @param paramNm 指定名称
	 * @return rows 指定名称的所有数据
	 */
	public List<IRow> getRowsOfReq(String paramNm);

	/**
	 * 获得响应信息的参数值
	 * @param paramNm 参数名称
	 * @return 参数值
	 */
	public String getParamOfResp(String paramNm);
	
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
}
