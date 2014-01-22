/**
 * @author gengw
 * Created on Apr 13, 2012
 */
package com.bln.framework.persist.session.executor.row.table.query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bln.framework.persist.row.IRow;
import com.bln.framework.persist.session.executor.material.condition.IConditionClause;
import com.bln.framework.persist.session.executor.material.pagination.IPagination;
import com.bln.framework.persist.session.executor.row.table.TableExecutorBase;
import com.bln.framework.persist.sql.entity.select.ISelectSqlEntity;

/**
 * 数据库中表的查询会话类
 */
public class TableQueryExecutor extends TableExecutorBase<TableQueryExecutor> implements ITableQueryExecutor{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#isExists(com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public boolean isExists(IConditionClause conClause) throws SQLException, IOException{
		
		//1.获取查询SQL语句
		ISelectSqlEntity selectSql = sqlt.isExistsSql(conClause);
		
		//2.执行SQL
		String result = jdbcTemplate.queryForString(conn, selectSql.getSqlInfo(), selectSql.getParams());
		
		//转换成是否存在
		boolean exists = !StringUtils.isEmpty(result);

		//3.返回结果
		return exists;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#count(com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public int count(IConditionClause conClause) throws SQLException, IOException{
		
		//1.获取查询SQL语句
		ISelectSqlEntity selectSql = sqlt.countSql(conClause);
		
		//2.执行SQL
		String result = jdbcTemplate.queryForString(conn, selectSql.getSqlInfo(), selectSql.getParams());
		
		//转换成行数
		int count = 0;
		if(!StringUtils.isEmpty(result)){
			count = Integer.parseInt(result);
		}
		
		//3.返回结果
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor#queryFirstRow(com.bln.framework.persist.session.executor.material.condition.IConditionClause, com.bln.framework.persist.session.executor.material.pagination.IPagination, java.lang.String)
	 */
	public IRow queryFirstRow(IConditionClause conClause, IPagination pagination, String orderby) throws SQLException, IOException{
		
		//1.获取符合条件的记录
		List<IRow> rows = this.query(conClause, pagination, orderby);
		
		//2.获取第一行记录
		IRow row = null;
		if(rows != null && rows.size() > 0){
			row = rows.get(0);
		}
		
		//3.返回第一行记录
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#query(com.bln.framework.persist.session.material.condition.IConditionClause, com.bln.framework.persist.session.material.pagination.IPagination, java.lang.String)
	 */
	public List<IRow> query(IConditionClause conClause, IPagination pagination, String orderby) throws SQLException, IOException{
		
		//1.获取查询SQL语句
		ISelectSqlEntity selectSql = sqlt.selectSql(conClause, null, orderby);
		
		//2.如果有分页信息，处理总行数和总页数
		if(pagination != null){
			pagination(selectSql, pagination);
		}
			
		//3.执行SQL
		List<IRow> rows = jdbcTemplate.query(conn, selectSql.getSqlInfo(), selectSql.getParams(), selectSql.getColumns());

		//4.返回结果
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#queryForUpdate(com.bln.framework.persist.session.material.condition.IConditionClause, java.lang.String)
	 */
	public List<IRow> queryForUpdate(IConditionClause conClause, String orderby) throws SQLException, IOException{
		
		//1.获取查询SQL语句
		ISelectSqlEntity selectSql = sqlt.selectSql(conClause, null, orderby);
		
		//2.添加锁定
		sqlt.queryForUpdateSql(selectSql);
		
		//3.执行SQL
		List<IRow> rows = jdbcTemplate.query(conn, selectSql.getSqlInfo(), selectSql.getParams(), selectSql.getColumns());

		//4.返回结果
		return rows;
	}

	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.SessionExecutorBase#newInstance()
	 */
	@Override
	protected TableQueryExecutor newInstance() {
		return new TableQueryExecutor();
	}


}
