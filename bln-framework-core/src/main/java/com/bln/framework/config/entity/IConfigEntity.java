package com.bln.framework.config.entity;

import java.util.List;
import java.util.Map;

/**
 * 配置实体接口
 * @param <T>
 */
public interface IConfigEntity<T extends IConfigEntity<T>>{

	/**
	 * 设置属性, 不会同步更新配置文件
	 * @param attrNm 属性名称
	 * @param attrVal 属性值
	 */
	public abstract void setAttr(String attrNm, String attrVal);

	/**
	 * 设置属性
	 * @param attrNm 属性名称
	 * @param attrVal 属性值
	 * @param isSyncConfig 是否同步更新config中的属性值
	 */
	public void setAttr(String attrNm, String attrVal, boolean isSyncConfig);

	/**
	 * 获得属性名称所对应的属性值
	 * @param attrNm 要返回属性值的属性名称
	 * @return 属性值
	 */
	public abstract String getAttr(String attrNm);

	/**
	 * 获取属性信息
	 * @return 该配置的属性信息
	 */
	public abstract Map<String, String> getAttrMap();

	/**
	 * 设置属性信息
	 * @param attrMap
	 */
	public abstract void setAttrMap(Map<String, String> attrMap);

	/**
	 * 获得子配置列表
	 * @return 当前配置下的子配置列表
	 */
	public abstract List<T> getSubConfigEntitys();

	/**
	 * 在子配置列表中添加子配置
	 * @param configEntity 要添加的配置信息
	 */
	public abstract void addSubConfigEntity(T configEntity);

	/**
	 * 获取文本
	 * @return
	 */
	public abstract String getTxtVal();

	/**
	 * 设置文本
	 * @param txtVal
	 */
	public abstract void setTxtVal(String txtVal);

	/**
	 * 获取节点类型
	 * @return
	 */
	public abstract String getType();

	/**
	 * 设置节点类型
	 * @param type
	 */
	public abstract void setType(String type);

}