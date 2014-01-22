/**
 * @author gengw
 * Created on Nov 23, 2012
 */
package com.bln.framework.biz.module.parsecondition.fieldopr.parser;

import com.bln.framework.biz.module.parsecondition.fieldopr.FieldOpr;

/**
 * 字段操作解析对象
 */
public class FieldOprParser implements IFieldOprParser {
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.module.parsecondition.fieldopr.parser.IFieldOprParser#parser(java.lang.String)
	 */
	public FieldOpr parser(String field, String value ){
				
		String opr = null;
		boolean isMultiValue = false;
		String[] values = null;
		
		if (!field.endsWith("_")){
			opr = " = ";
			
		}else{

			if(field.endsWith("_GE_")){
				field = field.substring(0, field.length() - 4);
				opr = " >= ";
				
			}else if(field.endsWith("_LE_")){
				field = field.substring(0, field.length() - 4);
				opr = " <= ";
				
			}else if(field.endsWith("_LLIKE_")){
				field = field.substring(0, field.length() - 7);
				opr = " LIKE ";
				value = "%" + value;
				
			}else if(field.endsWith("_RLIKE_")){
				field = field.substring(0, field.length() - 7);
				opr = " LIKE ";
				value = value + "%";
				
			}else if(field.endsWith("_LIKE_")){
				field = field.substring(0, field.length() - 6);
				opr = " LIKE ";
				value = "%" + value + "%";
				
			}else if(field.endsWith("_IN_")){
				field = field.substring(0, field.length() - 4);
				opr = " IN ";
				isMultiValue = true;
				
				if(value != null){
					values = value.split(",");
				}
			}else if(field.endsWith("_GREATER_")){
				field = field.substring(0, field.length() - 9);
				opr = " > ";
				
			}else if(field.endsWith("_LESS_")){
				field = field.substring(0, field.length() - 6);
				opr = " < ";
				
			}else if(field.endsWith("_NOTEQUAL_")){
				field = field.substring(0, field.length() - 10);
				opr = " <> ";

			}else if(field.endsWith("_NOTIN_")){
				field = field.substring(0, field.length() - 7);
				opr = " NOT IN ";
				isMultiValue = true;
				
				if(value != null){
					values = value.split(",");
				}
				
			}else if(field.endsWith("_EQUAL_")){
				field = field.substring(0, field.length() - 7);
				opr = " = ";
				
			}else if(field.endsWith("_NOTLLIKE_")){
				field = field.substring(0, field.length() - 10);
				opr = " NOT LIKE ";
				value = "%" + value;
				
			}else if(field.endsWith("_NOTRLIKE_")){
				field = field.substring(0, field.length() - 10);
				opr = " NOT LIKE ";
				value = value + "%";
				
			}else if(field.endsWith("_NOTLIKE_")){
				field = field.substring(0, field.length() - 9);
				opr = " NOT LIKE ";
				value = "%" + value + "%";		
			}
		}
		
		FieldOpr fieldOpr = new FieldOpr(field, value, opr);
		
		fieldOpr.setMultiValue(isMultiValue);
		fieldOpr.setFieldValues(values);
		
		return fieldOpr;
	}

}
