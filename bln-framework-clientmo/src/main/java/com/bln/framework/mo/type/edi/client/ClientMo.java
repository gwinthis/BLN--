/**
 * @author gengw
 * Created at 2012-03-27
 */
package com.bln.framework.mo.type.edi.client;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bln.framework.edi.stru.client.IClientFileStru;
import com.bln.framework.exception.DeveloperConfigErrorException;
import com.bln.framework.mo.BaseMo;
import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.row.IRow;

/**
 * 使用ClientXML对象存储的消息对象
 */
public class ClientMo extends BaseMo implements IClientMo{
	
	/**
	 * 客户端报文对象
	 */
	public IClientFileStru ediFileStru = null;

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getServiceId()
	 */
	public String getServiceId(){
		return ediFileStru.getServiceId();
	}
	
	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getRequestDate()
	 */
	public String getRequestDate(){
		return ediFileStru.getRequestDate();
	}

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getErrorCode()
	 */
	public String getErrorCode(){
		return ediFileStru.getErrorCode();
	}

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getResponseDate()
	 */
	public String getResponseDate(){
		return ediFileStru.getResponseDate();
	}

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getErrorDesc()
	 */
	public String getErrorDesc(){
		return ediFileStru.getErrorDesc();
	}

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getReturnCode()
	 */
	public String getReturnCode(){
		return ediFileStru.getReturnCode();
	}

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getSessionId()
	 */
	public String getSessionId(){
		return ediFileStru.getSessionId();
	}

	/**
	 * @param requestDate
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setRequestDate(java.lang.String)
	 */
	public void setRequestDate(String requestDate) {
		ediFileStru.setRequestDate(requestDate);
	}

	/**
	 * @param errorCode
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setErrorCode(java.lang.String)
	 */
	public void setErrorCode(String errorCode) {
		ediFileStru.setErrorCode(errorCode);
	}

	/**
	 * @param responseDate
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setResponseDate(java.lang.String)
	 */
	public void setResponseDate(String responseDate) {
		ediFileStru.setResponseDate(responseDate);
	}

	/**
	 * @param errorDesc
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setErrorDesc(java.lang.String)
	 */
	public void setErrorDesc(String errorDesc) {
		ediFileStru.setErrorDesc(errorDesc);
	}

	/**
	 * @param returnCode
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setReturnCode(java.lang.String)
	 */
	public void setReturnCode(String returnCode) {
		ediFileStru.setReturnCode(returnCode);
	}

	/**
	 * @param sessionId
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setSessionId(java.lang.String)
	 */
	public void setSessionId(String sessionId) {
		ediFileStru.setSessionId(sessionId);
	}


	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getAllParamsOfReq()
	 */
	public IRow getAllParamsOfReq(){
		return ediFileStru.getAllParamsOfReq();
	}

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getAllParamsOfResp()
	 */
	public IRow getAllParamsOfResp(){
		return ediFileStru.getAllParamsOfResp();
	}

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getAllRowsOfReq()
	 */
	public Map<String, List<IRow>> getAllRowsOfReq(){
		return ediFileStru.getAllRowsOfReq();
	}

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getAllRowsOfResp()
	 */
	public Map<String, List<IRow>> getAllRowsOfResp(){
		return ediFileStru.getAllRowsOfResp();
	}

