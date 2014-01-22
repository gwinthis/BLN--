/**
 * 北京八度互联科技有限公司版权所有
 * Copyright (C) Badu Corporation. All Rights Reserved
 */
package com.bln.framework.util.stringparser;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 字符串模板解析类
 * @author gengw(gengw@17guagua.com)
 * @version 2014-1-6 下午3:09:57
 */
public class StringTemplateParserFreeMarker implements IStringTemplateParser {

	/**
	 * 解析指定字符串模板，返回解析后的字符串
	 * @param templateSource 模板源
	 * @param params 参数
	 * @return 解析后的字符串
	 * @throws IOException
	 * @throws TemplateException
	 */
	public String parse(String templateSource, Map<String, Object> params) throws IOException, TemplateException{

		StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
		stringTemplateLoader.putTemplate(TEMPLATE_KEYWORDS, templateSource);
		
		config.setTemplateLoader(stringTemplateLoader);
		Template template = config.getTemplate(TEMPLATE_KEYWORDS);
		
		StringWriter stringWriter = new StringWriter();
		Map<String, Object> allParams = unionMap(defaultParams, params);
		
		template.process(allParams, stringWriter);
		String result = stringWriter.toString();
		
		return result;
	}
	
	/**
	 * 合并参数Map，并返回新的实例，第二个map会覆盖第一个map中的值
	 * @param first map1
	 * @param second map2
	 * @return
	 */
	protected <K,V>Map<K, V> unionMap(Map<K, V> first, Map<K, V> second){
		
		Map<K, V> result = new HashMap<K, V>();
		if(first != null){
			result.putAll(first);
		}
		
		if(second != null){
			result.putAll(second);
		}
		
		return result;
	}

	private static final String TEMPLATE_KEYWORDS = "DEFAULT";
	
	protected Configuration config = new Configuration();
	
	protected Map<String, Object> defaultParams = null;


	/**
	 * 
	 */
	public StringTemplateParserFreeMarker() {
		super();
	}
	
	public StringTemplateParserFreeMarker(Map<String, Object> defaultParams){
		this();
		this.defaultParams = defaultParams;
	}
	
	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = (Configuration) config.clone();
	}

	public Map<String, Object> getDefaultParams() {
		return defaultParams;
	}

	public void setDefaultParams(Map<String, Object> defaultParams) {
		this.defaultParams = defaultParams;
	}
}
