/**
 * @author gengw
 * Created on 2012-03-13
 */
package com.bln.framework.edi.stru.client.xml;

import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.bln.framework.edi.stru.client.IClientFileStru;
import com.bln.framework.filestru.xml.bizdata.XMLFile4BizDataBase;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.util.asserter.Assert;


/**
 * <p>客户端报文结构</p>
 * <p>报文结构</p>
 * <p>
 * <?xml version="1.0" encoding="utf-8"?>
 *	<Message>
 *		<Header>
 *			<service_id></service_id>
 *			<request_date></request_date>
 *			<return_code>00：成功；30：失败</return_code>
 *			<response_code></response_code>
 *			<response_desc></response_desc>
 *			<response_date></response_date>
 *			<session_id></session_id>
 *			<transservice_id></transservice_id>
 *			<ext></ext>
 *		</Header>
 *		<Body>
			<request>
				<parameter>
				</parameter>
			</request>
			<response>
				<parameter>
				</parameter>
			</response>
		</Body>
	</Message>
	</p>
 */
public class ClientXMLFileStru extends XMLFile4BizDataBase implements IClientFileStru{
	
	/**
	 * 根节点 
	 * Message节点
	 */
	protected Element msg = null;
	
	/**
	 * Header节点
	 */
	protected Element header = null;

	/**
	 * ext节点
	 */
	protected Element extOfHeader = null;
	
	/**
	 * Body节点
	 */
	protected Element body = null;
	
	/**
	 * request节点
	 */
	protected Element request = null;
	
	/**
	 * response节点
	 */
	protected Element response = null;

	/**
	 * 请求参数的节点
	 */
	protected Element paramOfReq = null;

	/**
	 * 响应参数的节点
	 */
	protected Element paramOfResp = null;
	
