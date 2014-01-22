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
 * ������Ȩ�޵ı�ִ����������
 */
public class TableExecutorWithDpBuilder extends BaseObj implements ITableExecutorBuilder{
		
	/**
	 * ����Ȩ�޿�
	 */
	protected IDataPrivilegeLib dpLib = null;
	
	/**
	 * ��ִ��������
	 */
	protected ITableExecutorWithDp<ITableExecutor> tableExecutorWithDp = null;
	
	/**
	 * ��ִ������
	 */
	protected ITableExecutorLib<ITableExecutor> tableExecutorLib = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.ISqlTBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public ITableExecutor build(ITableStru tableStru) {
		
		//1.У�����
		Assert.paramIsNotNull(tableStru, "tableStru");
		
		//2.����ʵ��
		ITableExecutorWithDp<ITableExecutor> tableExecutorWithDp = this.tableExecutorWithDp.clone();
		
		//3.����TableExecutor
		
		//���TableExecutor
		String tableName = tableStru.getTableName();
		ITableExecutor tableExecutor = tableExecutorLib.getInstance(tableName);
		
		//��ֵTableExecutor
		tableExecutorWithDp.setTableExecutor(tableExecutor);
		
		//4.����dataPrivilege
		
		//��ȡdataPrivilege
		IDataPrivilege dataPrivilege = null;
		try {
			dataPrivilege = dpLib.getInstance(tableName);
		} catch (Throwable e) {
		}
		
		//����dataPrivilege
		tableExecutorWithDp.setDataPrivilege(dataPrivilege);
		
		//5.����tableExecutorWithDp
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
