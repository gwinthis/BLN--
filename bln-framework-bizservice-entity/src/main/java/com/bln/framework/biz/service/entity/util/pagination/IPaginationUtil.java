package com.bln.framework.biz.service.entity.util.pagination;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

public interface IPaginationUtil {

	/**
	 * ����MO��ȡ��ҳ��Ϣ
	 * @param mo
	 * @return
	 */
	public abstract IPagination pagination(IMessageObject mo);

	/**
	 * �����ҳ�е���ҳ����������
	 * @param mo
	 * @param pagination
	 * @return
	 */
	public IMessageObject setPageCount(IMessageObject mo, IPagination pagination);

}