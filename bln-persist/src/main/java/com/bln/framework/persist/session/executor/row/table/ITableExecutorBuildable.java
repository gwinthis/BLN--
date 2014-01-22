package com.bln.framework.persist.session.executor.row.table;

import com.bln.framework.persist.jdbc.converter.util.IConverterUtil;
import com.bln.framework.persist.sql.template.ISQLTemplate;
import com.bln.framework.persist.tablestru.ITableStru;
import com.bln.framework.persist.valgenerator.lib.IColValGeneratorLib;

public interface ITableExecutorBuildable {

	/**
	 * @param convertUtil the convertUtil to set
	 */
	public void setConverterUtil(IConverterUtil converterUtil) ;

	/**
	 * @param valGeneratorLib the valGeneratorLib to set
	 */
	public void setValGeneratorLib(IColValGeneratorLib valGeneratorLib) ;
	
	/**
	 * @param tableStru the tableStru to set
	 */
	public abstract void setTableStru(ITableStru tableStru);

	/**
	 * @param sqlt the sqlt to set
	 */
	public abstract void setSqlt(ISQLTemplate sqlt);
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public abstract ITableExecutorBuildable cloneBuildable();

	/**
	 * Éú³ÉTableSession
	 * @return TableSessionÊµÀý
	 */
	public ITableExecutor buildToTableExecutor();

}