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
 * ������ѯ����
 */
public class ParseCondition implements IParseCondition {
	
	/**
	 * �ֶβ�����������
	 */
	IFieldOprParser fieldOprParser = null;
	
	/**
	 * ת������
	 */
	IConverterFactory convertFactory = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.util.IParseCondition#parse(com.bln.framework.persist.row.IRow, java.lang.String, java.util.Map, java.lang.String)
	 */
	public IConditionClause parseQueryCondition(IRow condition, ITableStru tableStru, Map<String, String> conField2ColMap, String tableAlias) throws ParseException {
		
		//1.У�����
		if(condition == null || condition.isEmpty()){
			return null;
		}
		
		Assert.paramIsNotNull(tableStru, "tableStru");
		
		//��ȡ�ֶ�����ӳ��
		Map<String, Integer> colTypeMap = tableStru.getAllColumns().getColumnTypeMap();
		
		//2.������������
		IConditionClause clause = new ConditionClause();
		
		//��ȡ�����ֶ�
		List<String> fields = condition.getAllFields();
		
		//��ȡ�����ֶ�ֵ
		List<String> values = condition.getAllFieldVals();
		
		boolean first = true;
		for (int i = 0, n = fields.size(); i < n; i++) {
			
			//��ӱ����
			if(tableAlias != null && !"".equals(tableAlias)){
				clause.addCondition(tableAlias + ".");
			}
			
			//����ֵ������
			String field = fields.get(i);
			String value = values.get(i);
			
			FieldOpr fieldOpr = fieldOprParser.parser(field, value);
			
			field = fieldOpr.getField();
			value = fieldOpr.getFieldValue();
			String opr = fieldOpr.getOpr();
			
			//��ȡ��ʵ�ֶ�
			if(conField2ColMap != null){
				String temp = conField2ColMap.get(field);
				if(temp != null){
					field = temp;
				}
			}
			
			//��ȡ�ֶ����ͣ����Ϊ�ձ�ʾ���ֶ��ڱ��в�����
			Integer colType = colTypeMap.get(field);
			if (colType == null) {
				continue;
			}

			//��ӱ��ʽ֮��Ĺ�������
			if(!first){
				clause.addCondition(" AND ");
			}
			
			//�����ֶ�ֵ������ֶ�ֵΪ��ʹ�ÿղ��������������ֵ���ʽ
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
		
		//3.������������
		return clause;
	}


	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.util.IParseCondition#parseAllIdsCondition(com.bln.framework.persist.row.IRow, com.bln.framework.persist.tablestru.ITableStru)
	 */
	public IConditionClause parseAllIdsCondition(IRow condition, ITableStru tableStru) throws ParseException{

		//1.У�����
		Assert.paramIsNotNull(tableStru, "tableStru");
		
		//��ȡ����
		IColumn[] ids = tableStru.getIds();

		//��ȡ�ֶ�����ӳ��
		Map<String, Integer> colTypeMap = tableStru.getAllColumns().getColumnTypeMap();

		//2.��������
		IConditionClause conClause = new ConditionClause();
		boolean first = true;
		for ( int i = 0; i < ids.length; i ++){
			
			//��ӹ���������
			if(!first){
				conClause.addCondition(" AND ");
			}
			
			//��������
			String idName = ids[i].getColName();
			
			//�����ѯ�������������ֶΣ��������������ֶ�
			String value = condition.getValue(idName);
			
			//ƴд����
			if(!StringUtils.isEmpty(value)){
				
				if(first){
					first = false;
				}
				
				//����������
				conClause.addCondition(idName).addCondition(" = ?");
				
				//��������
				IConverter converter = convertFactory.getInstance(colTypeMap.get(idName).intValue());			
				Object param = converter.convert(value);
				conClause.addValue(param);
			}
		}
		
		//3.��������
		return conClause;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.util.IParseCondition#parseChildReferCondition(com.bln.framework.persist.row.IRow, com.bln.framework.persist.tablestru.ITableStru, java.lang.String)
	 */
	public ConditionClause parseChildReferCondition( IRow condition, ITableStru tableStru, String childTableName )throws ParseException{

		//1.У�����
		Assert.paramIsNotNull(tableStru, "tableStru");
		Assert.paramIsNotNull(childTableName, "childTableName");

		//2.��ȡ���
		IForeignKey foreignKey = TableStruUtil.findForeignKey(tableStru, childTableName);
		Assert.notNull(foreignKey, " it not found {0} in tableStru" + tableStru.getTableName() + " foreign keys!", childTableName);
		
		//��ȡ������ϵ
		IPair<String, String>[] refs = foreignKey.getRefColNames();
		
		//��ȡ�ֶ�����ӳ��
		Map<String, Integer> colTypeMap = tableStru.getAllColumns().getColumnTypeMap();

		//3.ƴд����
		ConditionClause conClause = new ConditionClause();
		boolean first = true;
		for ( IPair<String, String> ref : refs){
			if(!first){
				conClause.addCondition(" AND ");
			}
			
			//ƴд����������
			String childRefKey = ref.getRight();
			
			//��ȡ��������ֶ�
			String parentRefKey = ref.getLeft();
			String value = condition.getValue(parentRefKey);
			
			//�����������ֶ�ֵΪ�գ�ƴд�յ�������������ʹ���ʺš�
			if(StringUtils.isEmpty(value)){
				conClause.addCondition(childRefKey).addCondition(" IS NULL");
			}else{
				conClause.addCondition(childRefKey).addCondition(" = ?");
				
				//��������ֵ
				IConverter converter = convertFactory.getInstance(colTypeMap.get(parentRefKey).intValue());
				Object param = converter.convert(value);
				conClause.addValue(param);
			}
		}
		
		//4.���ظ��ӱ�Ĺ�������
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
