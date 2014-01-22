/**
 * @author Gengw
 * Created at 2008-05-06
 */
package com.bln.framework.persist.jdbc.template.bean;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.beanutils.BeanUtils;

import com.bln.framework.persist.jdbc.template.JdbcTemplateBase;
import com.bln.framework.persist.tablestru.component.column.IColumn;

/**
 * <p>JDBCģ����</p>
 * ��ѯ������е�Ԫ��ΪJavaBean
 */
@SuppressWarnings("rawtypes")
public class JdbcTemplate4JavaBean extends JdbcTemplateBase {
	
	/**
	 * bean���������
	 */
	protected String beanClassName = null;
	
	/**
	 * ���캯��
	 */
	public JdbcTemplate4JavaBean() {
		super();
	}
	
	/**
	 * @param beanClassName
	 */
	public JdbcTemplate4JavaBean(String beanClassName) {
		super();
		this.beanClassName = beanClassName;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.template.JdbcTemplateBase#newEntity()
	 */
	protected Object newEntity()throws SQLException, IOException{
		
		Object bean = null;
		try {
			bean = Class.forName(beanClassName).newInstance();
		} catch (Throwable e) {
			IOException ioe = new IOException();
			ioe.initCause(e);
			
			throw ioe;
		}
		
		return bean;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.template.JdbcTemplateBase#setEntityProperty(java.lang.Object, java.sql.ResultSet, com.bln.framework.persist.tablestru.component.column.IColumn)
	 */
	@Override
	protected Object setEntityProperty(Object entity, ResultSet rs, IColumn column) throws SQLException, IOException{
		
		//��ȡ�ֶνṹ
		String colName = column.getColName();

		try {
			
			Object val = this.processValue(rs, column);
			if(val != null){
				BeanUtils.setProperty(entity, colName.toLowerCase(), val);
			}
			
		} catch (IllegalAccessException e) {
			IOException ioe = new IOException(String.format("set property errror, param entity is %s, rs is %s, column is %s", entity.toString(), rs.toString(), column.toString()));
			ioe.initCause(e);
			
			throw ioe;
		} catch (InvocationTargetException e) {
			IOException ioe = new IOException();
			ioe.initCause(e);
			
			throw ioe;
		}
		
		//����ʵ��
		return entity;			
	}

	/**
	 * �ӽ�����л�ȡֵ
	 * @param rs �����
	 * @param column �ֶ�
	 * @return ������ֵ
	 * @throws SQLException 
	 */
	protected Object processValue(ResultSet rs, IColumn column) throws SQLException{
		
		Object value = rs.getObject(column.getColName());
		if(java.sql.Types.BLOB == column.getColType()){
			Blob blob = (Blob)value;
			byte[] bytes = blob.getBytes(1, (int)blob.length());
			value = bytes;
		}
		
		return value;
	}
	
	/**
	 * @return the beanClassName
	 */
	public String getBeanClassName() {
		return beanClassName;
	}

	/**
	 * @param beanClassName the beanClassName to set
	 */
	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
	}
}
