/**
 * @author gengw
 * Created on 2012-03-13
 */
package com.bln.framework.mo.buildable;

import java.util.List;
import java.util.Map;

import com.bln.framework.persist.row.IRow;

/**
 * ������MO�Ľӿ�
 */
public interface IBuildMOable {
	
	/**
	 * �������ʵ��
	 * @return
	 */
	public String getServiceId();

	/**
	 * ��ȡ����ʱ��
	 * @return ����ʱ��
	 * @
	 */
	public String getRequestDate();

	/**
	 * ��ȡ������
	 * @return ������
	 * @
	 */
	public String getReturnCode();

	/**
	 * ��ȡ������
	 * @return ������
	 */
	public String getErrorCode();

	/**
	 * ��ȡ��������
	 * @return ��������
	 * @
	 */
	public String getErrorDesc();

	/**
	 * ��ȡ��Ӧʱ��
	 * @return ��������
	 * @
	 */
	public String getResponseDate();

	/**
	 * ��ȡSessionId
	 * @return SessionId
	 * @
	 */
	public String getSessionId();

	
	/**
	 * ���Header�е�����ֵ
	 * @return
	 */
	public abstract IRow getAllValOfHeader();

	/**
	 * �����չ��Ϣͷʵ��
	 * @return
	 */
	public IRow getAllValOfExtHeader();

	/**
	 * �������ʵ��
	 * @return
	 */
	public Map<String, List<IRow>> getAllRowsOfReq();

	/**
	 * ����������ʵ��
	 * @return
	 */
	public IRow getAllParamsOfReq();

	/**
	 * �����Ӧʵ��
	 * @return
	 */
	public Map<String, List<IRow>> getAllRowsOfResp();

	/**
	 * �����Ӧ����ʵ��
	 * @return
	 */
	public IRow getAllParamsOfResp();
	
	/**
	 * ����Ӧ���л�ȡ����
	 * @param paramNm ����������
	 */
	public List<IRow> getRowsOfResp(String paramNm);
	

	/**
	 * �����Ϣͷ�е�������
	 * @param paramNm ����������
	 * @return ������ֵ
	 * @
	 */
	public String getValOfExtHeader(String paramNm);

	/**
	 * ���������Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @return
	 */
	public String getParamOfReq(String paramNm);

	/**
	 * �������ڵ��ָ���ڵ����Ƶ�����
	 * @param paramNm ָ������
	 * @return rows ָ�����Ƶ���������
	 */
	public List<IRow> getRowsOfReq(String paramNm);

	/**
	 * �����Ӧ��Ϣ�Ĳ���ֵ
	 * @param paramNm ��������
	 * @return ����ֵ
	 */
	public String getParamOfResp(String paramNm);
	
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
}
