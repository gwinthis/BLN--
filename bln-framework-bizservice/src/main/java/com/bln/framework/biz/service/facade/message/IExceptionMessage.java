package com.bln.framework.biz.service.facade.message;

public interface IExceptionMessage {

	/**
	 * �����쳣��ȡ��Ϣ��
	 * @param clazzName ����
	 * @return ��Ϣ��
	 */
	public abstract String getErrorCode(String clazzName);

	/**
	 * ���ݴ�����Ϣ��ȡ��Ӧ��Ϣ
	 * @param e ������Ϣ
	 * @return ��Ӧ��Ϣ
	 */
	public abstract String getErrorDesc(Throwable e);

}