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
 * ʱ������͵İ汾��������
 */
public class TimestampVersionGenerator extends ColumnValueGeneratorBase{
	
	/**
	 * ʱ����Ӿ�
	 */
	protected String timestampClause = null;
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.generator.sequence.oracle.IGenerator#getValClause(com.bln.framework.persist.tablestru.param.IParam[])
	 */
	public String getValClause(IParam[] params, Object currVal){
		
		return this.timestampClause;
//		//1.�����Ӿ�
//		String clause = "systimestamp";
//		
//		//2.�����Ӿ�
//		return clause;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.valgenerator.ColValueGeneratorBase#getVal(Connection, IParam[])
	 */
	public Object getVal(Connection conn, IParam[] params, Object currVal){
		
		//1.��ȡֵ
		Object obj = new Timestamp(new Date().getTime());
		
		//2.����ֵ
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
