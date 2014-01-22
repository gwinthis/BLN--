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
 * <p>�ͻ��˱��Ľṹ</p>
 * <p>���Ľṹ</p>
 * <p>
 * <?xml version="1.0" encoding="utf-8"?>
 *	<Message>
 *		<Header>
 *			<service_id></service_id>
 *			<request_date></request_date>
 *			<return_code>00���ɹ���30��ʧ��</return_code>
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
	 * ���ڵ� 
	 * Message�ڵ�
	 */
	protected Element msg = null;
	
	/**
	 * Header�ڵ�
	 */
	protected Element header = null;

	/**
	 * ext�ڵ�
	 */
	protected Element extOfHeader = null;
	
	/**
	 * Body�ڵ�
	 */
	protected Element body = null;
	
	/**
	 * request�ڵ�
	 */
	protected Element request = null;
	
	/**
	 * response�ڵ�
	 */
	protected Element response = null;

	/**
	 * ��������Ľڵ�
	 */
	protected Element paramOfReq = null;

	/**
	 * ��Ӧ�����Ľڵ�
	 */
	protected Element paramOfResp = null;
	
	public ClientXMLFileStru(){
		super();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.filestru.xml.XMLFileBase#buildFile()
	 */
	public void initFile(){
		
		//�������ʼ��
		super.initFile();
		
		//��ʼ��msg�ڵ�
		initMsg();
	}
	
	/**
	 * ��ʼ��msg�ڵ�
	 */
	protected void initMsg(){
		
		//����msg�ڵ�
		this.msg = this.document.addElement(MSG_NM);
		
		//��ʼ��header�ڵ�
		initHeader();
		
		//��ʼ��body�ڵ�
		initBody();
	}

	/**
	 * ��ʼ��header�ڵ�
	 */
	protected void initHeader(){
		
		//����Header�ڵ�
		this.header = this.msg.addElement(HEADER_NM);

		//��ʼ��extOfHeader
		initExtOfHeader();
		
	}
	
	/**
	 * ��ʼ��extOfHeader
	 */
	protected void initExtOfHeader(){
		
		//����ext�ڵ�
		this.extOfHeader = this.header.addElement(EXT_NM);
	}
	
	/**
	 * ��ʼ��body�ڵ�
	 */
	protected void initBody(){
		
		//����Body�ڵ�
		this.body = this.msg.addElement(BODY_NM);
		
		//��ʼ������ڵ�
		initRequest();
		
		//��ʼ����Ӧ�ڵ�
		initResponse();
		
	}
	
	/**
	 * ��ʼ������ڵ�
	 */
	public void initRequest(){
		
		//����request�ڵ�
		this.request = body.addElement(REQUEST_NM);
		
		//������������ڵ�
		initParamOfRequest();
	}
	
	/**
	 * ��ʼ���������
	 */
	protected void initParamOfRequest(){
		
		//����param of request�ڵ�
		this.paramOfReq = request.addElement(PARAMETER_NM);
	}
	
	/**
	 * ��ʼ����Ӧ�ڵ�
	 */
	protected void initResponse(){
		
		//����response�ڵ�
		this.response = body.addElement(RESPONSE_NM);
		
		//������Ӧ�����ڵ�
		initParamOfResponse();
	}
	
	/**
	 * ��ʼ����Ӧ����
	 */
	protected void initParamOfResponse(){
		
		//����param of response�ڵ�
		this.paramOfResp = response.addElement(PARAMETER_NM);
	}
	
	/* (non-Javadoc)
	 * @see edi.stru.xml.XMLEdiBase#initStru()
	 */
	@Override
	protected void initStru() {
		
		//��ȡ���ڵ�
		this.msg = this.document.getRootElement();
		
		//��ȡHeader�ڵ�
		this.header = msg.element(HEADER_NM);
		if(header == null){
			this.initHeader();
		}else{
			this.extOfHeader = header.element(EXT_NM);
			if(extOfHeader == null){
				this.initExtOfHeader();
			}
		}
		
		//��ȡBody�ڵ�
		this.body = msg.element(BODY_NM);
		if(body == null){
			this.initBody();
		}else{
			
			//��ȡrequest�ڵ�
			this.request = body.element(REQUEST_NM);
			if(request == null ){
				this.initRequest();
			}else{
				this.paramOfReq = request.element(PARAMETER_NM);
				if(paramOfReq == null){
					this.initParamOfRequest();
				}
			}
			
			//��ȡresponse�ڵ�
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
	 * ���request��Ϣ
	 */
	public void clearRequest() {
		body.remove(request);
		this.initRequest();
	}

	/**
	 * ���response��Ϣ
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
	 * ��ȡ����ʱ��
	 * @return ����ʱ��
	 * @throws Throwable
	 */
	public String getRequestDate() {
		return this.getTxtOfElement(header, REQUESTDATE_NM);
	}

	/**
	 * ��������ʱ��
	 * @param requestDate ����ʱ��
	 */
	public void setRequestDate(String requestDate){
		this.setTxtOfElement(header, REQUESTDATE_NM, requestDate);
	}
	
	/**
	 * ��ȡ������
	 * @return ������
	 * @throws Throwable
	 */
	public String getReturnCode() {
		return this.getTxtOfElement(header, RETURNCODE_NM);
	}

	/**
	 * ���÷�����
	 * @param returnCode ������
	 */
	public void setReturnCode(String returnCode){
		this.setTxtOfElement(header, RETURNCODE_NM, returnCode);
	}
	
	/**
	 * ��ȡ������
	 * @return ������
	 * @throws Throwable
	 */
	public String getErrorCode() {
		return this.getTxtOfElement(header, ERRORCODE_NM);
	}

	/**
	 * ���ô�����
	 * @param errorCode ������
	 */
	public void setErrorCode(String errorCode){
		this.setTxtOfElement(header, ERRORCODE_NM, errorCode);
	}
	
	/**
	 * ��ȡ��������
	 * @return ��������
	 * @throws Throwable
	 */
	public String getErrorDesc() {
		return this.getTxtOfElement(header, ERRORDESC_NM);
	}

	/**
	 * ���ô�������
	 * @param errorDesc ��������
	 */
	public void setErrorDesc(String errorDesc){
		this.setTxtOfElement(header, ERRORDESC_NM, errorDesc);
	}
	
	/**
	 * ��ȡ��Ӧʱ��
	 * @return ��������
	 * @throws Throwable
	 */
	public String getResponseDate() {
		return this.getTxtOfElement(header, RESPONSEDATE_NM);
	}

	/**
	 * ������Ӧʱ��
	 * @param responseDate ��Ӧʱ��
	 */
	public void setResponseDate(String responseDate){
		this.setTxtOfElement(header, RESPONSEDATE_NM, responseDate);
	}
	
	/**
	 * ��ȡSessionId
	 * @return SessionId
	 * @throws Throwable
	 */
	public String getSessionId() {
		return this.getTxtOfElement(header, SESSIONID_NM);
	}

	/**
	 * ����SessionId
	 * @param SessionId SessionId
	 */
	public void setSessionId(String sessionId){
		this.setTxtOfElement(header, SESSIONID_NM, sessionId);
	}
	
	/**
	 * �����չ��Ϣͷ��ext�ڵ��е�������
	 * @param paramNm ����������
	 * @return ������ֵ
	 * @throws Throwable
	 */
	public String getValOfExtHeader(String paramNm) {
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		//��ȡheader�ڵ��µ�������
		String val = this.getTxtOfElement(extOfHeader, paramNm);
		
		//����������ֵ
		return val;
	}
		
	/**
	 * �����չ��Ϣͷ�е�������
	 * @param paramNm ����������
	 * @param val ������ֵ
	 * @throws Throwable
	 */
	public void setValOfExtHeader(String paramNm, String val) {
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		//����ֵ
		this.setTxtOfElement(extOfHeader, paramNm, val);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.buildable.IBuildMOable#getAllValOfHeader()
	 */
	public IRow getAllValOfHeader() {
		
		//��ȡmessage�ڵ��µ�header
		IRow row = this.getEntityOfElement(msg, HEADER_NM);

		//������Ϣͷӳ��
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
		
		//��ȡheader�ڵ��µ�ext
		IRow row = this.getEntityOfElement(header, EXT_NM);

		//������Ϣͷӳ��
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
	 * ���������Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @return
	 */
	public String getParamOfReq(String paramNm){
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		//��ȡparamOfReq�µ�������
		String val = this.getTxtOfElement(this.paramOfReq, paramNm);
		
		//����������ֵ
		return val;
	}

	/**
	 * ����������Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @param val ����ֵ
	 */
	public void setParamOfReq(String paramNm, String val){
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		//�������������������
		this.setTxtOfElement(this.paramOfReq, paramNm, val);	
	}

	
	/* (non-Javadoc)
	 * @see edi.stru.ICreateMO#getAllParamsOfReq()
	 */
	public IRow getAllParamsOfReq() {

		//��ȡrequest�ڵ��µ�parameter
		IRow row = this.getEntityOfElement(request, PARAMETER_NM);
		
		//���ز���ӳ��
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
	
		//��ȡrequest�ڵ��³���parameter���������ʵ��
		Map<String, List<IRow>> entitys = this.getAllEntityOfElement(request, false, PARAMETER_NM);

		//����ʵ��ӳ��
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
	 * �������ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ָ������
	 * @return rows ָ�����Ƶ���������
	 */
	public List<IRow> getRowsOfReq(String paramNm){
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		//���ؽ����
		return this.getEntitysOfElement(request, paramNm);
	}
	
	/**
	 * ��������ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ���ݼ�����
	 * @param rows ��¼��
	 */
	public void setRowsOfReq(String paramNm,  List<IRow> rows){
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		this.addRowsOfElement(request, paramNm, rows);
		
	}
	
	/**
	 * ��������ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ���ݼ�����
	 * @param row ��¼
	 */
	public void setRowOfReq(String paramNm,  IRow row){
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		this.addRowOfElement(request, paramNm, row);
		
	}
	
	/**
	 * �����Ӧ��Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @return ����ֵ
	 */
	public String getParamOfResp(String paramNm){
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		//��ȡparamOfReq�µ�������
		String val = this.getTxtOfElement(this.paramOfResp, paramNm);
		
		//����������ֵ
		return val;
	}
	
	/**
	 * ������Ӧ��Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @param val ����ֵ
	 */
	public void setParamOfResp(String paramNm, String val){
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		this.setTxtOfElement(this.paramOfResp, paramNm, val);	
	}

	/* (non-Javadoc)
	 * @see edi.stru.ICreateMO#getAllParamsOfResp()
	 */
	public IRow getAllParamsOfResp() {
		
		//��ȡrequest�ڵ��µ�parameter
		IRow row = this.getEntityOfElement(response, PARAMETER_NM);
		
		//���ز���ӳ��
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
	 * ����Ӧ���л�ȡ����
	 * @param paramNm ����������
	 */
	public List<IRow> getRowsOfResp(String paramNm){
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		//���ؽ����
		return this.getEntitysOfElement(response, paramNm);
		
	}
	
	/**
	 * ��������ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ���ݼ�����
	 * @param rows ��¼��
	 */
	public void setRowsOfResp(String paramNm,  List<IRow> rows){
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		this.addRowsOfElement(response, paramNm, rows);
	}
	
	/**
	 * ��������ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ���ݼ�����
	 * @param row ��¼
	 */
	public void setRowOfResp(String paramNm,  IRow row){
		
		//У�����
		Assert.paramIsNotNull(paramNm, "paramNm");
				
		this.addRowOfElement(response, paramNm, row);
	}
	
	
	/* (non-Javadoc)
	 * @see edi.stru.ICreateMO#getAllRowsOfResp()
	 */
	public Map<String, List<IRow>> getAllRowsOfResp() {
		
		//��ȡresponse�ڵ��³���parameter���������ʵ��
		Map<String, List<IRow>> entitys = this.getAllEntityOfElement(response, false, PARAMETER_NM);

		//����ʵ��ӳ��
		return entitys;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.edi.stru.client.xml.IBuildClientFile#setAllRowsOfResp(java.util.Map)
	 */
	public void setAllRowsOfResp(Map<String, List<IRow>> rowsMap){
		
		//���rowsMapΪ��ֱ�ӷ���		
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
