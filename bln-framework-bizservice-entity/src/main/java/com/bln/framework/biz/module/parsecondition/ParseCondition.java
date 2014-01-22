/**
 * @author gengw
 * Created on May 14, 2012
 */
package com.bln.framework.biz.module.parsecondition;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.biz.module.parsecondition.fieldopr.FieldOpr;
import com.bln.framework.biz.module.parsecondition.fieldopr.parser.IFieldOprParser;

import com.bln.framework.persist.jdbc.converter.IConverter;
import com.bln.framework.persist.jdbc.converter.factory.IConverterFactory;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.ConditionClause;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.tablestru.component.column.IColumn;
import com.bln.framework.persist.tablestru.component.fk.IForeignKey;
import com.bln.framework.persist.tablestru.util.TableStruUtil;
import com.bln.framework.util.asserter.Assert;
import com.bln.framework.util.pair.IPair;

/**
 * 解析查询条件
 */
public class ParseCondition implements IParseCondition {
	
	/**
	 * 字段操作符解析器
	 */
	IFieldOprParser fieldOprParser = null;
	
	/**
	 * 转换工厂
	 */
	IConverterFactory convertFactory = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.util.IParseCondition#parse(com.bln.framework.persist.row.IRow, java.lang.String, java.util.Map, java.lang.String)
	 */
	public IConditionClause parseQueryCondition(IRow condition, ITableStru tableStru, Map<String, String> conField2ColMap, String tableAlias) throws ParseException {
		
		//1.校验参数
		if(condition == null || condition.isEmpty()){
			return null;
		}
		
		Assert.paramIsNotNull(tableStru, "tableStru");
		
		//获取字段类型映射
		Map<String, Integer> colTypeMap = tableStru.getAllColumns().getColumnTypeMap();
		
		//2.生成条件对象
		IConditionClause clause = new ConditionClause();
		
		//获取所有字段
		List<String> fields = condition.getAllFields();
		
		//获取所有字段值
		List<String> values = condition.getAllFieldVals();
		
		boolean first = true;
		for (int i = 0, n = fields.size(); i < n; i++) {
			
			//添加表别名
			if(tableAlias != null && !"".equals(tableAlias)){
				clause.addCondition(tableAlias + ".");
			}
			
			//处理赋值操作符
			String field = fields.get(i);
			String value = values.get(i);
			
			FieldOpr fieldOpr = fieldOprParser.parser(field, value);
			
			field = fieldOpr.getField();
			value = fieldOpr.getFieldValue();
			String opr = fieldOpr.getOpr();
			
			//获取真实字段
			if(conField2ColMap != null){
				String temp = conField2ColMap.get(field);
				if(temp != null){
					field = temp;
				}
			}
			
			//获取字段类型，如果为空表示该字段在表中不存在
			Integer colType = colTypeMap.get(field);
			if (colType == null) {
				continue;
			}

			//添加表达式之间的关联符号
			if(!first){
				clause.addCondition(" AND ");
			}
			
			//处理字段值，如果字段值为空使用空操作符，否则添加值表达式
			if (value == null || "".equals(value)) {
				clause.addCondition(field + " IS NULL");
			}else{
				
				IConverter converter = convertFactory.getInstance(colType.intValue());
				
				if(!fieldOpr.isMultiValue()){
					Object object = converter.convert(value);
					clause.addConValue(field + opr + "?" ,object);					
				}else{
					
					String[] filedValues = fieldOpr.getFieldValues();
					
					clause.addCondition(field + opr + "(");
					for(int j = 0, m = filedValues.length; j < m; j++){
						
						if(j != 0){
							clause.addCondition(",");
						}
						
						Object object = converter.convert(filedValues[j]);
						clause.addConValue("?" ,object);
					}
					clause.addCondition(")");
				}
				
			}
			
			if(first){
				first = false;
			}
		}
		
		//3.返回条件对象
		return clause;
	}


	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.util.IParseCondition#parseAllIdsCondition(com.bln.framework.persist.row.IRow, com.bln.framework.persist.tablestru.ITableStru)
	 */
	public IConditionClause parseAllIdsCondition(IRow condition, ITableStru tableStru) throws ParseException{

		//1.校验参数
		Assert.paramIsNotNull(tableStru, "tableStru");
		
		//获取主键
		IColumn[] ids = tableStru.getIds();

		//获取字段类型映射
		Map<String, Integer> colTypeMap = tableStru.getAllColumns().getColumnTypeMap();

		//2.生成条件
		IConditionClause conClause = new ConditionClause();
		boolean first = true;
		for ( int i = 0; i < ids.length; i ++){
			
			//添加关联操作符
			if(!first){
				conClause.addCondition(" AND ");
			}
			
			//主键名称
			String idName = ids[i].getColName();
			
			//如果查询条件不包含该字段，条件不包含该字段
			String value = condition.getValue(idName);
			
			//拼写条件
			if(!StringUtils.isEmpty(value)){
				
				if(first){
					first = false;
				}
				
				//条件字面量
				conClause.addCondition(idName).addCondition(" = ?");
				
				//条件参数
				IConverter converter = convertFactory.getInstance(colTypeMap.get(idName).intValue());			
				Object param = converter.convert(value);
				conClause.addValue(param);
			}
		}
		
		//3.返回条件
		return conClause;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.util.IParseCondition#parseChildReferCondition(com.bln.framework.persist.row.IRow, com.bln.framework.persist.tablestru.ITableStru, java.lang.String)
	 */
	public ConditionClause parseChildReferCondition( IRow condition, ITableStru tableStru, String childTableName )throws ParseException{

		//1.校验参数
		Assert.paramIsNotNull(tableStru, "tableStru");
		Assert.paramIsNotNull(childTableName, "childTableName");

		//2.获取外键
		IForeignKey foreignKey = TableStruUtil.findForeignKey(tableStru, childTableName);
		Assert.notNull(foreignKey, " it not found {0} in tableStru" + tableStru.getTableName() + " foreign keys!", childTableName);
		
		//获取关联关系
		IPair<String, String>[] refs = foreignKey.getRefColNames();
		
		//获取字段类型映射
		Map<String, Integer> colTypeMap = tableStru.getAllColumns().getColumnTypeMap();

		//3.拼写条件
		ConditionClause conClause = new ConditionClause();
		boolean first = true;
		for ( IPair<String, String> ref : refs){
			if(!first){
				conClause.addCondition(" AND ");
			}
			
			//拼写条件字面量
			String childRefKey = ref.getRight();
			
			//获取主表关联字段
			String parentRefKey = ref.getLeft();
			String value = condition.getValue(parentRefKey);
			
			//如果主表关联字段值为空，拼写空的字面量，否则使用问号。
			if(StringUtils.isEmpty(value)){
				conClause.addCondition(childRefKey).addCondition(" IS NULL");
			}else{
				conClause.addCondition(childRefKey).addCondition(" = ?");
				
				//条件参数值
				IConverter converter = convertFactory.getInstance(colTypeMap.get(parentRefKey).intValue());
				Object param = converter.convert(value);
				conClause.addValue(param);
			}
		}
		
		//4.返回该子表的关联条件
		return conClause;
	}

	/**
	 * @param convertFactory the convertFactory to set
	 */
	public void setConvertFactory(IConverterFactory convertFactory) {
		this.convertFactory = convertFactory;
	}


	/**
	 * @param fieldOprParser the fieldOprParser to set
	 */
	public void setFieldOprParser(IFieldOprParser fieldOprParser) {
		this.fieldOprParser = fieldOprParser;
	}
}
