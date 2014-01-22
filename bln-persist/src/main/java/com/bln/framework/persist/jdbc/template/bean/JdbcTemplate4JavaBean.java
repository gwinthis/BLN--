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
 * <p>JDBC模板类</p>
 * 查询结果集中的元素为JavaBean
 */
@SuppressWarnings("rawtypes")
public class JdbcTemplate4JavaBean extends JdbcTemplateBase {
	
	/**
	 * bean对象的名称
	 */
	protected String beanClassName = null;
	
	/**
	 * 构造函数
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
		
		//获取字段结构
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
		
		//返回实体
		return entity;			
	}

	/**
	 * 从结果集中获取值
	 * @param rs 结果集
	 * @param column 字段
	 * @return 处理后的值
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
