/**
 * @author Gengw
 * Created at 2008-05-06
 */
package com.bln.framework.persist.jdbc.template;

import java.sql.ResultSet;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.row.Row;
import com.bln.framework.persist.tablestru.component.column.IColumn;

/**
 * <p>JDBC模板类</p>
 * 查询结果集中的元素为IRow
 */
public class JdbcTemplate4IRow extends JdbcTemplateBase<IRow> implements IJdbcTemplate<IRow> {
		
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.template.JdbcTemplateBase#newEntity()
	 */
	protected IRow newEntity(){
		return new Row();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.jdbc.template.JdbcTemplateBase#setEntityProperty(java.lang.Object, java.sql.ResultSet, com.bln.framework.persist.tablestru.component.column.IColumn)
	 */
	protected IRow setEntityProperty(IRow entity, ResultSet rs, IColumn column){
		
		//获取字段结构
		String colName = column.getColName();
		int colType = column.getColType();
		
		//设置实体属性
		String val = this.columnValueToString(rs, colName, colType);
		entity.setValue(colName, val);
		
		//返回实体
		return entity;
	}
}
