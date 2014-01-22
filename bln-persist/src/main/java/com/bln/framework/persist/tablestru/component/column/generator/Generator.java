/**
 * @author gengw
 * Created on Apr 11, 2012
 */
package com.bln.framework.persist.tablestru.component.column.generator;

import java.util.Arrays;

import com.bln.framework.persist.tablestru.param.IParam;

/**
 * ID������
 */
public class Generator implements IGenerator {
		
	/**
	 * ����������
	 */
	String type = null;
	
	/**
	 * ��ʹ�õĲ���
	 */
	IParam[] params = null;

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.column.id.generator.IIDGenerator#getType()
	 */
	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.column.id.generator.IIDGenerator#setType(java.lang.String)
	 */
	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.column.id.generator.IIDGenerator#getParams()
	 */
	public IParam[] getParams() {
		return params;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.tablemodule.column.id.generator.IIDGenerator#setParams(com.bln.framework.persist.tablemodule.ParamBean[])
	 */
	public void setParams(IParam[] params) {
		this.params = params;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder info = new StringBuilder("Generator ");
		
		//tableName
		info.append(type).append(" info : \r\n");

		//params
		info.append("params: ").append(Arrays.toString(params)).append("\r\n");
			
		//���ؽ��
		return info.toString();		
	}

}
