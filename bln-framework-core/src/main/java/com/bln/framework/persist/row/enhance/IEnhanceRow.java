package com.bln.framework.persist.row.enhance;

import com.bln.framework.persist.row.IRow;

public interface IEnhanceRow extends IRow{

	/**
	 * @return the originalRow
	 */
	public IRow getOriginalRow();

	/**
	 * @param originalRow the originalRow to set
	 */
	public void setOriginalRow(IRow originalRow);
	
	

}