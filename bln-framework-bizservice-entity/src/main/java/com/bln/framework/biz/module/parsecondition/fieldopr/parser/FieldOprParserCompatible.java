/**
 * @author gengw
 * Created on Nov 23, 2012
 */
package com.bln.framework.biz.module.parsecondition.fieldopr.parser;

import com.bln.framework.biz.module.parsecondition.fieldopr.FieldOpr;

/**
 * �ֶβ�����������
 */
public class FieldOprParserCompatible extends FieldOprParser{
	
	/**
	 * �����ֶ����������ֶβ�������
	 * @param field
	 * @return
	 */
	public FieldOpr parser(String field, String value ){
		
		//Ĭ�Ͻ�����ʽ
		FieldOpr fieldOpr = super.parser(field, value);
		
		//���ݾ����͵Ĳ�������ʽ
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
