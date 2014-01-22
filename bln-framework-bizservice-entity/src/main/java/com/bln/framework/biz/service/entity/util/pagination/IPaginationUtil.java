package com.bln.framework.biz.service.entity.util.pagination;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;

public interface IPaginationUtil {

	/**
	 * 根据MO获取分页信息
	 * @param mo
	 * @return
	 */
	public abstract IPagination pagination(IMessageObject mo);

	/**
	 * 处理分页中的总页数和总行数
	 * @param mo
	 * @param pagination
	 * @return
	 */
	public IMessageObject setPageCount(IMessageObject mo, IPagination pagination);

}