package com.bln.framework.biz.module.entity.lib;

import com.bln.framework.biz.module.entity.IEntityStatelessModule;

public interface IEntityLib{
	
	/**
	 * ���ݶ������ƻ�ö���
	 * @param objNm ��������
	 * @return ����ʵ�ʵĶ���
	 */
	public IEntityStatelessModule getInstance(String objNm);

	/**
	 * ���ݶ������ƻ�ö���
	 * @param objNm ��������
	 * @return ����ʵ�ʵĶ���
	 */
	public IEntityStatelessModule getNotNullInstance(String objNm);

	
}