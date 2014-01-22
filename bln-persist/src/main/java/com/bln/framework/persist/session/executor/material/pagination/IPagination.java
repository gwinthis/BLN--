package com.bln.framework.persist.session.executor.material.pagination;

public interface IPagination {

	/**
	 * @return the pageCount
	 */
	public abstract int getPageCount();

	/**
	 * @param pageCount the pageCount to set
	 */
	public abstract void setPageCount(int pageCount);

	/**
	 * @return the pageNo
	 */
	public abstract int getPageNo();

	/**
	 * @param pageNo the pageNo to set
	 */
	public abstract void setPageNo(int pageNo);

	/**
	 * @return the rowsOfPage
	 */
	public abstract int getRowsOfPage();

	/**
	 * @param rowsOfPage the rowsOfPage to set
	 */
	public abstract void setRowsOfPage(int rowsOfPage);

	/**
	 * @return the rowCount
	 */
	public abstract long getRowCount();

	/**
	 * @param rowCount the rowCount to set
	 */
	public abstract void setRowCount(long rowCount);

	/**
	 * ��ȡ��ʼ��
	 * @return ��ʼ�к�
	 */
	public int getStartRowNO();

	/**
	 * ��ȡ�����к�
	 * @return �����к�
	 */
	public int getEndRowNO();

}