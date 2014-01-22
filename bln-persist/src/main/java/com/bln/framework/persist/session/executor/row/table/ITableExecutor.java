package com.bln.framework.persist.session.executor.row.table;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.ISessionExecutor;

public interface ITableExecutor extends ISessionExecutor<IRow>{

	public ITableExecutor clone();

}