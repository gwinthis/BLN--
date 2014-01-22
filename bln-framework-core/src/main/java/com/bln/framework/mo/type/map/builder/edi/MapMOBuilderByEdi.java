/**
 * @author gengw
 * Created on 2012-03-12
 */
package com.bln.framework.mo.type.map.builder.edi;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.mo.buildable.edi.IBuildMOByEdiable;
import com.bln.framework.mo.builder.MoBuilderBase;

/**
 * <p>ͨ���ⲿ��������IMessageObject����</p>
 * �ⲿ���İ��ֽ��������ʽ������
 */
public class MapMOBuilderByEdi extends MoBuilderBase<IMessageObject, IBuildMOByEdiable> implements IMapMOBuilderByEdi{

	/**
	 * �ѱ��Ľ������Ϣ����
	 * @param bytes ���յ����ֽ�����
	 * @return �ѱ��Ľ�������Ϣ����
	 * @throws Throwable �����������׳����쳣
	 */
	public IMessageObject buildMo(byte[] bytes) throws Throwable {
		
		//1.���ֽ��������ɱ��Ľṹ
		this.buildMOable.readFromData(bytes);
		
		//2.������Ϣ����
		
		//����ServiceId
		mo.setServiceId(buildMOable.getServiceId());

		//����RequestDate
		mo.setRequestDate(buildMOable.getRequestDate());

		//����ReturnCode
		mo.setReturnCode(buildMOable.getReturnCode());

		//����ErrorCode
		mo.setErrorCode(buildMOable.getErrorCode());
		
		//����ErrorDesc
		mo.setErrorDesc(buildMOable.getErrorDesc());
		
		//����ResponseDate
		mo.setResponseDate(buildMOable.getResponseDate());
		
		//����SessionId
		mo.setSessionId(buildMOable.getSessionId());
		
		//������Ϣͷ
		mo.setAllValOfExtHeader(buildMOable.getAllValOfExtHeader());

		//���������ҵ������
		mo.setAllRowsOfReq(buildMOable.getAllRowsOfReq());

		//��������Ĳ�������
		mo.setAllParamOfReq(buildMOable.getAllParamsOfReq());

		//������Ӧ��ҵ������
		mo.setAllRowsOfResp(buildMOable.getAllRowsOfResp());

		//������Ӧ�Ĳ�������
		mo.setAllParamsOfResp(buildMOable.getAllParamsOfResp());
		
		//3.������Ϣ����
		return mo;
	}
}
