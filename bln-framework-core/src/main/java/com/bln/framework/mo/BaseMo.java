/**
 * @author gengw
 * Created on Apr 6, 2012
 */
package com.bln.framework.mo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.util.asserter.Assert;

/**
 * 消息对象的基类
 */
public abstract class BaseMo implements IMessageObject{

	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#getRowOfReq(java.lang.String)
	 */
	public IRow getRowOfReq(String paramNm) {
		
		List<IRow> rows = this.getRowsOfReq(paramNm);
		Assert.isTrue(rows != null && rows.size() > 0, "{0} in request, can't be null!", paramNm);
		
		IRow row = rows.get(0);
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#setRowOfReq(java.lang.String, com.bln.framework.persist.row.IRow)
	 */
	public void setRowOfReq(String paramNm, IRow row) {
		
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		List<IRow> rows = new ArrayList<IRow>(1);
		rows.add(row);
		
		this.setRowsOfReq(paramNm, rows);
	}	
	
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#getRowOfResp(java.lang.String)
	 */
	public IRow getRowOfResp(String paramNm) {
		
		List<IRow> rows = this.getRowsOfResp(paramNm);
		Assert.isTrue(rows != null && rows.size() > 0, "{0} in request, can't be null!", paramNm);
		
		IRow row = rows.get(0);
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.mo.IMessageObject#setRowOfResp(java.lang.String, com.bln.framework.persist.row.IRow)
	 */
	public void setRowOfResp(String paramNm, IRow row) {
		
		Assert.paramIsNotNull(paramNm, "paramNm");
		
		List<IRow> rows = new ArrayList<IRow>(1);
		rows.add(row);
		
		this.setRowsOfResp(paramNm, rows);
	}
	
	/**
	 * 从请求信息中获取指定表的数据
	 * @param tableNames 指定表名
	 * @return 指定表的数据
	 * @throws Throwable 
	 */
	public Map<String, List<IRow>> getRowsOfReq(String[] tableNames){
		
		Map<String, List<IRow>> allRows = this.getAllRowsOfReq();
		if(allRows != null && !allRows.isEmpty() && tableNames != null && tableNames.length > 0){
			allRows = this.filter(allRows, tableNames);
		}
		return allRows;
	}
	
	/**
	 * 从响应信息中获取指定表的数据
	 * @param tableNames 指定表名
	 * @return 指定表的数据
	 * @throws Throwable 
	 */
	public Map<String, List<IRow>> getRowsOfResp(String[] tableNames){
		
		Map<String, List<IRow>> allRows = this.getAllRowsOfResp();
		if(allRows != null && !allRows.isEmpty() && tableNames != null && tableNames.length > 0){
			allRows = this.filter(allRows, tableNames);
		}
		return allRows;
	}
	
	/**
	 * 过滤Map中的数据
	 * @param allRows 所有数据
	 * @param tableNames 要过滤的表名
	 * @return 过滤后新的Map
	 */
	protected Map<String, List<IRow>> filter(Map<String, List<IRow>> allRows, String[] tableNames) {
		
		Map<String, List<IRow>> rows = new HashMap<String, List<IRow>>(allRows.size());
		if(tableNames != null && tableNames.length > 0){
			for ( String tableName : tableNames){
				rows.put(tableName, allRows.get(tableName));
			}
		}
		return rows;
	}
	
	
}
