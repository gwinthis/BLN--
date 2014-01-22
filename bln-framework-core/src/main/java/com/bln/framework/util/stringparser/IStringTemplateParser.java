package com.bln.framework.util.stringparser;

import java.io.IOException;
import java.util.Map;

import freemarker.template.TemplateException;

public interface IStringTemplateParser {

	/**
	 * ����ָ���ַ���ģ�壬���ؽ�������ַ���
	 * @param templateSource ģ��Դ
	 * @param params ����
	 * @return ��������ַ���
	 * @throws IOException
	 * @throws TemplateException
	 */
	public abstract String parse(String templateSource, Map<String, Object> params) throws IOException, TemplateException;

}