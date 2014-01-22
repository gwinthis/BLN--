/**
 * @author Gengw
 * Created At 2008-05-08
 */
package com.bln.framework.persist.session.executor.material.pagination;

/**
 * 分页信息
 */
public class Pagination implements IPagination {

	/**
	 * 一页的行数
	 */
	protected int rowsOfPage = 0;
	
	/**
	 * 要查询的页号
	 */
	protected int pageNo = 0;
	
	/**
	 * 页总数
	 */
	protected int pageCount = 0;
	
	/**
	 * 总行数
	 */
	protected long rowCount = 0;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.pagination.IPagination#getPageCount()
	 */
	public int getPageCount() {
		return pageCount;
	}
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.pagination.IPagination#setPageCount(int)
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.pagination.IPagination#getPageNo()
	 */
	public int getPageNo() {
		return pageNo;
	}
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.pagination.IPagination#setPageNo(int)
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.pagination.IPagination#getRowsOfPage()
	 */
	public int getRowsOfPage() {
		return rowsOfPage;
	}
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.pagination.IPagination#setRowsOfPage(int)
	 */
	public void setRowsOfPage(int rowsOfPage) {
		this.rowsOfPage = rowsOfPage;
	}
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.pagination.IPagination#getRowCount()
	 */
	public long getRowCount() {
		return rowCount;
	}
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.pagination.IPagination#setRowCount(long)
	 */
	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.pagination.IPagination#getStartRowNO()
	 */
	public int getStartRowNO(){
		return (pageNo - 1) * rowsOfPage;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.pagination.IPagination#getEndRowNO()
	 */
	public int getEndRowNO(){
		return getStartRowNO() + rowsOfPage;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		
		StringBuilder desc = new StringBuilder(this.getClass().getName() + " description: ");
		
		desc.append("rowsOfPage : ").append(rowsOfPage).append("\r\n");
		desc.append("pageNo : ").append(pageNo).append("\r\n");
		desc.append("pageCount : ").append(pageCount).append("\r\n");
		desc.append("rowCount : ").append(rowCount).append("\r\n");
		
		return desc.toString();
	}
}
