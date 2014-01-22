/**
 * @author gengw
 * Created on Apr 16, 2012
 */
package com.bln.framework.persist.valgenerator.sequence.oracle;

import com.bln.framework.persist.valgenerator.sequence.SeqenceGenerator;

/**
 * 序列器类型的自动生成器
 */
public class SeqenceGeneratorOrcl extends SeqenceGenerator{
			
	public SeqenceGeneratorOrcl(){
		this.dualTableName = "DUAL";
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.valgenerator.sequence.SeqenceGenerator#getSeqValueClause(java.lang.String)
	 */
	@Override
	protected String getSeqValueClause(String seqName) {
		StringBuilder seqClause = new StringBuilder(seqName).append(".NEXTVAL");
		return seqClause.toString();
	}
	
}
