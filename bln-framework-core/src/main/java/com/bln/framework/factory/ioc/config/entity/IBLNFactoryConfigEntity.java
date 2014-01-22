package com.bln.framework.factory.ioc.config.entity;

import java.util.List;
import java.util.Map;

import com.bln.framework.config.entity.IConfigEntity;

public interface IBLNFactoryConfigEntity extends IConfigEntity<IBLNFactoryConfigEntity>{

	/**
	 * ����ָ�����Ƶ�����
	 * @param propertyNm ����Ӹ����Ƶ����ö���
	 * @param propertyConfig Ҫ��ӵ����ö���
	 */
	public abstract void setProperty(String propertyNm,
			IBLNFactoryConfigEntity propertyConfig);

	/**
	 * ��ȡָ�����Ƶ�����
	 * @param propertyNm �����ظ����Ƶ����ö���
	 * @return ��������
	 */
	public abstract IBLNFactoryConfigEntity getProperty(String propertyNm);

	/**
	 * ��ȡ��������
	 * @return
	 */
	public abstract Map<String, IBLNFactoryConfigEntity> getPropertyMap();

	/**
	 * ������������
	 * @param propertyMap
	 */
	public abstract void setPropertyMap(Map<String, IBLNFactoryConfigEntity> propertyMap);
	
	/**
	 * �����ʵ�����ã������߶���ʵ��
	 * @return ��ǰ�����µ��������б�
	 */
	public abstract List<IBLNFactoryConfigEntity> getSubEntitys();

	/**
	 * �����ʵ�����ã������߶���ʵ��
	 * @param config Ҫ��ӵ�������Ϣ
	 */
	public abstract void addSubEntity(IBLNFactoryConfigEntity config);
	
	

}