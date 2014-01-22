/**
 * @author gengw
 * Created on Apr 21, 2012
 */
package com.bln.framework.persist.valgenerator.version;

import java.sql.Connection;

import com.bln.framework.persist.tablestru.param.IParam;

/**
 * ��ֵ�Ͱ汾��������
 */
public class NumericVersionGenerator extends VersionGeneratorBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.generator.sequence.oracle.IGenerator#getValClause(com.bln.framework.persist.tablestru.param.IParam[])
	 */
	public String getValClause(IParam[] params, Object currVal){
		
		//1.�����Ӿ�
		String clause = null;
		
		if(currVal == null){
			clause = "1";
			
		}else{
			//1.����ֶβ���
			IParam param = this.getColumnParam(params, PARM_COLUMN);
			
			//2.�������
			String verColName = param.getValue();
			
			//# verColName + 1
			StringBuilder clauseBuilder = new StringBuilder(verColName);
			clauseBuilder.append(" + 1");
			
			clause = clauseBuilder.toString();
		}
		
		
		//2.�����Ӿ�
		return clause;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.valgenerator.ColValueGeneratorBase#getVal(Connection, IParam[])
	 */
	public Object getVal(Connection conn, IParam[] params, Object currVal){
				
		//1.���ֵ
		Integer obj = null;
		if(currVal == null){
			obj = new Integer(1);
		}else{
			Integer currInt = Integer.valueOf(currVal.toString());
			obj = new Integer(1 + currInt.intValue());
		}
		
		//3.����ֵ
		return obj;
		
	}

}
