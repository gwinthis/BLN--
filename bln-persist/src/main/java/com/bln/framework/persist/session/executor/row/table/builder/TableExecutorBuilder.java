/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.session.executor.row.table.builder;

import com.bln.framework.base.BaseObj;
import com.bln.framework.persist.session.executor.row.table.ITableExecutor;
import com.bln.framework.persist.session.executor.row.table.ITableExecutorBuildable;
import com.bln.framework.persist.sql.template.lib.ISqltLib;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.valgenerator.lib.IColValGeneratorLib;
import com.bln.framework.util.asserter.Assert;

/**
 * ģ�幹����
 */
public class TableExecutorBuilder extends BaseObj implements ITableExecutorBuilder{
		
	/**
	 * SQLģ������
	 */
	protected ISqltLib sqltLib = null;
	
	/**
	 * ��������
	 */
	protected IColValGeneratorLib valGeneratorLib = null;

	/**
	 * ��Ự����
	 */
	protected ITableExecutorBuildable tsTemplate = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.ISqlTBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public ITableExecutor build(ITableStru tableStru) {
		
		//1.У�����
		Assert.paramIsNotNull(tableStru, "tableStru");
		
		//2.����ʵ��
		ITableExecutorBuildable ts = tsTemplate.cloneBuildable();
		
		//��ֵtableStru
		ts.setTableStru(tableStru);
		
		//��ֵsqlt
		String tableName = tableStru.getTableName();
		ts.setSqlt(sqltLib.getInstance(tableName));
		
		//3.����TableSession
		return ts.buildToTableExecutor();
	}

	/**
	 * @param sqltLib the sqltLib to set
	 */
	public void setSqltLib(ISqltLib sqltLib) {
		this.sqltLib = sqltLib;
	}

	/**
	 * @param valGeneratorLib the valGeneratorLib to set
	 */
	public void setValGeneratorLib(IColValGeneratorLib valGeneratorLib) {
		this.valGeneratorLib = valGeneratorLib;
	}

	/**
	 * @param tsTemplate the tsTemplate to set
	 */
	public void setTsTemplate(ITableExecutorBuildable tsTemplate) {
		this.tsTemplate = tsTemplate;
	}
	
}
