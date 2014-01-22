/**
 * @author gengw(gengw@17guagua.com)
 * @version 2013-8-3 ����6:15:41
 */
package com.bln.framework.edi.ipextractor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * ��ѡ�жϱ�����ؼ��֣��ٵ���getRemoteAddr������ȡIP
 * 
 */
public class HTTPIPExtractor implements IExtractor4IP<HttpServletRequest>{

	private static final String FORWARDED_KEY = "x-forwarded-for";
	
	public String extract(HttpServletRequest request){
		
		String ip = request.getHeader(FORWARDED_KEY);
		if(StringUtils.isEmpty(ip)){
			ip = request.getRemoteAddr();
		}else{
			int idx = ip.indexOf(",");
			if(idx > 0){
				ip = ip.substring(0, idx);
			}
		}
		
		return ip;
	}
}
