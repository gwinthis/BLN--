package com.bln.framework.biz.service.entity.util.orderby;

import com.bln.framework.mo.IMessageObject;

/**
 * orderby �ɼ�������
 */
public interface IOrderbyExtractorUtil {

	/**
	 * ����MO��ȡorderby��Ϣ
	 * @param mo
	 * @return
	 */
	public abstract String extractor(IMessageObject mo);

}