package com.bln.framework.biz.module.parsecondition.fieldopr.parser;

import com.bln.framework.biz.module.parsecondition.fieldopr.FieldOpr;

public interface IFieldOprParser {

	/**
	 * �����ֶ����������ֶβ�������
	 * @param field
	 * @param value TODO
	 * @return
	 */
	public abstract FieldOpr parser(String field, String value);

}