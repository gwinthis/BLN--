/**
 * �����˶Ȼ����Ƽ����޹�˾��Ȩ����
 * Copyright (C) Badu Corporation. All Rights Reserved
 */
package com.bln.framework.util.stringparser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

/**
 * �ַ���ģ�������
 * @author gengw(gengw@17guagua.com)
 * @version 2014-1-6 ����4:39:00
 */
public class StringTemplateParserFactory {
	
	/**
	 * ����Ĭ�ϵ��ַ���������
	 * @return
	 */
	public IStringTemplateParser getDefaultParser(){
		return getFreeMarkerParser();
	}
	
	/**
	 * ����freeMarker�ַ���������
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
	 * Ĭ�ϲ���
	 */
	protected Map<String, Object> defaultParams;
	
	/**
	 * ��ʼ������
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
