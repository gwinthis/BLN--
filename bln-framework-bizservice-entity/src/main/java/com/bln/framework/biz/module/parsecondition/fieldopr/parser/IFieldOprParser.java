package com.bln.framework.biz.module.parsecondition.fieldopr.parser;

import com.bln.framework.biz.module.parsecondition.fieldopr.FieldOpr;

public interface IFieldOprParser {

	/**
	 * 根据字段名解析成字段操作对象
	 * @param field
	 * @param value TODO
	 * @return
	 */
	public abstract FieldOpr parser(String field, String value);

}