/**
 * @author gengw
 * Created on Nov 23, 2012
 */
package com.bln.framework.biz.module.parsecondition.fieldopr.parser;

import com.bln.framework.biz.module.parsecondition.fieldopr.FieldOpr;

/**
 * 字段操作解析对象
 */
public class FieldOprParserCompatible extends FieldOprParser{
	
	/**
	 * 根据字段名解析成字段操作对象
	 * @param field
	 * @return
	 */
	public FieldOpr parser(String field, String value ){
		
		//默认解析方式
		FieldOpr fieldOpr = super.parser(field, value);
		
		//兼容旧类型的操作符方式
		if(" = ".equals(fieldOpr.getOpr())){
			
			String opr = " = ";
			if(field.endsWith("_TO")){
				field = field.substring(0, field.length() - 3);
				opr = " <= ";
			}else if (field.endsWith("_FROM")){
				field = field.substring(0, field.length() - 5);
				opr = " >= ";
			}
			
			fieldOpr.setField(field);
			fieldOpr.setOpr(opr);
		}

		return fieldOpr;
	}

}