	/**
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getAllValOfExtHeader()
	 */
	public IRow getAllValOfExtHeader(){
		return ediFileStru.getAllValOfExtHeader();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#getAllValOfHeader()
	 */
	public IRow getAllValOfHeader() {
		return ediFileStru.getAllValOfHeader();
	}
	
	/**
	 * @param paramNm
	 * @return
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getParamOfReq(java.lang.String)
	 */
	public String getParamOfReq(String paramNm) {
		return ediFileStru.getParamOfReq(paramNm);
	}

	/**
	 * @param paramNm
	 * @return
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getParamOfResp(java.lang.String)
	 */
	public String getParamOfResp(String paramNm) {
		return ediFileStru.getParamOfResp(paramNm);
	}

	

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.BaseMo#getRowOfResp(String)
	 */
	public IRow getRowOfResp(String paramNm) {
		return null;
	}
	
	/**
	 * @param paramNm
	 * @return
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getRowsOfReq(java.lang.String)
	 */
	public List<IRow> getRowsOfReq(String paramNm) {
		return ediFileStru.getRowsOfReq(paramNm);
	}

	/**
	 * @param paramNm
	 * @return
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getRowsOfResp(java.lang.String)
	 */
	public List<IRow> getRowsOfResp(String paramNm) {
		return ediFileStru.getRowsOfResp(paramNm);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.BaseMo#getRowsOfReq(java.lang.String[])
	 */
	public Map<String, List<IRow>> getRowsOfReq(String[] tableNames){
		
		return ediFileStru.getRowsOfReq(tableNames);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.BaseMo#getRowsOfResp(java.lang.String[])
	 */
	public Map<String, List<IRow>> getRowsOfResp(String[] tableNames){
		return ediFileStru.getRowsOfResp(tableNames);
	}
	
	/**
	 * @param paramNm
	 * @return
	 * @throws Throwable
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#getValOfExtHeader(java.lang.String)
	 */
	public String getValueOfExtHeader(String paramNm){
		return ediFileStru.getValOfExtHeader(paramNm);
	}

	/**
	 * @param serviceId
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setServiceId(java.lang.String)
	 */
	public void setServiceId(String serviceId) {
		ediFileStru.setServiceId(serviceId);
	}

	/**
	 * @param row
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setAllParamOfReq(com.bln.framework.persist.row.IRow)
	 */
	public void setAllParamOfReq(IRow row) {
		ediFileStru.setAllParamOfReq(row);
	}

	/**
	 * @param row
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setAllParamsOfResp(com.bln.framework.persist.row.IRow)
	 */
	public void setAllParamsOfResp(IRow row) {
		ediFileStru.setAllParamsOfResp(row);
	}

	/**
	 * @param rowsMap
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setAllRowsOfReq(java.util.Map)
	 */
	public void setAllRowsOfReq(Map<String, List<IRow>> rowsMap) {
		ediFileStru.setAllRowsOfReq(rowsMap);
	}

	/**
	 * @param rowsMap
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setAllRowsOfResp(java.util.Map)
	 */
	public void setAllRowsOfResp(Map<String, List<IRow>> rowsMap) {
		ediFileStru.setAllRowsOfResp(rowsMap);
	}

	/**
	 * @param row
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setAllValOfExtHeader(com.bln.framework.persist.row.IRow)
	 */
	public void setAllValOfExtHeader(IRow row) {
		ediFileStru.setAllValOfExtHeader(row);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#setAllValOfHeader(com.bln.framework.persist.row.IRow)
	 */
	public void setAllValOfHeader(IRow headDatas) {
		ediFileStru.setAllValOfHeader(headDatas);
	}
	
	/**
	 * @param paramNm
	 * @param val
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setParamOfReq(java.lang.String, java.lang.String)
	 */
	public void setParamOfReq(String paramNm, String val) {
		ediFileStru.setParamOfReq(paramNm, val);
	}

	/**
	 * @param paramNm
	 * @param val
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setParamOfResp(java.lang.String, java.lang.String)
	 */
	public void setParamOfResp(String paramNm, String val) {
		ediFileStru.setParamOfResp(paramNm, val);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.BaseMo#setRowOfReq(String, IRow)
	 */
	public void setRowOfReq(String paramNm, IRow row) {
		ediFileStru.setRowOfReq(paramNm, row);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.BaseMo#setRowOfResp(String, IRow)
	 */
	public void setRowOfResp(String paramNm, IRow row) {
		ediFileStru.setRowOfResp(paramNm, row);
	}
	
	/**
	 * @param paramNm
	 * @param rows
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setRowsOfReq(java.lang.String, java.util.List)
	 */
	public void setRowsOfReq(String paramNm, List<IRow> rows) {
		ediFileStru.setRowsOfReq(paramNm, rows);
	}

	/**
	 * @param paramNm
	 * @param rows
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setRowsOfResp(java.lang.String, java.util.List)
	 */
	public void setRowsOfResp(String paramNm, List<IRow> rows) {
		ediFileStru.setRowsOfResp(paramNm, rows);
	}

	/**
	 * @param paramNm
	 * @param val
	 * @throws Throwable
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#setValOfExtHeader(java.lang.String, java.lang.String)
	 */
	public void setValOfExtHeader(String paramNm, String val){
		ediFileStru.setValOfExtHeader(paramNm, val);
	}

	/**
	 * @return
	 * @see com.bln.framework.filestru.xml.XMLFileBase#toString()
	 */
	public String toString() {
		return ediFileStru.toString();
	}

	/**
	 * @return
	 * @throws IOException 
	 * @throws Throwable
	 * @see com.bln.framework.filestru.xml.XMLFileBase#writeToBytes()
	 */
	public byte[] writeToBytes() throws IOException{
		return ediFileStru.writeToBytes();
	}

	/**
	 * @throws Throwable
	 * @see com.bln.framework.edi.stru.client.xml.ClientXMLFileStru#initFile()
	 */
	public void initFile(){
		ediFileStru.initFile();
	}

	/**
	 * @param fileData
	 * @throws Throwable
	 * @see com.bln.framework.filestru.xml.XMLFileBase#readFromData(byte[])
	 */
	public void readFromData(byte[] fileData){
		ediFileStru.readFromData(fileData);
	}

	/**
	 * @return the clientXmlFile
	 */
	public IClientFileStru getEdiFileStru() {
		return ediFileStru;
	}

	/**
	 * @param clientXmlFile the clientXmlFile to set
	 */
	public void setEdiFileStru(IClientFileStru ediFileStru) {
		this.ediFileStru = ediFileStru;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#clearRequest()
	 */
	public void clearRequest() {
		this.ediFileStru.clearRequest();
		
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#clearResponse()
	 */
	public void clearResponse() {
		this.ediFileStru.clearResponse();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#emptyMo()
	 */
	public IMessageObject emptyMo() {
		
		ClientMo mo = new ClientMo();
		try {
			IClientFileStru clientFileStru = this.ediFileStru.getClass().newInstance();
			clientFileStru.setEncoding(ediFileStru.getEncoding());
			
			mo.setEdiFileStru(clientFileStru);
		} catch (Throwable e) {
			DeveloperConfigErrorException dce = new DeveloperConfigErrorException("new " + this.ediFileStru.getClass() + " error!");
			dce.initCause(e);
		}
		return mo;
	}
}
