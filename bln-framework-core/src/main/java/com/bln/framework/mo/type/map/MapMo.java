/**
 * @author gengw
 * Created on 2012-03-12
 */
package com.bln.framework.mo.type.map;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bln.framework.mo.BaseMo;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.row.Row;
import com.bln.framework.util.asserter.Assert;

/**
 * 使用MAP存储的消息对象
 */
public class MapMo extends BaseMo implements IMessageObject, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6216154095527370507L;

	/**
	 * 服务标识
	 */
	String serviceId = null;

	/**
	 * 请求时间
	 */
	String requestDate = null;
	
	/**
	 * 返回码
	 */
	String returnCode = null;
	
	/**
	 * 错误码
	 */
	String errorCode = null;

	/**
	 * 错误描述
	 */
	String errorDesc = null;
	
	/**
	 * 响应时间
	 */
	String responseDate = null;
	
	/**
	 * sessionId
	 */
	String sessionId = null;

	/**
	 * 消息头
	 */
	protected final IRow headDatas = new Row();
	
	/**
	 * 扩展消息头
	 */
	protected final IRow extHeadDatas = new Row();
	
	/**
	 * 请求业务数据
	 */
	@SuppressWarnings("unchecked")
	public final Map<String, List<IRow>> reqDatas = ListOrderedMap.decorate(new HashMap<String, List<IRow>>());

	/**
	 * 请求参数数据
	 */
	protected final IRow reqParams = new Row();

	/**
	 * 响应业务数据
	 */
	@SuppressWarnings("unchecked")
	public final Map<String, List<IRow>> respDatas = ListOrderedMap.decorate(new HashMap<String, List<IRow>>());
	
	/**
	 * 响应参数数据
	 */
	protected final IRow respParams = new Row();
	
	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#getServiceId()
	 */
	public String getServiceId() {
		return serviceId;
	}

	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#setServiceId(java.lang.String)
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	/**
	 * @return the requestDate
	 */
	public String getRequestDate() {
		return requestDate;
	}

	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * @return the returnCode
	 */
	public String getReturnCode() {
		return returnCode;
	}

	/**
	 * @param returnCode the returnCode to set
	 */
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * @return the responseDate
	 */
	public String getResponseDate() {
		return responseDate;
	}

	/**
	 * @param responseDate the responseDate to set
	 */
	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#getValOfHeader(java.lang.String)
	 */
	public String getValueOfExtHeader(String paramNm){
		Assert.paramIsNotNull(paramNm, "paramNm");
		return extHeadDatas.getValue(paramNm);
	}
	
	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#setValOfHeader(java.lang.String, java.lang.String)
	 */
	public void setValOfExtHeader(String paramNm, String val){
		Assert.paramIsNotNull(paramNm, "paramNm");
		extHeadDatas.setValue(paramNm, val);
	}

	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#getParamOfReq(java.lang.String)
	 */
	public String getParamOfReq(String paramNm){
		Assert.paramIsNotNull(paramNm, "paramNm");
		return this.reqParams.getValue(paramNm);
	}
		
	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#setParamOfReq(java.lang.String, java.lang.String)
	 */
	public void setParamOfReq(String paramNm, String val){
		Assert.paramIsNotNull(paramNm, "paramNm");
		this.reqParams.setValue(paramNm, val);
	}
	
	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#getObjOfReq(java.lang.String)
	 */
	public List<IRow> getRowsOfReq(String paramNm){
		Assert.paramIsNotNull(paramNm, "paramNm");
		return (List<IRow>)this.reqDatas.get(paramNm);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#setRowsOfReq(java.lang.String, java.util.List)
	 */
	public void setRowsOfReq(String objNm, List<IRow> obj){
		Assert.paramIsNotNull(objNm, "objNm");
		this.reqDatas.put(objNm, obj);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#getAllValOfHeader()
	 */
	public IRow getAllValOfHeader() {
		return this.headDatas;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#setAllValOfHeader()
	 */
	public void setAllValOfHeader(IRow headDatas) {
		this.headDatas.importRow(headDatas);
	}
	
	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#getHeadObjs()
	 */
	public IRow getAllValOfExtHeader() {
		return extHeadDatas;
	}

	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#setHeadObjs(java.util.Map)
	 */
	public void setAllValOfExtHeader(IRow headDatas) {
		this.extHeadDatas.importRow(headDatas);
	}

	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#getReqObjs()
	 */
	public Map<String, List<IRow>> getAllRowsOfReq() {
		return reqDatas;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#setAllRowsOfReq(java.util.Map)
	 */
	public void setAllRowsOfReq(Map<String, List<IRow>> reqObjs) {
		this.reqDatas.clear();
		if(reqObjs != null && !reqObjs.isEmpty()){
			this.reqDatas.putAll(reqObjs);	
		}
	}

	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#getReqParams()
	 */
	public IRow getAllParamsOfReq() {
		return reqParams;
	}

	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#setReqParams(java.util.Map)
	 */
	public void setAllParamOfReq(IRow reqParams) {
		this.reqParams.importRow(reqParams);
	}

	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#getParamOfResp(java.lang.String)
	 */
	public String getParamOfResp(String paramNm){
		Assert.paramIsNotNull(paramNm, "paramNm");
		return this.respParams.getValue(paramNm);
	}
	
	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#setParamOfResp(java.lang.String, java.lang.String)
	 */
	public void setParamOfResp(String paramNm, String val){
		Assert.paramIsNotNull(paramNm, "paramNm");
		this.respParams.setValue(paramNm, val);
	}
			
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#setAllParamsOfResp(com.bln.framework.persist.row.IRow)
	 */
	public void setAllParamsOfResp(IRow respParams) {
		this.respParams.importRow(respParams);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#getAllParamsOfResp()
	 */
	public IRow getAllParamsOfResp() {
		return this.respParams;
	}
	
	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#getRowsOfResp(java.lang.String)
	 */
	public List<IRow> getRowsOfResp(String paramNm){
		return this.respDatas.get(paramNm);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#setRowsOfResp(java.lang.String, java.util.List)
	 */
	public void setRowsOfResp(String paramNm, List<IRow> rows) {
		Assert.paramIsNotNull(paramNm, "paramNm");
		respDatas.put(paramNm, rows);
	}
	
	/* (non-Javadoc)
	 * @see edi.msg.IMessageObject#getRespObjs()
	 */
	public Map<String, List<IRow>> getAllRowsOfResp() {
		return respDatas;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#setAllRowsOfResp(java.util.Map)
	 */
	public void setAllRowsOfResp(Map<String, List<IRow>> respObjs) {
		this.respDatas.clear();
		if(respObjs != null && !respObjs.isEmpty()){
			this.respDatas.putAll(respObjs);
		}
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#clearRequest()
	 */
	public void clearRequest(){
		this.reqDatas.clear();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#clearResponse()
	 */
	public void clearResponse(){
		this.respDatas.clear();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		
		//定义描述信息
		StringBuilder info = new StringBuilder("MessageObject info : \r\n");
		
		//1.serviceId
		info.append("serviceId = ").append(this.serviceId).append("\r\n");
		
		//2.reqeust
		info.append("request info :\r\n").append(this.reqDatas).append("\r\n");
		
		//3.param of request
		info.append("param of request info :\r\n").append(this.reqParams).append("\r\n");
		
		//4.response
		info.append("response info :\r\n").append(this.respDatas).append("\r\n");
		
		//5.param of response
		info.append("param of response info : \r\n").append(this.respParams).append("\r\n");

		//返回描述信息
		return info.toString();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#writeToBytes()
	 */
	public byte[] writeToBytes() throws IOException{
		
		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		ObjectOutputStream ops = new  ObjectOutputStream(ostream);
		
		ops.writeObject(this);
		ops.flush();
		ops.close();
		
		byte[] bytes = ostream.toByteArray();
		ostream.close();
		
		return bytes;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#emptyMo()
	 */
	public IMessageObject emptyMo() {
		return new MapMo();
	}


}