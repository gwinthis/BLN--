/**
 * @author gengw
 * Created at 2013-10-08
 */
package com.bln.framework.biz.service.instance.entity;

import com.bln.framework.biz.service.entity.util.orderby.IOrderbyExtractorUtil;
import com.bln.framework.biz.service.entity.util.pagination.IPaginationUtil;

/**
 * 实体查询服务基类
 */
public abstract class EntityQueryServiceBase extends EntityServiceBase{

	/**
	 * 条件节点名称
	 */
	protected String conditionNodeName = "condition";
	
	/**
	 * 分页信息提取器
	 */
	protected IPaginationUtil paginationUtil = null;

	/**
	 * 排序信息提取器
	 */
	protected IOrderbyExtractorUtil orderbyExtractor = null;

	/**
	 * @return the conditionNodeName
	 */
	public String getConditionNodeName() {
		return conditionNodeName;
	}

	/**
	 * @param conditionNodeName the conditionNodeName to set
	 */
	public void setConditionNodeName(String conditionNodeName) {
		this.conditionNodeName = conditionNodeName;
	}

	/**
	 * @return the paginationUtil
	 */
	public IPaginationUtil getPaginationUtil() {
		return paginationUtil;
	}

	/**
	 * @param paginationUtil the paginationUtil to set
	 */
	public void setPaginationUtil(IPaginationUtil paginationUtil) {
		this.paginationUtil = paginationUtil;
	}

	/**
	 * @return the orderbyExtractor
	 */
	public IOrderbyExtractorUtil getOrderbyExtractor() {
		return orderbyExtractor;
	}

	/**
	 * @param orderbyExtractor the orderbyExtractor to set
	 */
	public void setOrderbyExtractor(IOrderbyExtractorUtil orderbyExtractor) {
		this.orderbyExtractor = orderbyExtractor;
	}
}
