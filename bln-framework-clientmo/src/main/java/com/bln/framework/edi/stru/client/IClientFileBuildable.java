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
 * ClientFile�������Ľӿ�
 */
public interface IClientFileBuildable extends IFileBuildable {

	/**
	 * ��header�ڵ�������SERVICEID��ֵ
	 * @param serviceId��ֵ
	 * @throws Throwable
	 */
	public abstract void setServiceId(String serviceId);

	/**
	 * ��������ʱ��
	 * @param requestDate ����ʱ��
	 */
	public void setRequestDate(String requestDate);

	/**
	 * ���÷�����
	 * @param returnCode ������
	 */
	public void setReturnCode(String returnCode);

	/**
	 * ���ô�����
	 * @param errorCode ������
	 */
	public void setErrorCode(String errorCode);

	/**
	 * ���ô�������
	 * @param errorDesc ��������
	 */
	public void setErrorDesc(String errorDesc);

	/**
	 * ������Ӧʱ��
	 * @param responseDate ��Ӧʱ��
	 */
	public void setResponseDate(String responseDate);

	/**
	 * ����SessionId
	 * @param SessionId SessionId
	 */
	public void setSessionId(String sessionId);

	/**
	 * ����Header�е�����ֵ
	 * @param headDatas
	 */
	public abstract void setAllValOfHeader(IRow headDatas);

	/**
	 * ������չͷ�ڵ��ֵ
	 * @param row ͷ�ڵ������
	 */
	public abstract void setAllValOfExtHeader(IRow row);

	/**
	 * ��request�ڵ�����Ӽ�¼��
	 * @param rowsMap ��¼�������ƺ����ݼ���
	 */
	public abstract void setAllRowsOfReq(Map<String, List<IRow>> rowsMap);

	/**
	 * �����������������ֵ
	 * @param row ��������ֵ
	 * @throws Throwable
	 */
	public abstract void setAllParamOfReq(IRow row);

	/**
	 * ��response�ڵ�����Ӽ�¼��
	 * @param rowsMap ��¼�������ƺ����ݼ���
	 */
	public abstract void setAllRowsOfResp(Map<String, List<IRow>> rowsMap);

	/**
	 * ������Ӧ����������ֵ
	 * @param row ��������ֵ
	 * @throws Throwable
	 */
	public abstract void setAllParamsOfResp(IRow row);

	
	/**
	 * �����Ϣͷ�е�������
	 * @param paramNm ����������
	 * @param val ������ֵ
	 * @throws Throwable
	 */
	public void setValOfExtHeader(String paramNm, String val);

	/**
	 * ����������Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @param val ����ֵ
	 */
	public void setParamOfReq(String paramNm, String val);

	/**
	 * ��������ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ���ݼ�����
	 * @param rows ��¼��
	 */
	public void setRowsOfReq(String paramNm, List<IRow> rows);

	/**
	 * ������Ӧ��Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @param val ����ֵ
	 */
	public void setParamOfResp(String paramNm, String val);

	/**
	 * ��������ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ���ݼ�����
	 * @param rows ��¼��
	 */
	public void setRowsOfResp(String paramNm, List<IRow> rows);

	/**
	 * ��������ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ���ݼ�����
	 * @param row ��¼
	 */
	public void setRowOfReq(String paramNm, IRow row);

	/**
	 * ��������ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ���ݼ�����
	 * @param row ��¼
	 */
	public void setRowOfResp(String paramNm, IRow row);
	
	/**
	 * ���request��Ϣ
	 */
	public void clearRequest();

	/**
	 * ���response��Ϣ
	 */
	public void clearResponse();
}