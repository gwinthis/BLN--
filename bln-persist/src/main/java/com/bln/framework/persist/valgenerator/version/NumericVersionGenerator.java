/**
 * @author gengw
 * Created on Apr 21, 2012
 */
package com.bln.framework.persist.valgenerator.version;

import java.sql.Connection;

import com.bln.framework.persist.tablestru.param.IParam;

/**
 * 数值型版本号生成器
 */
public class NumericVersionGenerator extends VersionGeneratorBase{

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.generator.sequence.oracle.IGenerator#getValClause(com.bln.framework.persist.tablestru.param.IParam[])
	 */
	public String getValClause(IParam[] params, Object currVal){
		
		//1.构造子句
		String clause = null;
		
		if(currVal == null){
			clause = "1";
			
		}else{
			//1.获得字段参数
			IParam param = this.getColumnParam(params, PARM_COLUMN);
			
			//2.构造语句
			String verColName = param.getValue();
			
			//# verColName + 1
			StringBuilder clauseBuilder = new StringBuilder(verColName);
			clauseBuilder.append(" + 1");
			
			clause = clauseBuilder.toString();
		}
		
		
		//2.返回子句
		return clause;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.valgenerator.ColValueGeneratorBase#getVal(Connection, IParam[])
	 */
	public Object getVal(Connection conn, IParam[] params, Object currVal){
				
		//1.获得值
		Integer obj = null;
		if(currVal == null){
			obj = new Integer(1);
		}else{
			Integer currInt = Integer.valueOf(currVal.toString());
			obj = new Integer(1 + currInt.intValue());
		}
		
		//3.返回值
		return obj;
		
	}

}
