/**
 * @author Gengw
 * Created at 2008-05-08
 */
package com.bln.framework.persist.session.executor.material.condition;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.base.BaseObj;
import com.bln.framework.util.asserter.Assert;

/**
 * 条件语句对象，用于定义SQL语句中的条件语句
 */
public class ConditionClause extends BaseObj implements IConditionClause{
	
	/**
	 *	条件的字面量
	 */
	private final StringBuilder condition = new StringBuilder();
	
	/**
	 * 条件中的变量值
	 */
	private final List<Object> values = new ArrayList<Object>();
	
	/**
	 * 构造函数
	 */
	public ConditionClause(){
		super();
	}
		
	/**
	 * 构造函数
	 * @param con
	 */
	public ConditionClause(String con){
		this.addCondition(con);	
	}
	
	/**
	 * 构造函数
	 * @param con
	 * @param val
	 */
	public ConditionClause(String con, Object val){
		this.addConValue(con, val);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.condition.IConditionClause#addConValue(java.lang.String, java.lang.Object)
	 */
	public IConditionClause addConValue(String con, Object obj){
		if(con.indexOf('?') < 0){
			throw new IllegalArgumentException(new StringBuffer("expect '?' in condition \"").append(con).append("\"!!").toString());
		}
		condition.append(con);
		values.add(obj);
		
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.condition.IConditionClause#addCondition(java.lang.String)
	 */
	public IConditionClause addCondition(String str){
		condition.append(str);
		return this;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.condition.IConditionClause#addConditionAtHead(java.lang.String, java.lang.String)
	 */
	public IConditionClause addConditionAtHead(String con, String opr){
		
		condition.insert(0, new StringBuffer(con).append(" ").append(opr).append(" "));
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.condition.IConditionClause#addValue(java.lang.Object)
	 */
	public IConditionClause addValue(Object obj){
		values.add(obj);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.condition.IConditionClause#addValues(java.lang.Object[])
	 */
	public IConditionClause addValues(Object[] objs){
		for ( int i = 0; i < objs.length; i++){
			values.add(objs[i]);
		}
		
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.condition.IConditionClause#addValues(java.util.List)
	 */
	public IConditionClause addValues(List<Object> objs){
		values.addAll(objs);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.condition.IConditionClause#addConClause(com.bln.framework.persist.condition.IConditionClause, java.lang.String)
	 */
	public IConditionClause addConClause(IConditionClause newConClause, String linkOpr){
		
		//如果新的条件对象为空，那么返回当前的条件对象
		if(newConClause == null || StringUtils.isEmpty(newConClause.getCondtion())){
			return this;
		}
		
		//关联操作符不能为空
		Assert.paramIsNotNull(linkOpr, "linkOpr");

		//关联新的条件对象
		String newCon = newConClause.getCondtion();
		
		//如果原来条件为空，那么直接使用新的条件对象的值
		if(StringUtils.isEmpty(condition.toString())){
			condition.append(newCon);
		}else{
			condition.insert(0, "(");
			condition.append(" ").append(linkOpr).append(" (").append(newCon).append(")");
			condition.append(")");
		}
		
		//添加参数
		values.addAll(newConClause.getValues());
		
		//返回关联后的条件对象
		return this;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.condition.IConditionClause#getCondtion()
	 */
	public String getCondtion(){
		
		return condition.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.condition.IConditionClause#getValuesAsList()
	 */
	public List<Object> getValues(){
		
		return values;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.condition.IConditionClause#getValues()
	 */
	public Object[] getValuesAsArray(){
		
		return values.toArray();
	}
	
}
