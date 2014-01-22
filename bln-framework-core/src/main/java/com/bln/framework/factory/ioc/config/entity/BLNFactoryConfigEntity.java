/**
 * @author gengw
 * Created on Apr 13, 2012
 */
package com.bln.framework.factory.ioc.config.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bln.framework.config.entity.ConfigEntity;

/**
 * BLNFactory�����ļ�ʵ��
 */
public class BLNFactoryConfigEntity extends ConfigEntity<IBLNFactoryConfigEntity> implements IBLNFactoryConfigEntity{
	
	/**
	 * ���캯��
	 * @param oriParam
	 */
	public BLNFactoryConfigEntity(Object oriParam) {
		super(oriParam);
	}

	/**
	 * ���õ�������Ϣ
	 */
	protected Map<String, IBLNFactoryConfigEntity> propertyMap = new HashMap<String, IBLNFactoryConfigEntity>();

	/**
	 * �������б�
	 */
	protected List<IBLNFactoryConfigEntity> subEntitys = new ArrayList<IBLNFactoryConfigEntity>();
	

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity#setProperty(java.lang.String, com.bln.framework.config.entity.IConfigEntity)
	 */
	public void setProperty(String propertyNm, IBLNFactoryConfigEntity propertyConfig){
		propertyMap.put(propertyNm, propertyConfig);
	}
	

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity#getProperty(java.lang.String)
	 */
	public IBLNFactoryConfigEntity getProperty(String propertyNm){
		return propertyMap.get(propertyNm);
	}
	

	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity#getPropertyMap()
	 */
	public Map<String, IBLNFactoryConfigEntity> getPropertyMap() {
		return propertyMap;
	}


	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.entity.IIocFactoryConfigEntity#setPropertyMap(java.util.Map)
	 */
	public void setPropertyMap(Map<String, IBLNFactoryConfigEntity> propertyMap) {
		this.propertyMap = propertyMap;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.ConfigEntity#getSubEntitys()
	 */
	public List<IBLNFactoryConfigEntity> getSubEntitys() {
		return subEntitys;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.factory.type.ioc.config.entity.IBLNFactoryConfigEntity#addSubEntity(com.bln.framework.factory.type.ioc.config.entity.IBLNFactoryConfigEntity)
	 */
	public void addSubEntity(IBLNFactoryConfigEntity config) {
		this.subEntitys.add(config);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder info = new StringBuilder(super.toString());
		
		//�����б�
		info.append("\r\n propertyMap: ").append("\r\n").append(propertyMap);
		
		//����
		return info.toString();
	}
}
