package com.bln.framework.util.stringparser;

import java.io.IOException;
import java.util.Map;

import freemarker.template.TemplateException;

public interface IStringTemplateParser {

	/**
	 * 解析指定字符串模板，返回解析后的字符串
	 * @param templateSource 模板源
	 * @param params 参数
	 * @return 解析后的字符串
	 * @throws IOException
	 * @throws TemplateException
	 */
	public abstract String parse(String templateSource, Map<String, Object> params) throws IOException, TemplateException;

}