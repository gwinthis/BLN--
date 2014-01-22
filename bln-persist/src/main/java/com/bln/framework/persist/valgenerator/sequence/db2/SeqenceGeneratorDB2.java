/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.valgenerator.sequence.db2;

import com.bln.framework.persist.valgenerator.sequence.SeqenceGenerator;

/**
 * ���������͵��Զ�������
 */
public class SeqenceGeneratorDB2 extends SeqenceGenerator{
			
	public SeqenceGeneratorDB2(){
		this.dualTableName = "SYSIBM.DUAL";
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.valgenerator.sequence.SeqenceGenerator#getSeqValueClause(java.lang.String)
	 */
	@Override
	protected String getSeqValueClause(String seqName) {
		StringBuilder seqClause = new StringBuilder("NEXT VALUE FOR ").append(seqName);
		return seqClause.toString();
	}
	
}
