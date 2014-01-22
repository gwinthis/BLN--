/**
 * 北京八度互联科技有限公司版权所有
 * Copyright (C) Badu Corporation. All Rights Reserved
 */
package com.bln.framework.util.stringparser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 字符串模板解析器
 * @author gengw(gengw@17guagua.com)
 * @version 2014-1-6 下午4:39:00
 */
public class StringTemplateParserFactory {
	
	/**
	 * 返回默认的字符串解析器
	 * @return
	 */
	public IStringTemplateParser getDefaultParser(){
		return getFreeMarkerParser();
	}
	
	/**
	 * 返回freeMarker字符串解析器
	 * @return
	 */
	public IStringTemplateParser getFreeMarkerParser(){
		return new StringTemplateParserFreeMarker(defaultParams);
	}
	
	public static StringTemplateParserFactory singleton(){
		return instance;
	}
	
	private static StringTemplateParserFactory instance = new StringTemplateParserFactory();
	
	/**
	 * 默认参数
	 */
	protected Map<String, Object> defaultParams;
	
	/**
	 * 初始化参数
	 */
	protected void initDefaultParams(){
		
		defaultParams = new HashMap<String, Object>();
		
		Date now = new Date();
		defaultParams.put("now", now);
		
		int n = 720;
		Date[] beforeNow = new Date[n];
		Date[] afterNow = new Date[n];
		
		for (int i = 0; i < n; i++){
			beforeNow[i] = DateUtils.addDays(now, -i);
			afterNow[i] = DateUtils.addDays(now, i);
		}
		
		defaultParams.put("beforeNow", beforeNow);
		defaultParams.put("afterNow", beforeNow);
	}
	
	private StringTemplateParserFactory(){
		initDefaultParams();
	}

}
