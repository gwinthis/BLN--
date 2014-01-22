package com.bln.framework.biz.service.entity.util.orderby;

import com.bln.framework.mo.IMessageObject;

/**
 * orderby 采集器工具
 */
public interface IOrderbyExtractorUtil {

	/**
	 * 根据MO获取orderby信息
	 * @param mo
	 * @return
	 */
	public abstract String extractor(IMessageObject mo);

}