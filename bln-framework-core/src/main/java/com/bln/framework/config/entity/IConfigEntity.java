package com.bln.framework.config.entity;

import java.util.List;
import java.util.Map;

/**
 * ����ʵ��ӿ�
 * @param <T>
 */
public interface IConfigEntity<T extends IConfigEntity<T>>{

	/**
	 * ��������, ����ͬ�����������ļ�
	 * @param attrNm ��������
	 * @param attrVal ����ֵ
	 */
	public abstract void setAttr(String attrNm, String attrVal);

	/**
	 * ��������
	 * @param attrNm ��������
	 * @param attrVal ����ֵ
	 * @param isSyncConfig �Ƿ�ͬ������config�е�����ֵ
	 */
	public void setAttr(String attrNm, String attrVal, boolean isSyncConfig);

	/**
	 * ���������������Ӧ������ֵ
	 * @param attrNm Ҫ��������ֵ����������
	 * @return ����ֵ
	 */
	public abstract String getAttr(String attrNm);

	/**
	 * ��ȡ������Ϣ
	 * @return �����õ�������Ϣ
	 */
	public abstract Map<String, String> getAttrMap();

	/**
	 * ����������Ϣ
	 * @param attrMap
	 */
	public abstract void setAttrMap(Map<String, String> attrMap);

	/**
	 * ����������б�
	 * @return ��ǰ�����µ��������б�
	 */
	public abstract List<T> getSubConfigEntitys();

	/**
	 * ���������б������������
	 * @param configEntity Ҫ��ӵ�������Ϣ
	 */
	public abstract void addSubConfigEntity(T configEntity);

	/**
	 * ��ȡ�ı�
	 * @return
	 */
	public abstract String getTxtVal();

	/**
	 * �����ı�
	 * @param txtVal
	 */
	public abstract void setTxtVal(String txtVal);

	/**
	 * ��ȡ�ڵ�����
	 * @return
	 */
	public abstract String getType();

	/**
	 * ���ýڵ�����
	 * @param type
	 */
	public abstract void setType(String type);

}