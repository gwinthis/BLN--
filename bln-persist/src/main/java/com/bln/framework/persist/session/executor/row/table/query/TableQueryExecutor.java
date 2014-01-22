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
 * ���ݿ��б�Ĳ�ѯ�Ự��
 */
public class TableQueryExecutor extends TableExecutorBase<TableQueryExecutor> implements ITableQueryExecutor{
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#isExists(com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public boolean isExists(IConditionClause conClause) throws SQLException, IOException{
		
		//1.��ȡ��ѯSQL���
		ISelectSqlEntity selectSql = sqlt.isExistsSql(conClause);
		
		//2.ִ��SQL
		String result = jdbcTemplate.queryForString(conn, selectSql.getSqlInfo(), selectSql.getParams());
		
		//ת�����Ƿ����
		boolean exists = !StringUtils.isEmpty(result);

		//3.���ؽ��
		return exists;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#count(com.bln.framework.persist.session.material.condition.IConditionClause)
	 */
	public int count(IConditionClause conClause) throws SQLException, IOException{
		
		//1.��ȡ��ѯSQL���
		ISelectSqlEntity selectSql = sqlt.countSql(conClause);
		
		//2.ִ��SQL
		String result = jdbcTemplate.queryForString(conn, selectSql.getSqlInfo(), selectSql.getParams());
		
		//ת��������
		int count = 0;
		if(!StringUtils.isEmpty(result)){
			count = Integer.parseInt(result);
		}
		
		//3.���ؽ��
		return count;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.executor.row.table.query.ITableQueryExecutor#queryFirstRow(com.bln.framework.persist.session.executor.material.condition.IConditionClause, com.bln.framework.persist.session.executor.material.pagination.IPagination, java.lang.String)
	 */
	public IRow queryFirstRow(IConditionClause conClause, IPagination pagination, String orderby) throws SQLException, IOException{
		
		//1.��ȡ���������ļ�¼
		List<IRow> rows = this.query(conClause, pagination, orderby);
		
		//2.��ȡ��һ�м�¼
		IRow row = null;
		if(rows != null && rows.size() > 0){
			row = rows.get(0);
		}
		
		//3.���ص�һ�м�¼
		return row;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#query(com.bln.framework.persist.session.material.condition.IConditionClause, com.bln.framework.persist.session.material.pagination.IPagination, java.lang.String)
	 */
	public List<IRow> query(IConditionClause conClause, IPagination pagination, String orderby) throws SQLException, IOException{
		
		//1.��ȡ��ѯSQL���
		ISelectSqlEntity selectSql = sqlt.selectSql(conClause, null, orderby);
		
		//2.����з�ҳ��Ϣ����������������ҳ��
		if(pagination != null){
			pagination(selectSql, pagination);
		}
			
		//3.ִ��SQL
		List<IRow> rows = jdbcTemplate.query(conn, selectSql.getSqlInfo(), selectSql.getParams(), selectSql.getColumns());

		//4.���ؽ��
		return rows;
	}
	
	/* (non-Javadoc)
	 * @see com.bln.framework.persist.session.row.table.ITableSession#queryForUpdate(com.bln.framework.persist.session.material.condition.IConditionClause, java.lang.String)
	 */
	public List<IRow> queryForUpdate(IConditionClause conClause, String orderby) throws SQLException, IOException{
		
		//1.��ȡ��ѯSQL���
		ISelectSqlEntity selectSql = sqlt.selectSql(conClause, null, orderby);
		
		//2.�������
		sqlt.queryForUpdateSql(selectSql);
		
		//3.ִ��SQL
		List<IRow> rows = jdbcTemplate.query(conn, selectSql.getSqlInfo(), selectSql.getParams(), selectSql.getColumns());

		//4.���ؽ��
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
