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
 * 模板构造器
 */
public class TableExecutorBuilder extends BaseObj implements ITableExecutorBuilder{
		
	/**
	 * SQL模板库对象
	 */
	protected ISqltLib sqltLib = null;
	
	/**
	 * 生成器库
	 */
	protected IColValGeneratorLib valGeneratorLib = null;

	/**
	 * 表会话对象
	 */
	protected ITableExecutorBuildable tsTemplate = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.sql.template.builder.ISqlTBuilder#build(com.bln.framework.persist.tablestru.ITableStru)
	 */
	public ITableExecutor build(ITableStru tableStru) {
		
		//1.校验参数
		Assert.paramIsNotNull(tableStru, "tableStru");
		
		//2.生成实例
		ITableExecutorBuildable ts = tsTemplate.cloneBuildable();
		
		//赋值tableStru
		ts.setTableStru(tableStru);
		
		//赋值sqlt
		String tableName = tableStru.getTableName();
		ts.setSqlt(sqltLib.getInstance(tableName));
		
		//3.返回TableSession
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
