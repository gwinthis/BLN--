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
 * ���������͵��Զ�������
 */
public abstract class SeqenceGenerator extends ColumnValueGeneratorBase{
		
	/**
	 * ���������Ʋ���
	 */
	protected static final String PARM_SEQNAME = "seqname";
	
	/**
	 * dual�������
	 */
	protected String dualTableName = "";
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.generator.sequence.oracle.IGenerator#getValClause(com.bln.framework.persist.tablestru.param.IParam[])
	 */
	public String getValClause(IParam[] params, Object currVal){
		
		//1.�������
		Assert.paramIsNotNull(params, "params");
		
		//2.��ȡ����������		
		IParam seqNameParam = params[0];
		if(seqNameParam == null){
			throw new GeneratorException(" not found param " + PARM_SEQNAME + " in the params " + Arrays.toString(params));
		}
		
		//3.�����Ӿ�
		return this.getSeqValueClause(seqNameParam.getValue());
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.valgenerator.ColValueGeneratorBase#getVal(Connection, IParam[])
	 */
	public Object getVal(Connection conn, IParam[] params, Object currVal) throws SQLException{
		
		//1.�������
		Assert.paramIsNotNull(params, "params");
		
		//2.��ȡ����������
		IParam seqNameParam = params[0];
		if(seqNameParam == null){
			throw new GeneratorException(" not found param " + PARM_SEQNAME + " in the params " + Arrays.toString(params));
		}
		
		//3.��ȡֵ
		StringBuilder sql = new StringBuilder("SELECT ");
		
		sql.append(this.getSeqValueClause(seqNameParam.getValue()));
		sql.append(" FROM ").append(this.dualTableName);
		
		Object obj = jdbcTemplate.queryForString(conn, sql.toString(), null);
		
		//4.����ֵ
		return obj;
		
	}
	
	/**
	 * ��ȡ������ֵ�����
	 * @param seqName ����������
	 * @return
	 */
	protected abstract String getSeqValueClause(String seqName);	
}
