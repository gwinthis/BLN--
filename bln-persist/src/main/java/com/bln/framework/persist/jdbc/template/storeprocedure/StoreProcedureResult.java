/**
 * @author gengw
 * Created at 2013-08-05
 */
package com.bln.framework.persist.jdbc.template.storeprocedure;

import java.util.List;

import com.bln.framework.base.BaseObj;

/**
 * 存储过程返回结果
 */
public class StoreProcedureResult<T> extends BaseObj {

	/**
	 * 结果集返回行列表
	 */
	protected List<T> resultSetRows = null;

	/**
	 * 更新行数
	 */
	protected int updateCount = -1;
	
	/**
	 * 输出参数
	 */
	protected List<Object> outParams = null;

	/**
	 * @return the outParams
	 */
	public List<Object> getOutParams() {
		return outParams;
	}

	/**
	 * @param outParams the outParams to set
	 */
	public void setOutParams(List<Object> outParams) {
		this.outParams = outParams;
	}

	/**
	 * @return the updateCount
	 */
	public int getUpdateCount() {
		return updateCount;
	}

	/**
	 * @param updateCount the updateCount to set
	 */
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}

	/**
	 * @return the resultSetRows
	 */
	public List<T> getResultSetRows() {
		return resultSetRows;
	}

	/**
	 * @param resultSetRows the resultSetRows to set
	 */
	public void setResultSetRows(List<T> resultSetRows) {
		this.resultSetRows = resultSetRows;
	}
}
