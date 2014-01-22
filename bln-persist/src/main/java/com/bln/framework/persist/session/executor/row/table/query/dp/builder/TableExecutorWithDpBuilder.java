/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.session.executor.row.table.query.dp.builder;

import com.bln.framework.base.BaseObj;
import com.bln.framework.persist.dataprivilege.IDataPrivilege;
import com.bln.framework.persist.dataprivilege.lib.IDataPrivilegeLib;
import com.bln.framework.persist.session.executor.row.table.ITableExecutor;
import com.bln.framework.persist.session.executor.row.table.builder.ITableExecutorBuilder;
import com.bln.framework.persist.session.executor.row.table.dp.ITableExecutorWithDp;
import com.bln.framework.persist.session.executor.row.table.lib.ITableExecutorLib;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.util.asserter.Assert;

/**
 * 带数据权限的表执行器构造器
 */
public class TableExecutorWithDpBuilder extends BaseObj implements ITableExecutorBuilder{
		
	/**
	 * 数据权限库
	 */
	protected IDataPrivilegeLib dpLib = null;
	
	/**
	 * 表执行器对象
	 */
	protected ITableExecutorWithDp<ITableExecutor> tableExecutorWithDp = null;
	
	/**
	 * 表执行器库
	 */
	protected ITableExecutorLib<ITableExecutor> tableExecutorLib = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.ISqlTBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public ITableExecutor build(ITableStru tableStru) {
		
		//1.校验参数
		Assert.paramIsNotNull(tableStru, "tableStru");
		
		//2.生成实例
		ITableExecutorWithDp<ITableExecutor> tableExecutorWithDp = this.tableExecutorWithDp.clone();
		
		//3.处理TableExecutor
		
		//获得TableExecutor
		String tableName = tableStru.getTableName();
		ITableExecutor tableExecutor = tableExecutorLib.getInstance(tableName);
		
		//赋值TableExecutor
		tableExecutorWithDp.setTableExecutor(tableExecutor);
		
		//4.处理dataPrivilege
		
		//获取dataPrivilege
		IDataPrivilege dataPrivilege = null;
		try {
			dataPrivilege = dpLib.getInstance(tableName);
		} catch (Throwable e) {
		}
		
		//设置dataPrivilege
		tableExecutorWithDp.setDataPrivilege(dataPrivilege);
		
		//5.返回tableExecutorWithDp
		return tableExecutorWithDp.buildToTableExecutor();
	}

	/**
	 * @param dpLib the dpLib to set
	 */
	public void setDpLib(IDataPrivilegeLib dpLib) {
		this.dpLib = dpLib;
	}

	/**
	 * @param tableExecutorWithDp the tableExecutorWithDp to set
	 */
	public void setTableExecutorWithDp(
			ITableExecutorWithDp<ITableExecutor> tableExecutorWithDp) {
		this.tableExecutorWithDp = tableExecutorWithDp;
	}

	/**
	 * @param tableExecutorLib the tableExecutorLib to set
	 */
	public void setTableExecutorLib(
			ITableExecutorLib<ITableExecutor> tableExecutorLib) {
		this.tableExecutorLib = tableExecutorLib;
	}

}
