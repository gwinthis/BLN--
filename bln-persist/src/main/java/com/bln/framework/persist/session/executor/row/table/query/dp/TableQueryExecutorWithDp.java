/**
 * @author gengw
 * Created on May 4, 2012
 */
package com.bln.framework.persist.session.executor.row.table.query.dp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.session.executor.row.table.ITableExecutor;
import com.bln.framework.persist.session.executor.row.table.dp.ITableExecutorWithDp;
import com.bln.framework.persist.session.executor.row.table.dp.TableExecutorWithDp;
import com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor;

/**
 * 使用权限的表执行器
 */
public class TableQueryExecutorWithDp extends TableExecutorWithDp<TableQueryExecutorWithDp, ITableQueryExecutor> implements ITableExecutorWithDp<ITableQueryExecutor>, ITableQueryExecutor{

	/**
	 * @param conClause
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @see com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor#count(com.bln.framework.persist.session.executor.material.condition.IConditionClause)
	 */
	public int count(IConditionClause conClause) throws SQLException, IOException {
		conClause = this.addPrivilege(conClause);
		return tableExecutor.count(conClause);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor#isExists(com.bln.framework.persist.session.executor.material.condition.IConditionClause)
	 */
	public boolean isExists(IConditionClause conClause) throws SQLException, IOException {
		conClause = this.addPrivilege(conClause);
		return tableExecutor.isExists(conClause);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor#queryFirstRow(com.bln.framework.persist.session.executor.material.condition.IConditionClause, com.bln.framework.persist.session.executor.material.pagination.IPagination, java.lang.String)
	 */
	public IRow queryFirstRow(IConditionClause conClause, IPagination pagination, String orderby)throws SQLException, IOException {
		conClause = this.addPrivilege(conClause);
		return tableExecutor.queryFirstRow(conClause, pagination, orderby);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor#query(com.bln.framework.persist.session.executor.material.condition.IConditionClause, com.bln.framework.persist.session.executor.material.pagination.IPagination, java.lang.String)
	 */
	public List<IRow> query(IConditionClause conClause, IPagination pagination, String orderby) throws SQLException, IOException {
		
		conClause = this.addPrivilege(conClause);
		return tableExecutor.query(conClause, pagination, orderby);
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor#queryForUpdate(com.bln.framework.persist.session.executor.material.condition.IConditionClause, java.lang.String)
	 */
	public List<IRow> queryForUpdate(IConditionClause conClause, String orderby)throws SQLException, IOException {
		conClause = this.addPrivilege(conClause);
		return tableExecutor.queryForUpdate(conClause, orderby);
	}

	/**
	 * @param connection
	 * @see com.bln.framework.persist.session.executor.ISessionExecutor#setConnection(java.sql.Connection)
	 */
	public void setConnection(Connection connection) {
		tableExecutor.setConnection(connection);
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.dp.TableExecutorWithDp#newInstance()
	 */
	@Override
	protected TableQueryExecutorWithDp newInstance() {
		return new TableQueryExecutorWithDp();
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.dp.ITableExecutorWithDp#buildToTableExecutor()
	 */
	public ITableExecutor buildToTableExecutor() {
		return this;
	}


}
