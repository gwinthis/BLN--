package com.bln.framework.mo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bln.framework.persist.row.IRow;

public interface IMessageObject {

	/**
	 * �����Ϊ����
	 * @return
	 * @throws Throwable 
	 */
	public abstract String getServiceId();

	/**
	 * ������Ϊ����
	 * @param serviceId
	 */
	public abstract void setServiceId(String serviceId);
	
	/**
	 * �����Ϣͷ�е�������
	 * @param paramNm ����������
	 * @return ������ֵ
	 * @throws Throwable 
	 */
	public abstract String getValueOfExtHeader(String paramNm);

	/**
	 * ������Ϣͷ�е�������
	 * @param paramNm ����������
	 * @param val ������ֵ
	 * @throws Throwable 
	 */
	public abstract void setValOfExtHeader(String paramNm, String val);

	/**
	 * ���Header�е�����ֵ
	 * @return
	 */
	public abstract IRow getAllValOfHeader();

	/**
	 * ����Header�е�����ֵ
	 * @param headDatas
	 */
	public abstract void setAllValOfHeader(IRow headDatas);
	
	/**
	 * ���������Ϣͷ�е�����
	 * @return
	 * @throws Throwable 
	 */
	public abstract IRow getAllValOfExtHeader();

	/**
	 * ����������Ϣͷ�е�����
	 * @param headDatas
	 */
	public abstract void setAllValOfExtHeader(IRow headDatas);
	
	/**
	 * ���������Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @return
	 */
	public abstract String getParamOfReq(String paramNm);

	/**
	 * ����������Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @param val ����ֵ
	 */
	public abstract void setParamOfReq(String paramNm, String val);

	/**
	 * ��������������
	 * @return
	 * @throws Throwable 
	 */
	public abstract IRow getAllParamsOfReq();

	/**
	 * ������������ҵ������
	 * @param reqParams
	 */
	public abstract void setAllParamOfReq(IRow reqParams);
	
	/**
	 * �����������ҵ������
	 * @return
	 * @throws Throwable 
	 */
	public abstract Map<String, List<IRow>> getAllRowsOfReq();

	/**
	 * ������������ҵ������
	 * @param reqRowsMap
	 */
	public abstract void setAllRowsOfReq(Map<String, List<IRow>> reqRowsMap);

	/**
	 * �������ڵ��ָ���ڵ����Ƶ�����
	 * @param objNm ָ������
	 * @return rows ָ�����Ƶ���������
	 */
	public List<IRow> getRowsOfReq(String objNm);
	
	/**
	 * ��������ڵ��ָ���ڵ����Ƶ�����
	 * @param objNm
	 * @param rows
	 */
	public void setRowsOfReq(String objNm,  List<IRow> rows);

	/**
	 * �����Ӧ��Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @return
	 */
	public abstract String getParamOfResp(String paramNm);

	/**
	 * ������Ӧ��Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @param val ����ֵ
	 */
	public abstract void setParamOfResp(String paramNm, String val);

	/**
	 * ��������������
	 * @return
	 * @throws Throwable 
	 */
	public abstract IRow getAllParamsOfResp();

	/**
	 * ����������Ӧ����
	 * @param respParams
	 */
	public abstract void setAllParamsOfResp(IRow respParams);
	
	
	/**
	 * ����Ӧ���л�ȡ����
	 * @param paramNm ����������
	 */
	public abstract List<IRow> getRowsOfResp(String paramNm);

	/**
	 * ������Ӧ�ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ָ���ڵ�����
	 * @param rows ���ݼ�
	 */
	public abstract void setRowsOfResp (String paramNm, List<IRow> rows);
	
	/**
	 * ���������Ӧҵ������
	 * @return
	 * @throws Throwable 
	 */
	public abstract Map<String, List<IRow>> getAllRowsOfResp();
	
	/**
	 * ����������Ӧҵ������
	 * @param respRowsMap
	 */
	public abstract void setAllRowsOfResp(Map<String, List<IRow>> respRowsMap);

	/**
	 * ������ֽ�������
	 * @return �ֽ�����
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
	 * ���Request�ڵ��е�����
	 */
	public void clearRequest();

	/**
	 * ���Response�ڵ��е�����
	 */
	public void clearResponse();


	/**
	 * ��request�����õ��м�¼
	 * @param paramNm ʵ������
	 * @param row ��¼
	 */
	public void setRowOfReq(String paramNm, IRow row);

	/**
	 * ��response�����õ��м�¼
	 * @param paramNm ʵ������
	 * @param rows ��¼
	 */
	public void setRowOfResp(String paramNm, IRow row);


	/**
	 * ȡ������Ӧ�еĽڵ��һ����¼
	 * @param paramNm
	 * @return
	 */
	public IRow getRowOfResp(String paramNm);

	/**
	 * ��ȡ������Ϣ�еĽڵ��һ����¼
	 * @param paramNm �ڵ�����
	 * @return ��¼
	 */
	public IRow getRowOfReq(String paramNm);


	/**
	 * ��������Ϣ�л�ȡָ���������
	 * @param tableNames ָ������
	 * @return ָ���������
	 * @throws Throwable 
	 */
	public Map<String, List<IRow>> getRowsOfReq(String[] tableNames);

	/**
	 * ����Ӧ��Ϣ�л�ȡָ���������
	 * @param tableNames ָ������
	 * @return ָ���������
	 * @throws Throwable 
	 */
	public Map<String, List<IRow>> getRowsOfResp(String[] tableNames);

	/**
	 * ���ؿյ�mo����
	 * @return
	 */
	public IMessageObject emptyMo();
	
}