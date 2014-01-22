/**
 * @author gengw
 * Created on Apr 26, 2012
 */
package com.bln.framework.persist.session.executor.row.sp;

import java.io.IOException;
import java.sql.SQLException;

import com.bln.framework.persist.jdbc.template.storeprocedure.IStoreProcedureParameters;
import com.bln.framework.persist.jdbc.template.storeprocedure.StoreProcedureResult;
import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.row.SessionExecutorRowBase;
import com.bln.framework.util.asserter.Assert;

/**
 * <p>存储过程Session</p>
 * 该对象只可以执行存储过程
 */
public class StoreProcedureExecutor extends SessionExecutorRowBase<StoreProcedureExecutor> implements IStoreProcedureExecutor{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.sp.IStoreProcedureSession#execute(java.lang.String, com.bln.framework.persist.jdbc.template.storeprocedure.ISPParameters)
	 */
	public StoreProcedureResult<IRow> execute(String procedureName, IStoreProcedureParameters spParameters) throws SQLException, IOException{
		Assert.paramIsNotNull(procedureName, "procedureName");
		
		StoreProcedureResult<IRow> result = jdbcTemplate.executeStoreProcedure(conn, procedureName, spParameters);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.SessionBase#newInstance()
	 */
	@Override
	protected StoreProcedureExecutor newInstance() {
		return new StoreProcedureExecutor();
	}
}
