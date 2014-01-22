/**
 * @author gengw
 * Created on Aug 8, 2012
 */
package com.bln.framework.biz.service.entity.util.orderby;

import com.bln.framework.mo.IMessageObject;

/**
 * orderby ÌáÈ¡Æ÷
 */
public class OrderbyExtractor implements IOrderbyExtractorUtil {

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.util.orderby.IOrderbyExtractorUtil#extractor(com.bln.framework.mo.IMessageObject)
	 */
	public String extractor(IMessageObject mo) {
		
		String orderby = mo.getParamOfReq("ORDERBY");
		return orderby;
	}
}
