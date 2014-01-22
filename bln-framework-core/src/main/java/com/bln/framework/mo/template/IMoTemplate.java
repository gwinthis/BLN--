package com.bln.framework.mo.template;

import com.bln.framework.mo.IMessageObject;

public interface IMoTemplate {

	/**
	 * �������
	 * @return TODO
	 */
	public abstract IMessageObject requestEnd(IMessageObject mo);

	/**
	 * ����ɹ����
	 * @param mo Ҫ�������Ϣ����
	 * @return TODO
	 */
	public abstract IMessageObject serviceSuccessfulEnd(IMessageObject mo);

	/**
	 * ����ʧ�ܽ���
	 * @param mo Ҫ�������Ϣ����
	 * @param respCode ������
	 * @param respDesc ��������
	 * @return ���������Ϣ����
	 */
	public IMessageObject serviceFailedEnd(IMessageObject mo, String errorCode, String respDesc);

	/**
	 * ����ʧ�ܽ��� 
	 * @param mo Ҫ�������Ϣ����
	 * @param errorCode ������
	 * @return ���������Ϣ����
	 */
	public IMessageObject serviceFailedEnd(IMessageObject mo, String errorCode);

}