	public ClientXMLFileStru(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.xml.XMLFileBase#buildFile()
	 */
	public void initFile(){
		
		//父对象初始化
		super.initFile();
		
		//初始化msg节点
		initMsg();
	}
	
	/**
	 * 初始化msg节点
	 */
	protected void initMsg(){
		
		//定义msg节点
		this.msg = this.document.addElement(MSG_NM);
		
		//初始化header节点
		initHeader();
		
		//初始化body节点
		initBody();
	}

	/**
	 * 初始化header节点
	 */
	protected void initHeader(){
		
		//定义Header节点
		this.header = this.msg.addElement(HEADER_NM);

		//初始化extOfHeader
		initExtOfHeader();
		
	}
	
	/**
	 * 初始化extOfHeader
	 */
	protected void initExtOfHeader(){
		
		//定义ext节点
		this.extOfHeader = this.header.addElement(EXT_NM);
	}
	
	/**
	 * 初始化body节点
	 */
	protected void initBody(){
		
		//定义Body节点
		this.body = this.msg.addElement(BODY_NM);
		
		//初始化请求节点
		initRequest();
		
		//初始化响应节点
		initResponse();
		
	}
	
	/**
	 * 初始化请求节点
	 */
	public void initRequest(){
		
		//定义request节点
		this.request = body.addElement(REQUEST_NM);
		
		//定义请求参数节点
		initParamOfRequest();
	}
	
	/**
	 * 初始化请求参数
	 */
	protected void initParamOfRequest(){
		
		//定义param of request节点
		this.paramOfReq = request.addElement(PARAMETER_NM);
	}
	
	/**
	 * 初始化响应节点
	 */
	protected void initResponse(){
		
		//定义response节点
		this.response = body.addElement(RESPONSE_NM);
		
		//定义响应参数节点
		initParamOfResponse();
	}
	
	/**
	 * 初始化响应参数
	 */
	protected void initParamOfResponse(){
		
		//定义param of response节点
		this.paramOfResp = response.addElement(PARAMETER_NM);
	}
	
	/* (non-Javadoc)
	 * @see edi.stru.xml.XMLEdiBase#initStru()
	 */
	@Override
	protected void initStru() {
		
		//获取根节点
		this.msg = this.document.getRootElement();
		
		//获取Header节点
		this.header = msg.element(HEADER_NM);
		if(header == null){
			this.initHeader();
		}else{
			this.extOfHeader = header.element(EXT_NM);
			if(extOfHeader == null){
				this.initExtOfHeader();
			}
		}
		
		//获取Body节点
		this.body = msg.element(BODY_NM);
		if(body == null){
			this.initBody();
		}else{
			
			//获取request节点
			this.request = body.element(REQUEST_NM);
			if(request == null ){
				this.initRequest();
			}else{
				this.paramOfReq = request.element(PARAMETER_NM);
				if(paramOfReq == null){
					this.initParamOfRequest();
				}
			}
			
			//获取response节点
			this.response = body.element(RESPONSE_NM);
			if(response == null){
				this.initResponse();
			}else{
				this.paramOfResp = response.element(PARAMETER_NM);
				if(paramOfResp == null){
					this.initParamOfResponse();
				}
			}
		}
	}
	
	/**
	 * 清除request信息
	 */
	public void clearRequest() {
		body.remove(request);
		this.initRequest();
	}

	/**
	 * 清除response信息
	 */
	public void clearResponse() {
		body.remove(response);
		this.initResponse();
	}
	
	/* (non-Javadoc)
	 * @see edi.stru.ICreateMO#getServiceId()
	 */
	public String getServiceId() {
		return this.getTxtOfElement(header, SERVICEID_NM);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.xml.IBuildClientFile#setServiceId(java.lang.String)
	 */
	public void setServiceId(String serviceId){

		this.setTxtOfElement(header, SERVICEID_NM, serviceId);
	}
	
	/**
	 * 获取请求时间
	 * @return 请求时间
	 * @throws Throwable
	 */
	public String getRequestDate() {
		return this.getTxtOfElement(header, REQUESTDATE_NM);
	}

	/**
	 * 设置请求时间
	 * @param requestDate 请求时间
	 */
	public void setRequestDate(String requestDate){
		this.setTxtOfElement(header, REQUESTDATE_NM, requestDate);
	}
	
	/**
	 * 获取返回码
	 * @return 返回码
	 * @throws Throwable
	 */
	public String getReturnCode() {
		return this.getTxtOfElement(header, RETURNCODE_NM);
	}

	/**
	 * 设置返回码
	 * @param returnCode 返回码
	 */
	public void setReturnCode(String returnCode){
		this.setTxtOfElement(header, RETURNCODE_NM, returnCode);
	}
	
	/**
	 * 获取错误码
	 * @return 错误码
	 * @throws Throwable
	 */
	public String getErrorCode() {
		return this.getTxtOfElement(header, ERRORCODE_NM);
	}

	/**
	 * 设置错误码
	 * @param errorCode 错误码
	 */
	public void setErrorCode(String errorCode){
		this.setTxtOfElement(header, ERRORCODE_NM, errorCode);
	}
	
	/**
	 * 获取错误描述
	 * @return 错误描述
	 * @throws Throwable
	 */
	public String getErrorDesc() {
		return this.getTxtOfElement(header, ERRORDESC_NM);
	}

	/**
	 * 设置错误描述
	 * @param errorDesc 错误描述
	 */
	public void setErrorDesc(String errorDesc){
		this.setTxtOfElement(header, ERRORDESC_NM, errorDesc);
	}
	
	/**
	 * 获取响应时间
	 * @return 错误描述
	 * @throws Throwable
	 */
	public String getResponseDate() {
		return this.getTxtOfElement(header, RESPONSEDATE_NM);
	}

	/**
	 * 设置响应时间
	 * @param responseDate 响应时间
	 */
	public void setResponseDate(String responseDate){
		this.setTxtOfElement(header, RESPONSEDATE_NM, responseDate);
	}
	
	/**
	 * 获取SessionId
	 * @return SessionId
	 * @throws Throwable
	 */
	public String getSessionId() {
		return this.getTxtOfElement(header, SESSIONID_NM);
	}

	/**
	 * 设置SessionId
	 * @param SessionId SessionId
	 */
	public void setSessionId(String sessionId){
		this.setTxtOfElement(header, SESSIONID_NM, sessionId);
	}
	
	/**
	 * 获得扩展消息头的ext节点中的数据项
	 * @param paramNm 数据项名称
	 * @return 数据项值
	 * @throws Throwable
	 */
	public String getValOfExtHeader(String paramNm) {
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		//获取header节点下的数据项
		String val = this.getTxtOfElement(extOfHeader, paramNm);
		
		//返回数据项值
		return val;
	}
		
	/**
	 * 获得扩展消息头中的数据项
	 * @param paramNm 数据项名称
	 * @param val 数据项值
	 * @throws Throwable
	 */
	public void setValOfExtHeader(String paramNm, String val) {
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		//设置值
		this.setTxtOfElement(extOfHeader, paramNm, val);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getAllValOfHeader()
	 */
	public IRow getAllValOfHeader() {
		
		//获取message节点下的header
		IRow row = this.getEntityOfElement(msg, HEADER_NM);

		//返回消息头映射
		return row;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#setAllValOfHeader(com.bln.framework.persist.row.IRow)
	 */
	public void setAllValOfHeader(IRow headDatas) {
		if(headDatas != null && !headDatas.isEmpty()){
			msg.remove(this.header);
			this.addRowOfElement(msg, HEADER_NM, headDatas);
		}
	}
	
	/* (non-Javadoc)
	 * @see edi.parser.client.ICreateMOable#getAllValOfHeader()
	 */
	public IRow getAllValOfExtHeader() {
		
		//获取header节点下的ext
		IRow row = this.getEntityOfElement(header, EXT_NM);

		//返回消息头映射
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.xml.IBuildClientFile#setHeadEntitys(com.bln.framework.persist.row.IRow)
	 */
	public void setAllValOfExtHeader(IRow row){

		if(row != null && !row.isEmpty()){
			header.remove(this.extOfHeader);
			this.addRowOfElement(header, EXT_NM, row);
		}
	}
	
	/**
	 * 获得请求信息的参数值
	 * @param paramNm 参数名称
	 * @return
	 */
	public String getParamOfReq(String paramNm){
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		//获取paramOfReq下的数据项
		String val = this.getTxtOfElement(this.paramOfReq, paramNm);
		
		//返回数据项值
		return val;
	}

	/**
	 * 设置请求信息的参数值
	 * @param paramNm 参数名称
	 * @param val 参数值
	 */
	public void setParamOfReq(String paramNm, String val){
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		//设置请求参数的数据项
		this.setTxtOfElement(this.paramOfReq, paramNm, val);	
	}

	
	/* (non-Javadoc)
	 * @see edi.stru.ICreateMO#getAllParamsOfReq()
	 */
	public IRow getAllParamsOfReq() {

		//获取request节点下的parameter
		IRow row = this.getEntityOfElement(request, PARAMETER_NM);
		
		//返回参数映射
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.xml.IBuildClientFile#setAllParamOfReq(com.bln.framework.persist.row.IRow)
	 */
	public void setAllParamOfReq(IRow row) {
		if(row != null && !row.isEmpty()){
			request.remove(this.paramOfReq);
			this.addRowOfElement(request, PARAMETER_NM, row);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see edi.stru.ICreateMO#getAllRowsOfReq()
	 */
	public Map<String, List<IRow>> getAllRowsOfReq() {
	
		//获取request节点下除了parameter以外的所有实体
		Map<String, List<IRow>> entitys = this.getAllEntityOfElement(request, false, PARAMETER_NM);

		//返回实体映射
		return entitys;
	}
		
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.xml.IBuildClientFile#setRequestEntitys(java.util.Map)
	 */
	public void setAllRowsOfReq(Map<String, List<IRow>> rowsMap){
		
		if(rowsMap != null && !rowsMap.isEmpty()){
			clearRequest();
			this.addRowsOfElement(request, rowsMap);
		}
	}
	
	/**
	 * 获得请求节点的指定节点名称的数据
	 * @param paramNm 指定名称
	 * @return rows 指定名称的所有数据
	 */
	public List<IRow> getRowsOfReq(String paramNm){
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		//返回结果集
		return this.getEntitysOfElement(request, paramNm);
	}
	
	/**
	 * 设置请求节点的指定节点名称的数据
	 * @param paramNm 数据集名称
	 * @param rows 记录集
	 */
	public void setRowsOfReq(String paramNm,  List<IRow> rows){
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		this.addRowsOfElement(request, paramNm, rows);
		
	}
	
	/**
	 * 设置请求节点的指定节点名称的数据
	 * @param paramNm 数据集名称
	 * @param row 记录
	 */
	public void setRowOfReq(String paramNm,  IRow row){
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		this.addRowOfElement(request, paramNm, row);
		
	}
	
	/**
	 * 获得响应信息的参数值
	 * @param paramNm 参数名称
	 * @return 参数值
	 */
	public String getParamOfResp(String paramNm){
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		//获取paramOfReq下的数据项
		String val = this.getTxtOfElement(this.paramOfResp, paramNm);
		
		//返回数据项值
		return val;
	}
	
	/**
	 * 设置响应信息的参数值
	 * @param paramNm 参数名称
	 * @param val 参数值
	 */
	public void setParamOfResp(String paramNm, String val){
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		this.setTxtOfElement(this.paramOfResp, paramNm, val);	
	}

	/* (non-Javadoc)
	 * @see edi.stru.ICreateMO#getAllParamsOfResp()
	 */
	public IRow getAllParamsOfResp() {
		
		//获取request节点下的parameter
		IRow row = this.getEntityOfElement(response, PARAMETER_NM);
		
		//返回参数映射
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.xml.IBuildClientFile#setAllParamsOfResp(com.bln.framework.persist.row.IRow)
	 */
	public void setAllParamsOfResp(IRow row){
		
		if(row != null && !row.isEmpty()){
			response.remove(this.paramOfResp);
			this.addRowOfElement(response, PARAMETER_NM, row);
		}
	}

	/**
	 * 从响应段中获取数据
	 * @param paramNm 数据项名称
	 */
	public List<IRow> getRowsOfResp(String paramNm){
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		//返回结果集
		return this.getEntitysOfElement(response, paramNm);
		
	}
	
	/**
	 * 设置请求节点的指定节点名称的数据
	 * @param paramNm 数据集名称
	 * @param rows 记录集
	 */
	public void setRowsOfResp(String paramNm,  List<IRow> rows){
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		this.addRowsOfElement(response, paramNm, rows);
	}
	
	/**
	 * 设置请求节点的指定节点名称的数据
	 * @param paramNm 数据集名称
	 * @param row 记录
	 */
	public void setRowOfResp(String paramNm,  IRow row){
		
		//校验参数
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		this.addRowOfElement(response, paramNm, row);
	}
	
	
	/* (non-Javadoc)
	 * @see edi.stru.ICreateMO#getAllRowsOfResp()
	 */
	public Map<String, List<IRow>> getAllRowsOfResp() {
		
		//获取response节点下除了parameter以外的所有实体
		Map<String, List<IRow>> entitys = this.getAllEntityOfElement(response, false, PARAMETER_NM);

		//返回实体映射
		return entitys;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.xml.IBuildClientFile#setAllRowsOfResp(java.util.Map)
	 */
	public void setAllRowsOfResp(Map<String, List<IRow>> rowsMap){
		
		//如果rowsMap为空直接返回		
		if(rowsMap != null && !rowsMap.isEmpty()){
			clearResponse();
			this.addRowsOfElement(response, rowsMap);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#getRowsOfReq(java.lang.String[])
	 */
	public Map<String, List<IRow>> getRowsOfReq(String[] tableNames) {
		
		return this.getAllEntityOfElement(request, true, tableNames);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.IClientFileBuildable#getRowsOfResp(java.lang.String[])
	 */
	public Map<String, List<IRow>> getRowsOfResp(String[] tableNames) {
		
		return this.getAllEntityOfElement(response, true, tableNames);
	}


}
