/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.valgenerator.sequence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import com.bln.framework.persist.tablestru.param.IParam;
import com.bln.framework.persist.valgenerator.ColumnValueGeneratorBase;
import com.bln.framework.persist.valgenerator.exception.GeneratorException;
import com.bln.framework.util.asserter.Assert;

/**
 * 序列器类型的自动生成器
 */
public abstract class SeqenceGenerator extends ColumnValueGeneratorBase{
		
	/**
	 * 序列器名称参数
	 */
	protected static final String PARM_SEQNAME = "seqname";
	
	/**
	 * dual表的名称
	 */
	protected String dualTableName = "";
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.generator.sequence.oracle.IGenerator#getValClause(com.bln.framework.persist.tablestru.param.IParam[])
	 */
	public String getValClause(IParam[] params, Object currVal){
		
		//1.检验参数
		Assert.paramIsNotNull(params, "params");
		
		//2.获取序列器名称		
		IParam seqNameParam = params[0];
		if(seqNameParam == null){
			throw new GeneratorException(" not found param " + PARM_SEQNAME + " in the params " + Arrays.toString(params));
		}
		
		//3.生成子句
		return this.getSeqValueClause(seqNameParam.getValue());
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.valgenerator.ColValueGeneratorBase#getVal(Connection, IParam[])
	 */
	public Object getVal(Connection conn, IParam[] params, Object currVal) throws SQLException{
		
		//1.检验参数
		Assert.paramIsNotNull(params, "params");
		
		//2.获取序列器名称
		IParam seqNameParam = params[0];
		if(seqNameParam == null){
			throw new GeneratorException(" not found param " + PARM_SEQNAME + " in the params " + Arrays.toString(params));
		}
		
		//3.获取值
		StringBuilder sql = new StringBuilder("SELECT ");
		
		sql.append(this.getSeqValueClause(seqNameParam.getValue()));
		sql.append(" FROM ").append(this.dualTableName);
		
		Object obj = jdbcTemplate.queryForString(conn, sql.toString(), null);
		
		//4.返回值
		return obj;
		
	}
	
	/**
	 * 获取序列器值的语句
	 * @param seqName 序列器名称
	 * @return
	 */
	protected abstract String getSeqValueClause(String seqName);	
}
