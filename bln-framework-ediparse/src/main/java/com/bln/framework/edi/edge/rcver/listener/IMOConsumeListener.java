/**
 * �����˶Ȼ����Ƽ����޹�˾��Ȩ����
 * Copyright (C) Badu Corporation. All Rights Reserved
 */
package com.bln.framework.edi.edge.rcver.listener;

import com.bln.framework.mo.IMessageObject;

/**
 * ���Ľ����߼�����
 * @author gengw(gengw@17guagua.com)
 * @version 2013-7-18 ����6:19:45
 */
public interface IMOConsumeListener {
	
	/**
	 * �������֮ǰ�������¼�
	 * @param reqMo ������Ϣ����
	 */
	public void beforExecuteService(IMessageObject reqMo);
	
	/**
	 * �������֮�󴥷����¼�
	 * @param reqMo ������Ϣ����
	 * @param respMo ��Ӧ��Ϣ����
	 */
	public void afterExecuteService(IMessageObject reqMo, IMessageObject respMo);
	

}
