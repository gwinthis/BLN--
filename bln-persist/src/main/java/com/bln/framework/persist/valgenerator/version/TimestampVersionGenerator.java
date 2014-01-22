/**
 * @author gengw
 * Created on Apr 21, 2012
 */
package com.bln.framework.persist.valgenerator.version;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;

import com.bln.framework.persist.tablestru.param.IParam;
import com.bln.framework.persist.valgenerator.ColumnValueGeneratorBase;

/**
 * 时间戳类型的版本号生成器
 */
public class TimestampVersionGenerator extends ColumnValueGeneratorBase{
	
	/**
	 * 时间戳子句
	 */
	protected String timestampClause = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.generator.sequence.oracle.IGenerator#getValClause(com.bln.framework.persist.tablestru.param.IParam[])
	 */
	public String getValClause(IParam[] params, Object currVal){
		
		return this.timestampClause;
//		//1.生成子句
//		String clause = "systimestamp";
//		
//		//2.返回子句
//		return clause;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.valgenerator.ColValueGeneratorBase#getVal(Connection, IParam[])
	 */
	public Object getVal(Connection conn, IParam[] params, Object currVal){
		
		//1.获取值
		Object obj = new Timestamp(new Date().getTime());
		
		//2.返回值
		return obj;
		
	}

	/**
	 * @return the timestampClause
	 */
	public String getTimestampClause() {
		return timestampClause;
	}

	/**
	 * @param timestampClause the timestampClause to set
	 */
	public void setTimestampClause(String timestampClause) {
		this.timestampClause = timestampClause;
	}

}
