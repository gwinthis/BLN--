/**
 * @author gengw
 * Created at 2012-03-16
 */
package com.bln.framework.config.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import com.bln.framework.base.BaseObj;

/**
 * <p>����ʵ��</p>
 * ��������ú����������������б�
 */

public class ConfigEntity<T extends IConfigEntity<T>> extends BaseObj implements IConfigEntity<T>{
	
	/**
	 * ���õ�����
	 */
	protected String type = null;
	
	/**
	 * ���õ��ı�
	 */
	protected String txtVal = null;
	
	/**
	 * ���õ�������Ϣ
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, String> attrMap = new ListOrderedMap();

	/**
	 * �������б�
	 */
	protected List<T> subConfigs = new ArrayList<T>();

	/**
	 * ԭԪ�ض���
	 */
	protected Object oriElement = null;
	
	/**
	 * ���캯��
	 * @param oriParam
	 */
	public ConfigEntity(Object oriParam){
		this.oriElement = oriParam;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#addSubConfig(com.bln.framework.config.entity.IConfigEntity)
	 */
	public void addSubConfigEntity(T configEntity) {
		this.subConfigs.add(configEntity);
		
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#getSubConfigs()
	 */
	public List<T> getSubConfigEntitys() {
		return subConfigs;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#setAttr(java.lang.String, java.lang.String)
	 */
	public void setAttr(String attrNm, String attrVal){
		this.setAttr(attrNm, attrVal, false);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#setAttr(java.lang.String, java.lang.String, boolean)
	 */
	public void setAttr(String attrNm, String attrVal, boolean isSyncConfig){
		attrMap.put(attrNm, attrVal);
		if(isSyncConfig){
			if(oriElement instanceof org.dom4j.Element){
				org.dom4j.Element ele = (org.dom4j.Element)oriElement;
				ele.addAttribute(attrNm, attrVal);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#getAttr(java.lang.String)
	 */
	public String getAttr(String attrNm){
		return attrMap.get(attrNm);
	}
			
	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#getAttrMap()
	 */
	public Map<String, String> getAttrMap() {
		return attrMap;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#setAttrMap(java.util.Map)
	 */
	public void setAttrMap(Map<String, String> attrMap) {
		this.attrMap = attrMap;
	}
		
	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#getTxtVal()
	 */
	public String getTxtVal() {
		return txtVal;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#setTxtVal(java.lang.String)
	 */
	public void setTxtVal(String txtVal) {
		this.txtVal = txtVal;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#getType()
	 */
	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#setType(java.lang.String)
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuilder info = new StringBuilder("\r\n");
		
		//����
		info.append(" type = ").append(type);

		//�ı�ֵ
		info.append("\r\n txtVal = ").append(txtVal);

		//�����б�
		info.append("\r\n attrMap: ").append("\r\n").append(attrMap);
		
		//�������б�
		info.append("\r\n subConfigs").append("\r\n").append(subConfigs == null ? null : Arrays.toString(subConfigs.toArray(new ConfigEntity[]{})));

		return info.toString();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.config.entity.IConfigEntity#getOriElement()
	 */
	public Object getOriElement() {
		return oriElement;
	}
}
