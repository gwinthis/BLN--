/**
 * @author gengw
 * Created on May 21, 2012
 */
package com.bln.framework.biz.service.entity.util.pagination;

import org.apache.commons.lang3.math.NumberUtils;

import com.bln.framework.mo.IMessageObject;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.session.executor.material.pagination.Pagination;
import com.bln.framework.util.asserter.Assert;

/**
 * ��ҳ����
 */
public class PaginationUtil implements IPaginationUtil {

	/**
	 * ҳ�ŵĽڵ�����
	 */
	protected String pageNoNodeName = "pageNo";

	/**
	 * һҳ�������Ľڵ�����
	 */
	protected String rowsNodeName = "rowsOfPage";

	/**
	 * ��ҳ���Ľڵ�����
	 */
	protected String pageCountNodeName = "pageCount";
	
	/**
	 * �������Ľڵ�����
	 */
	protected String rowCountNodeName = "rowCount";
	
	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.util.IPaginationUtil#pagination(com.bln.framework.mo.IMessageObject)
	 */
	public IPagination pagination(IMessageObject mo) {

		//У�����
		Assert.paramIsNotNull(mo, "mo");

		//���ɷ�ҳ������Ϣ
		IPagination pagination = null;
		
		//��ȡҳ������������㣬���ɷ�ҳ������������
		int pageNo = NumberUtils.toInt(mo.getParamOfReq(pageNoNodeName));
		if(pageNo > 0){
			
			pagination = new Pagination();
			
			pagination.setPageNo(pageNo);
			
			pagination.setRowsOfPage(NumberUtils.toInt(mo.getParamOfReq(rowsNodeName)));
			pagination.setPageCount(NumberUtils.toInt(mo.getParamOfReq(pageCountNodeName)));
			pagination.setRowCount(NumberUtils.toInt(mo.getParamOfReq(rowCountNodeName)));
		}

		//���ط�ҳ��Ϣ
		return pagination;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.biz.service.util.IPaginationUtil#setPageCount(com.bln.framework.mo.IMessageObject, com.bln.framework.persist.session.executor.material.pagination.IPagination)
	 */
	public IMessageObject setPageCount(IMessageObject mo, IPagination pagination) {

		if ( mo == null || pagination == null){
			return mo;
		}
		
		mo.setParamOfResp(pageNoNodeName, String.valueOf(pagination.getPageNo()));
		mo.setParamOfResp(rowsNodeName, String.valueOf(pagination.getRowsOfPage()));
		mo.setParamOfResp(pageCountNodeName, String.valueOf(pagination.getPageCount()));
		mo.setParamOfResp(rowCountNodeName, String.valueOf(pagination.getRowCount()));
		
		return mo;
	}
	 
	/**
	 * @return the pageNoNodeName
	 */
	public String getPageNoNodeName() {
		return pageNoNodeName;
	}

	/**
	 * @param pageNoNodeName the pageNoNodeName to set
	 */
	public void setPageNoNodeName(String pageNoNodeName) {
		this.pageNoNodeName = pageNoNodeName;
	}

	/**
	 * @return the rowsNodeName
	 */
	public String getRowsNodeName() {
		return rowsNodeName;
	}

	/**
	 * @param rowsNodeName the rowsNodeName to set
	 */
	public void setRowsNodeName(String rowsNodeName) {
		this.rowsNodeName = rowsNodeName;
	}

	/**
	 * @return the pageCountNodeName
	 */
	public String getPageCountNodeName() {
		return pageCountNodeName;
	}

	/**
	 * @param pageCountNodeName the pageCountNodeName to set
	 */
	public void setPageCountNodeName(String pageCountNodeName) {
		this.pageCountNodeName = pageCountNodeName;
	}

	/**
	 * @return the rowCountNodeName
	 */
	public String getRowCountNodeName() {
		return rowCountNodeName;
	}

	/**
	 * @param rowCountNodeName the rowCountNodeName to set
	 */
	public void setRowCountNodeName(String rowCountNodeName) {
		this.rowCountNodeName = rowCountNodeName;
	}
}
