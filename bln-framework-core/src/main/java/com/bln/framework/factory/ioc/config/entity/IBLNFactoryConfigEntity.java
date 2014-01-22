package com.bln.framework.factory.ioc.config.entity;

import java.util.List;
import java.util.Map;

import com.bln.framework.config.entity.IConfigEntity;

public interface IBLNFactoryConfigEntity extends IConfigEntity<IBLNFactoryConfigEntity>{

	/**
	 * 设置指定名称的特性
	 * @param propertyNm 将添加该名称的配置对象
	 * @param propertyConfig 要添加的配置对象
	 */
	public abstract void setProperty(String propertyNm,
			IBLNFactoryConfigEntity propertyConfig);

	/**
	 * 获取指定名称的特性
	 * @param propertyNm 将返回该名称的配置对象
	 * @return 特性配置
	 */
	public abstract IBLNFactoryConfigEntity getProperty(String propertyNm);

	/**
	 * 获取所有特性
	 * @return
	 */
	public abstract Map<String, IBLNFactoryConfigEntity> getPropertyMap();

	/**
	 * 设置所有特性
	 * @param propertyMap
	 */
	public abstract void setPropertyMap(Map<String, IBLNFactoryConfigEntity> propertyMap);
	
	/**
	 * 获得子实体配置，类别或者对象实例
	 * @return 当前配置下的子配置列表
	 */
	public abstract List<IBLNFactoryConfigEntity> getSubEntitys();

	/**
	 * 添加子实体配置，类别或者对象实例
	 * @param config 要添加的配置信息
	 */
	public abstract void addSubEntity(IBLNFactoryConfigEntity config);
	
	

}